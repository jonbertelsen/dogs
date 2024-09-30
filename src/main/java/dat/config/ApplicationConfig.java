package dat.config;

import dat.routes.Routes;
import io.javalin.config.JavalinConfig;

public class ApplicationConfig {

    private static Routes routes = new Routes();

    public static void configuration(JavalinConfig config) {
        config.router.contextPath = "/api/v1"; // base path for all routes
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.router.apiBuilder(routes.getRoutes());

        // Plugins
        config.bundledPlugins.enableRouteOverview("/routes"); // enables route overview at /routes
    }
}
