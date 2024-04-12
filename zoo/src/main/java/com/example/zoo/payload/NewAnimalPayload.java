package com.example.zoo.payload;

public record NewAnimalPayload(String species, String color, String habitat, String name, int age, double weight) {
}
