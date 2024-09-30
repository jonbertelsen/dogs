package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("dog");

    public static void main(String[] args) {
        System.out.println("Hello dogs!");
        ApplicationConfig.startServer(7070, emf);
    }
}