package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.DogJPADAO;
import dat.dtos.DogDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DogRoutesTest {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final DogJPADAO dogDao = new DogJPADAO(emf);
    private static final String BASE_URL = "http://localhost:7070/api/v1";

    private static DogDTO d1, d2, d3;

    @BeforeAll
    void setUpAll() {
        //HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(7070, emf);
    }

    @BeforeEach
    void setUp() {
        // Populate the database with test data (3 dogs)
        d1 = new DogDTO("Buller", "Poodle", "Male", 1);
        d2 = new DogDTO("Fido", "Lab", "Female", 2);
        d3 = new DogDTO("Rex", "Pit", "Male", 3);
        d1 = dogDao.add(d1);
        d2 = dogDao.add(d2);
        d3 = dogDao.add(d3);
    }

    @AfterEach
    void tearDown() {
       // Delete all data from database
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE dog_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetDogsEasy() {
        // Test that we can get all dogs
        // Use RestAssured to make a GET request to BASE_URL + "/dogs"
        // Check that the status code is 200
        // Check that the response body is a list of size 3

    given()
            .when()
            .get(BASE_URL + "/dogs")
            .then()
            .statusCode(200)
            .body("size()", is(3));
    }


    @Test
    void testGetDogsHard(){
        // Test that we can get all dogs and we get the right dogs
        // Use RestAssured to make a GET request to BASE_URL + "/dogs"
        // Check that the status code is 200
        // Deserialize the response body to a List<DogDTO>
        // Check that the list contains d1, d2, and d3
        DogDTO[] actualDogsArray =
                given()
                .when()
                .get(BASE_URL + "/dogs")
                .then()
                .statusCode(200)
                .extract()
                .as(DogDTO[].class);
        List<DogDTO> actualDogList = Arrays.asList(actualDogsArray);
        assertThat(actualDogList, containsInAnyOrder(d1, d2, d3));
    }


}