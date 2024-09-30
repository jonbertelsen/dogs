package dat.controllers;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dtos.DogDTO;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DogRestTest {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static List<DogDTO> testDogs;
    private static final String BASE_URL = "http://localhost:7070/api/v1";
    private static final Logger logger = LoggerFactory.getLogger(DogRestTest.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("dev");

    @BeforeAll
    void setUpAll() {
        app = ApplicationConfig.startServer(7070, emf);
    }

    @BeforeEach
    void setUp() {
        testDogs = Populators.populate3Dogs(emf);
    }

    @AfterEach
    void tearDown() {
        Populators.clearDogTable(emf);
    }

    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetDogsEasy() {
        // Test that we can get all dogs
        String response =
        given()
                .when()
                .get(BASE_URL + "/dogs")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .log().all()
                .extract()
                .asString();

        debugLogger.debug(response);
    }

    @Test
    void testGetDogsHard() {
        // Test that we can get all dogs
        List<DogDTO> actualDogs =
                given()
                .when()
                    .get(BASE_URL + "/dogs")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<DogDTO>>() {});  // Deserialize to List<DogDTO>

        // Assert that the expected and actual lists are the same
        assertThat(actualDogs, containsInAnyOrder(testDogs.toArray()));
    }


}