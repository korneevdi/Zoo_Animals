package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.UpdateAnimalPayload;
import com.example.zoo.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/animals/{animalId:\\d+}")
public class AnimalController {

    private final AnimalService animalService;

    @ModelAttribute("animal")
    public Animal animal(@PathVariable("animalId") int animalId) {
        return this.animalService.findAnimal(animalId).orElseThrow();
    }

    @GetMapping
    public String getAnimal(){
        return "catalogue/animals/animal";
    }

    @GetMapping("edit")
    public String getAnimalEditPage(){
        return "catalogue/animals/edit";
    }

    @PostMapping("edit")
    public String updateAnimal(@ModelAttribute("animal") Animal animal, UpdateAnimalPayload payload) {
        this.animalService.updateAnimal(animal.getId(), payload.species(), payload.color(),
                payload.habitat(), payload.name(), payload.age(), payload.weight());
        return "redirect:/catalogue/animals/%d".formatted(animal.getId());
    }
}
