package dat.controllers;

import dat.daos.DogJPADAO;
import dat.dtos.DogDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Populators {

    public static List<DogDTO> populate3Dogs(EntityManagerFactory emf) {
        // Populate the database with some dogs
        DogDTO d1, d2, d3;
        DogJPADAO dogDAO = new DogJPADAO(emf);
        DogDTO dog1 = new DogDTO("Fido", "Labrador", "Male", 5);
        DogDTO dog2 = new DogDTO("Rex", "German Shepherd" ,"Male", 3);
        DogDTO dog3 = new DogDTO("Spot", "Dalmatian", "Female", 2);
        d1 = dogDAO.add(dog1);
        d2 = dogDAO.add(dog2);
        d3 = dogDAO.add(dog3);
        return List.of(d1, d2, d3);
    }

    public static void clearDogTable(EntityManagerFactory emf) {
        // Clear the database
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
