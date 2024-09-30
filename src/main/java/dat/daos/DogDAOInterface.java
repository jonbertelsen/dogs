package dat.daos;

import dat.dtos.DogDTO;

import java.util.List;

public interface DogDAOInterface {
    DogDTO add(DogDTO dogDTO);

    DogDTO getDog(int id);

    List<DogDTO> getDogs();

    DogDTO update(DogDTO dogDTO);

    DogDTO findDogByName(String name);

    void remove(int id);
}
