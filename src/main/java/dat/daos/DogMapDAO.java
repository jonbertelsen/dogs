package dat.daos;

import dat.dtos.DogDTO;
import lombok.AllArgsConstructor;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DogMapDAO implements DogDAOInterface {
    private Map<Integer, DogDTO> dogMap;

    public DogMapDAO() {
        dogMap = new HashMap<>();
    }

    @Override
    @Synchronized
    public DogDTO add(DogDTO dogDTO) {
        DogDTO newDog = new DogDTO(dogMap.size() + 1, dogDTO.getName(), dogDTO.getBreed(), dogDTO.getGender(), dogDTO.getAge());
        dogMap.put(newDog.getId(), newDog);
        return newDog;
    }

    @Override
    public DogDTO getDog(int id) {
        return dogMap.getOrDefault(id, null);
    }

    @Override
    public List<DogDTO> getDogs() {
        return dogMap.values().stream().toList();
    }

    @Override
    public DogDTO update(DogDTO dogDTO) {
        dogMap.put(dogDTO.getId(), dogDTO);
        return dogDTO;
    }

    @Override
    public DogDTO findDogByName(String name) {
        return dogMap.values().stream().filter(dog -> dog.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        dogMap.remove(id);
    }
}
