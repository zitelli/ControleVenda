package br.com.ControleVenda.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.com.ControleVenda.dao.AppMain;
import br.com.ControleVenda.dao.CRUD;
import br.com.ControleVenda.dao.Pedido;

public class JsonToDB {

	private final static Logger logger = Logger.getLogger(AppMain.class);
	
	private String sFileNameJson;

	private static JsonReader result;

	private static int findCtr(int ctr, List<Pedido> relacao) {
	    for (Pedido reg : relacao) {
	        if (reg.getCtr() == ctr) {
	            return reg.getId();
	        }
	    }
	    return -1;
	}
	
	public void setsFileNameJson(String sFileNameJson) {
		this.sFileNameJson = sFileNameJson;
	}

	public void ReadJson() {
		
		logger.info("....... Hibernate Crud Operations .......\n");
	
		logger.info("\n======= READ RECORDS FROM DATABASE =======\n");
		List<Pedido> registrosDB = CRUD.displayRecords();
	
		BufferedReader reader = null;
		try {
	
			logger.info("\n======= READ JSON =======\n");
			reader = new BufferedReader(new FileReader(this.sFileNameJson)); 
			//reader = new BufferedReader(new FileReader("entrada2.json")); 
			result = new JsonReader(reader);
			Gson g = new Gson();
			Result res = g.fromJson(result, Result.class);		
			List<Pedido> lista = res.getPedidos();
			List<Integer> keys = new ArrayList<Integer>();
			
			int iLimite = 10;
			for (int i = 0; ((i < lista.size()) && (i < iLimite)); i++) {
	
	        	Pedido pedido = lista.get(i);
	        	int iControle = pedido.getCtr();
	
	        	int iachou = findCtr(iControle, registrosDB);			
	        	if ((iachou < 0) && (!keys.contains(iControle))) {
	        		
	            	keys.add(iControle);
	
	        		if (pedido.getData() == null) {
					    DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						pedido.setData(f.format(new Date()));
					}
					if (pedido.getQtd() == 0) {
						pedido.setQtd(1);
					}
					pedido.setTotal();
	
					logger.info("\n======= CREATE RECORD =======\n");
					CRUD.createRecord(pedido);
	    		} else {
	    			iLimite += 1;
	    		}
				}	
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
	
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
