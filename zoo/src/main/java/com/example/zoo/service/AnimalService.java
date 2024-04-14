package com.example.zoo.service;

import com.example.zoo.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService {

    List<Animal> findAllAnimals();

    Animal createAnimal(String species, String color, String habitat, String name, int age, double weight);

    Optional<Animal> findAnimal(int animalId);

    void updateAnimal(Integer id, String species, String color, String habitat, String name, int age, double weight);

    void deleteAnimal(Integer id);

    List<Animal> findAnimalsBySpecies(String species);
}
