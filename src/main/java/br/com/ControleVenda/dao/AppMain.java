package br.com.ControleVenda.dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ControleVenda.controllers.JsonToDB;
import br.com.ControleVenda.controllers.XmlToDB;

public class AppMain {

	private final static Logger logger = Logger.getLogger(AppMain.class);

	public static void main(String[] args) throws IOException, ParseException {

		logger.info("\n======= READ XML and CREATE RECORDS DB =======\n");
		XmlToDB xmlDBObj = new XmlToDB();
		//xmlDBObj.setsFileNameXml("entrada.xml");
		xmlDBObj.ReadXml();

		logger.info("\n======= READ JSON and CREATE RECORDS DB =======\n");
		JsonToDB jsonDBObj = new JsonToDB();
		jsonDBObj.setsFileNameJson("entrada2.json");
		jsonDBObj.ReadJson();
		
		logger.info("\n======= READ RECORDS =======\n");
		List<Pedido>viewPedidos = CRUD.displayRecords();
		if(viewPedidos != null & viewPedidos.size() > 0) {
			for(Pedido pedidoObj : viewPedidos) {
				logger.info(pedidoObj.toString());
			}
		}
		
		logger.info("\n======= FIND RECORD FOR ID =======\n");
		int id = 10;
		Pedido pachado = CRUD.findRecordById(id); 
		if (pachado == null ) {
			System.out.println("Not found ID");
		} else {
			System.out.println(pachado); 
		}
		
		logger.info("\n======= FIND RECORD FOR DATE =======\n");
    	String datacadastro = "2019-02-19";
    	List<Pedido> view = CRUD.findByDataCad(datacadastro);
		if (view != null) {
	    	for (Pedido pedi : view) {
				System.out.println(pedi.toString());
	    	}
	    }

/*  		
   	    logger.info("\n======= UPDATE RECORDS =======\n");
   	    //int updateId = 10;
		List<Pedido> listaPedido = CRUD.displayRecords();
		int achou = findCtr(3, listaPedido);			
		if (achou > 0) {
			CRUD.updateRecord(achou);
		}
		for(Pedido PedidoObj : CRUD.findByCodCli("DFW")) {
			logger.info(PedidoObj.toString());
		}
*/		
		
/*		logger.info("\n======= READ RECORDS AFTER UPDATION =======\n");
		List<Pedido> listaPedido = CRUD.displayRecords();

		if(listaPedido != null & listaPedido.size() > 0) {
			for(Pedido PedidoObj : listaPedido) {
				logger.info(PedidoObj.toString());
			}
		}
		
		logger.info("\n======= DELETE RECORD =======\n");
		int deleteId = 14;
		CRUD.deleteRecord(deleteId);
		logger.info("\n=======READ RECORDS AFTER DELETION=======\n");
		List<Pedido> deletePedidoRecord = CRUD.displayRecords();
		for(Pedido PedidoObj : deletePedidoRecord) {
			logger.info(PedidoObj.toString());
		}

  
	  	logger.info("\n======= DELETE ALL RECORDS =======\n");
		CRUD.deleteAllRecords();
		logger.info("\n=======READ RECORDS AFTER ALL RECORDS DELETION=======");
		List<Pedido> deleteAll = CRUD.displayRecords();
		if(deleteAll.size() == 0) {
			logger.info("\nNo Records Are Present In The Database Table!\n");
		}		
*/

	logger.info("\n======= FINAL =======\n");
	System.exit(0);

	} 
}