package dat.routes;

import dat.Populator;
import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.DogJPADAO;
import dat.dtos.DogDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DogRoutesTest {

    private static Javalin app;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7070/api/v1";
    private static DogJPADAO dogDao = new DogJPADAO(emf);
    private static Populator populator = new Populator(dogDao, emf);

    private static DogDTO d1, d2, d3;

    private static List<DogDTO> dogs;

    @BeforeAll
    void init() {
        app = ApplicationConfig.startServer(7070, emf);
        HibernateConfig.setTest(true);

    }

    @BeforeEach
    void setUp() {
        dogs = populator.populate3Dogs();
        d1 = dogs.get(0);
        d2 = dogs.get(1);
        d3 = dogs.get(2);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpDogs();
    }

    @AfterAll
    void closeDown() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetDog() {
        DogDTO dog =
                given()
                .when()
                .get(BASE_URL + "/dogs/" + d1.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(DogDTO.class);

        assertThat(dog, equalTo(d1));
    }

    @Test
    void testGetDogs() {
        DogDTO[] dogs =
                given()
                .when()
                .get(BASE_URL + "/dogs")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(DogDTO[].class);

        assertEquals(3, dogs.length);
        assertThat(dogs, arrayContainingInAnyOrder(d1, d2, d3));


    }
}