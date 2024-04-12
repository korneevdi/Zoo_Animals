package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.NewAnimalPayload;
import com.example.zoo.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/animals")
public class AnimalsController {

    private final AnimalService animalService;

        @GetMapping("list") // RequestMapping with the GET method
    public String getAnimalList(Model model) {
        model.addAttribute("animals", this.animalService.findAllAnimals());
        return "catalogue/animals/list";
    }

    @GetMapping("create")
    public String getNewAnimalPage() {
        return "catalogue/animals/new_animal";
    }

    @PostMapping("create")
    public String createAnimal(NewAnimalPayload payload) {
        Animal animal = this.animalService.createAnimal(payload.species(), payload.color(), payload.habitat(),
                payload.name(), payload.age(), payload.weight());
        return "redirect:/catalogue/animals/%d".formatted(animal.getId());
    }
}
