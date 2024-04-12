package com.example.zoo.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewAnimalPayload(
        @NotNull(message = "{catalogue.animals.create.errors.species_is_null}")
        @Size(min = 2, max = 50, message = "{catalogue.animals.create.errors.species_size_is_invalid}")
        String species,
        @Size(max = 20, message = "{catalogue.animals.create.errors.color_is_invalid}")
        String color,
        @NotNull(message = "{catalogue.animals.create.errors.habitat_is_null}")
        @Size(min = 2, max = 30, message = "{catalogue.animals.create.errors.habitat_size_is_invalid}")
        String habitat,
        @NotNull(message = "{catalogue.animals.create.errors.name_is_null}")
        @Size(min = 2, max = 30, message = "{catalogue.animals.create.errors.name_size_is_invalid}")
        String name,
        @NotNull(message = "{catalogue.animals.create.errors.age_is_null}")
        @Min(value = 0, message = "{catalogue.animals.create.errors.age_value_is_invalid}")
        int age,
        @NotNull(message = "{catalogue.animals.create.errors.weight_is_null}")
        @Min(value = 0, message = "{catalogue.animals.create.errors.weight_value_is_invalid}")
        double weight) {
}
