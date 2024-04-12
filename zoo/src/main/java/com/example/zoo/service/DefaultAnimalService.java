package com.example.zoo.service;

import com.example.zoo.entity.Animal;
import com.example.zoo.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAnimalService implements AnimalService{

    private final AnimalRepository animalRepository;

    @Override
    public List<Animal> findAllAnimals() {
        return this.animalRepository.findAll();
    }

    @Override
    public Animal createAnimal(String species, String color, String habitat, String name, int age, double weight) {
        return this.animalRepository.save(new Animal(null, species, color, habitat, name, age, weight));
    }

    @Override
    public Optional<Animal> findAnimal(int animalId) {
        return this.animalRepository.findById(animalId);
    }

    @Override
    public void updateAnimal(Integer id, String species, String color, String habitat,
                             String name, int age, double weight) {
        this.animalRepository.findById(id)
                .ifPresentOrElse(animal ->  {
                    animal.setSpecies(species);
                    animal.setColor(color);
                    animal.setHabitat(habitat);
                    animal.setName(name);
                    animal.setAge(age);
                    animal.setWeight(weight);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }
}
