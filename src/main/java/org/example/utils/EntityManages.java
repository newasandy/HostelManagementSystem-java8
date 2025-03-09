package org.example.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManages {

    private static final EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hostelmanagement");
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Throwable ex) {
                System.err.println("Initial EntityManagerFactory creation failed: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        public EntityManager getEntityManager() {
            return entityManager;
        }
        public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }


}
