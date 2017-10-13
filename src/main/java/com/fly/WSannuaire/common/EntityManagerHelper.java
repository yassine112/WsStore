package com.fly.WSannuaire.common;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author EL HALAOUI Yassine
 *
 * Class Control the EntityManager and make it in threadLocal
 */
public class EntityManagerHelper {
	private static EntityManagerFactory emf = null; 
    private static ThreadLocal<EntityManager> threadLocal = null;
    
    static {
        try {
			emf = Persistence.createEntityManagerFactory("default");      
			threadLocal = new ThreadLocal<EntityManager>();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();

        if (em == null) {
            em = emf.createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }
        
    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            em.close();
            threadLocal.set(null);
        }
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }
}
