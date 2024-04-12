package com.example.zoo.payload;

public record UpdateAnimalPayload(String species, String color, String habitat, String name, int age, double weight) {
}
