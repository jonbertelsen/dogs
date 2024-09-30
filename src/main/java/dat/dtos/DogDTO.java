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


}