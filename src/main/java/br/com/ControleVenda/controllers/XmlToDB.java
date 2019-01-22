package br.com.ControleVenda.controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.ControleVenda.dao.AppMain;
import br.com.ControleVenda.dao.CRUD;
import br.com.ControleVenda.dao.Pedido;

public class XmlToDB {

	private final static Logger logger = Logger.getLogger(AppMain.class);
	private String sFileNameXml;

	public XmlToDB() {
		sFileNameXml = "entrada.xml";
	}

	public void setsFileNameXml(String sFileNameXml) {
		this.sFileNameXml = sFileNameXml;
	}

	private static int findCtr(int ctr, List<Pedido> relacao) {
	    for (Pedido reg : relacao) {
	        if (reg.getCtr() == ctr) {
	            return reg.getId();
	        }
	    }
	    return -1;
	}

	public void ReadXml() {
	     try {
	    	 File inputFile = new File(this.sFileNameXml);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();

	         logger.info("\nRoot element : " + doc.getDocumentElement().getNodeName() + "\n");

	         NodeList nList = doc.getElementsByTagName("pedido");
	         
			 logger.info("\n======= READ RECORDS FROM DATABASE =======\n");
			 List<Pedido> registrosDB = CRUD.displayRecords();
  			 List<Integer> keys = new ArrayList<Integer>();
 			 int iLimite = 10;

	         for (int temp = 0; ((temp < nList.getLength()) && (temp < iLimite)); temp++) {
	        	 Node nNode = nList.item(temp);
	        	 if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) nNode;
		        	 String sCtr = eElement.getElementsByTagName("controle").item(0).getTextContent();
		        	 int iCtr = Integer.parseInt(sCtr);

		        	 int iachou = findCtr(iCtr, registrosDB);			
		        	 if ((iachou < 0) && (!keys.contains(iCtr))) {
		        		 keys.add(iCtr);
		        		 String sDat = eElement.getElementsByTagName("data").item(0).getTextContent();
		        		 if (sDat == "") {
		        			 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        			 sDat = f.format(new Date());
			        	 }
		        		 String sPrd = eElement.getElementsByTagName("produto").item(0).getTextContent();
		        		 String sVlr = eElement.getElementsByTagName("valorunitario").item(0).getTextContent();
		        		 double dVlr = Double.parseDouble(sVlr);
		        		 String sQtd = eElement.getElementsByTagName("quantidade").item(0).getTextContent();
		        		 if (sQtd == "") sQtd = "1";
		        		 int iQtd = Integer.parseInt(sQtd);
		        		 String sCod = eElement.getElementsByTagName("cliente").item(0).getTextContent();

		        		 Pedido ped = new Pedido();
		        		 ped.MontaPedido(iCtr, sDat, sPrd, dVlr, iQtd, sCod);
		        		 ped.setTotal();
					     //System.out.println(ped);
		        		 
		        		 logger.info("\n======= CREATE RECORD =======\n");
		        		 CRUD.createRecord(ped);
			    	} else {
			    		iLimite += 1;
			    	}
	            }
	         }
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	}		
}
