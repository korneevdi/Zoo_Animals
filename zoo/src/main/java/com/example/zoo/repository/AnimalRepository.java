package com.example.zoo.repository;

import com.example.zoo.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    List<Animal> findAll();

    Animal save(Animal animal);

    Optional<Animal> findById(Integer animalId);

    void deleteById(Integer id);

    List<Animal> findBySpecies(String species);
}
