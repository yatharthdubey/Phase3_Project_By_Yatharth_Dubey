package com.capg.parallelproject.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Util {

	private static EntityManager em;
	
	public static EntityManager getEntityManager() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Employee_Details");  
        EntityManager em = emf.createEntityManager();  
        return em;
	}
	public static void close() {
		em.close();
	}
}
