package dat.entities;

import dat.dtos.DogDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // Marks this class as a JPA entity
public class Dog {

    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID values
    private int id;

    private String name;
    private String breed;
    private String gender;
    private int age;

    public Dog(String name, String breed, String gender, int age) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }

    public Dog(DogDTO dogDTO){
        this.id = dogDTO.getId();
        this.name = dogDTO.getName();
        this.breed = dogDTO.getBreed();
        this.gender = dogDTO.getGender();
        this.age = dogDTO.getAge();
    }
}
