package dat.routes;

import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final DogRoutes dogRoutes = new DogRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/", () -> {
                get(ctx -> ctx.json("{ \"message\": \"Hello world\" }"));
                path("dogs", dogRoutes.getRoutes());
            });
        };
    }
}
