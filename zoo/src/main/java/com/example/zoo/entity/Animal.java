package com.example.zoo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    private Integer id;

    private String species;

    private String color;

    private String habitat;

    private String name;

    private int age;

    private double weight;
}
