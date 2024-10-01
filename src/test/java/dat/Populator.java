package dat;

import dat.daos.DogJPADAO;
import dat.dtos.DogDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {

    private static DogJPADAO dogDao;
    private static EntityManagerFactory emf;

    public Populator(DogJPADAO dogDao, EntityManagerFactory emf) {
        this.dogDao = dogDao;
        this.emf = emf;
    }


    public List<DogDTO> populate3Dogs() {

        DogDTO d1, d2, d3;
        // Populate data and test objects
        d1 = new DogDTO("Buller", "Poodle", "Male", 2);
        d2 = new DogDTO("Fido", "Lab", "Female", 3);
        d3 = new DogDTO("Rex", "Pit", "Male", 4);
        d1 = dogDao.add(d1);
        d2 = dogDao.add(d2);
        d3 = dogDao.add(d3);
        return new ArrayList<>(List.of(d1, d2, d3));
    }

    public void cleanUpDogs(){
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE dog_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
