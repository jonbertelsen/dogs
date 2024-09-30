package dat.controllers;

import dat.daos.DogJPADAO;
import dat.dtos.DogDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class DogController {

    private DogJPADAO dogDAO;

    public DogController(DogJPADAO dogDAO) {
        this.dogDAO = dogDAO;
    }

    public void getDog(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dog = dogDAO.getDog(id);
        if (dog == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("{ \"message\": \"Dog not found\" }");
        } else {
            ctx.json(dog);
        }
    }

    public void getDogs(Context ctx) {
        ctx.status(HttpStatus.OK);
        ctx.json(dogDAO.getDogs());
    }

    public void addDog(Context ctx) {
        DogDTO newDog = ctx.bodyAsClass(DogDTO.class);
        ctx.status(HttpStatus.CREATED);
        ctx.json(dogDAO.add(newDog));
    }

    public void updateDog(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dog = dogDAO.getDog(id);
        if (dog == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("{ \"message\": \"Dog not found\" }");
        } else {
            DogDTO updatedDog = ctx.bodyAsClass(DogDTO.class);
            updatedDog.setId(id);
            ctx.json(dogDAO.update(updatedDog));
        }
    }

    public void deleteDog(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dog = dogDAO.getDog(id);
        if (dog == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("{ \"message\": \"Dog not found\" }");
        } else {
            dogDAO.remove(id);
            ctx.status(HttpStatus.NO_CONTENT);
        }
    }

    public void addDogs(Context ctx) {
        DogDTO[] newDogs = ctx.bodyAsClass(DogDTO[].class);
        for (DogDTO dog : newDogs) {
            dogDAO.add(dog);
        }
        ctx.status(HttpStatus.CREATED);
        ctx.json(newDogs);
    }
}
