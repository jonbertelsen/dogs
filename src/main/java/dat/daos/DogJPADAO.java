package dat.daos;

import dat.dtos.DogDTO;
import dat.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DogJPADAO implements DogDAOInterface {

    private EntityManagerFactory emf;

    public DogJPADAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    @Override
    public DogDTO add(DogDTO dogDTO) {
        EntityManager em = emf.createEntityManager();
        Dog dog = new Dog(dogDTO);
        em.getTransaction().begin();
        em.persist(dog);
        em.getTransaction().commit();
        return new DogDTO(dog);
    }

    @Override
    public DogDTO getDog(int id) {
        EntityManager em = emf.createEntityManager();
        Dog dog = em.find(Dog.class, id);
        return dog != null ? new DogDTO(dog) : null;
    }

    @Override
    public List<DogDTO> getDogs() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<DogDTO> query = em.createQuery("SELECT new dat.dtos.DogDTO(d) FROM Dog d", DogDTO.class);
        return query.getResultList();
    }

    @Override
    public DogDTO update(DogDTO dogDTO) {
        EntityManager em = emf.createEntityManager();
        Dog dog = new Dog(dogDTO);
        em.getTransaction().begin();
        Dog updatedDog = em.merge(dog);
        em.getTransaction().commit();
        return new DogDTO(updatedDog);
    }

    @Override
    public DogDTO findDogByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<DogDTO> query = em.createQuery("SELECT new dat.dtos.DogDTO(d) FROM Dog d WHERE d.name = :name", DogDTO.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Dog dog = em.find(Dog.class, id);
        if (dog != null) {
            em.remove(dog);
        }
        em.getTransaction().commit();
    }

    // Close the entity manager and factory when done
    public void close() {
        emf.close();
    }
}
