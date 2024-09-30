package dat.routes;

import dat.controllers.DogController;
import dat.daos.DogJPADAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogController dogController;

    public DogRoutes(EntityManagerFactory emf) {
        dogController = new DogController(new DogJPADAO(emf));
    }

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", dogController::getDogs);
            path("/{id}", () -> {
                get(dogController::getDog);
                delete(dogController::deleteDog);
                put(dogController::updateDog);
            });
            post(dogController::addDog);
            path("doggies", () -> {
                post(dogController::addDogs);
            });
        };
    }
}
