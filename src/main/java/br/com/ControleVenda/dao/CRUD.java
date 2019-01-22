package br.com.ControleVenda.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class CRUD {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private final static Logger logger = Logger.getLogger(CRUD.class);

	// This Method Is Used To Create The Hibernate's SessionFactory Object
	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	// Method 1: This Method Used To Create A New Pedido Record In The Database Table
	public static void createRecord(Pedido pedidoObj) {
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();
			sessionObj.save(pedidoObj);
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Created Record In The Database!\n");
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	// Method 2: This Method Is Used To Display The Records From The Database Table
	@SuppressWarnings("unchecked")
	public static List<Pedido> displayRecords() {
		List<Pedido> PedidosList = new ArrayList<Pedido>();		
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			PedidosList = sessionObj.createQuery("FROM Pedido").list();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
		return PedidosList;
	}
	
	// Method 3: This Method Is Used To Update A Record In The Database Table	
	public static void updateRecord(int id) {		
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			// Creating Transaction Entity
			Pedido studObj = (Pedido) sessionObj.get(Pedido.class, id);

			studObj.setProd("Apostila C++");
			studObj.setData("2017-08-21 10:20:56");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nPedido - Id = " + id + " foi atualizado na base de dados!\n");
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	// Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
	public static void deleteRecord(int id) {
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			Pedido studObj = findRecordById(id);
			sessionObj.delete(studObj);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nPedido With controle?= " + id + " foi deletado com sucesso da base de dados!\n");
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
	
	// Method 4(b): This Method To Find Particular Record In The Database Table
	public static Pedido findRecordById(int id) {
		Pedido findPedObj = null;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			findPedObj = (Pedido) sessionObj.get(Pedido.class, id);
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
		return findPedObj;
	}

    public static List<Pedido> findByDataCad(String dat) {
    	List<Pedido> filtroList = null;
		if (dat == null || dat == "") return filtroList;
   	
    	Date data = java.sql.Date.valueOf(dat);
    	try {
			filtroList = new ArrayList<Pedido>();		

			// Getting Session Object From SessionFactory
	    	sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

	    	Query consulta = sessionObj.createQuery("FROM Pedido WHERE date(data) LIKE :dc");
	    	consulta.setParameter("dc", data);
	    	filtroList = consulta.list();

    	} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return filtroList;		
    }	
	
	
	// Method 5: This Method Is Used To Delete All Records From The Database Table
	public static void deleteAllRecords() {
		try {
			// Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			Query queryObj = sessionObj.createQuery("DELETE FROM Pedido");
			queryObj.executeUpdate();

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			logger.info("\nSuccessfully Deleted All Records From The Database Table!\n");
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				logger.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}