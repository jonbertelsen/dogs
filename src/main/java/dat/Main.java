package dat;

import dat.config.ApplicationConfig;
import dat.controllers.DogController;
import dat.daos.DogMapDAO;
import dat.dtos.DogDTO;
import io.javalin.Javalin;

public class Main {
    private static final DogMapDAO DOG_MAP_DAO = new DogMapDAO();
    private static final DogController dogController = new DogController(DOG_MAP_DAO);

    public static void main(String[] args) {

        System.out.println("Hello dogs!");

        DOG_MAP_DAO.add(new DogDTO("Bulder", "Golden", "Male", 2));
        DOG_MAP_DAO.add(new DogDTO("Basse", "Doodle", "Female", 3));

        var app = Javalin
                .create(ApplicationConfig::configuration)
                .start(7070);
    }
}