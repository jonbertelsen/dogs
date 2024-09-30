package dat.routes;

import dat.controllers.DogController;
import dat.daos.DogMapDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogMapDAO dogMapDao = new DogMapDAO();
    private final DogController dogController = new DogController(dogMapDao);

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
