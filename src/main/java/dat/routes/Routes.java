package dat.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final DogRoutes dogRoutes;

    public Routes(EntityManagerFactory emf){
        dogRoutes = new DogRoutes(emf);
    }

    public EndpointGroup getRoutes() {
        return () -> {
                get(ctx -> ctx.json("{ \"message\": \"Hello world\" }"));
                path("dogs", dogRoutes.getRoutes());
        };
    }
}