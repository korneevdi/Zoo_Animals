package com.example.zoo.repository;

import com.example.zoo.entity.Animal;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class InMemoryAnimalRepository implements AnimalRepository{

    // Repository for animal information
    private final List<Animal> animals = Collections.synchronizedList(new ArrayList<>());

    public InMemoryAnimalRepository() {
        // Add a couple of animals by hand to output them at the web page
        this.animals.add(new Animal(1, "Lion", "orange", "Tanzania", "King", 8, 121.2));
        this.animals.add(new Animal(2, "Wolf", "grey", "Canada", "Big-Paw", 11, 81.4));
    }

    @Override
    public List<Animal> findAll() {
        // Return unmodifiable list so that the origin list could not be changed
        return Collections.unmodifiableList(this.animals);
    }

    @Override
    public Animal save(Animal animal) {
        animal.setId(this.animals.stream()
                .max(Comparator.comparingInt(Animal::getId))
                .map(Animal::getId)
                .orElse(0) + 1);
        this.animals.add(animal);
        return animal;
    }

    @Override
    public Optional<Animal> findById(Integer animalId) {
        return this.animals.stream()
                .filter(animal -> Objects.equals(animalId, animal.getId()))
                .findFirst();
    }
}
