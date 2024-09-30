package dat.dtos;

import dat.entities.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogDTO {
    private int id;
    private String name;
    private String breed;
    private String gender;
    private int age;

    public DogDTO(String name, String breed, String gender, int age) {
        this.id = 0;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }


    public DogDTO(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.gender = dog.getGender();
        this.age = dog.getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DogDTO dogDTO = (DogDTO) o;
        return getId() == dogDTO.getId() && getAge() == dogDTO.getAge() && getName().equals(dogDTO.getName()) && getBreed().equals(dogDTO.getBreed()) && getGender().equals(dogDTO.getGender());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getBreed().hashCode();
        result = 31 * result + getGender().hashCode();
        result = 31 * result + getAge();
        return result;
    }
}