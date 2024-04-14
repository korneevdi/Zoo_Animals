package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.NewAnimalPayload;
import com.example.zoo.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/animals")
public class AnimalsController {

    private final AnimalService animalService;

    /*@GetMapping("list") // RequestMapping with the GET method
    public String getAnimalList(Model model) {
        model.addAttribute("animals", this.animalService.findAllAnimals());
        return "catalogue/animals/list";
    }*/

    @GetMapping("list")
    public String getFilteredAnimalList(@RequestParam(required = false) String species, Model model) {
        List<Animal> animals;
        if (species != null && !species.isEmpty()) {
            animals = animalService.findAnimalsBySpecies(species);
        } else {
            animals = animalService.findAllAnimals();
        }
        model.addAttribute("animals", animals);
        return "catalogue/animals/list";
    }

    @GetMapping("create")
    public String getNewAnimalPage() {
        return "catalogue/animals/new_animal";
    }

    @PostMapping("create")
    public String createAnimal(@Valid NewAnimalPayload payload, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/animals/new_animal";
        } else {
            Animal animal = this.animalService.createAnimal(payload.species(), payload.color(), payload.habitat(),
                    payload.name(), payload.age(), payload.weight());
            return "redirect:/catalogue/animals/%d".formatted(animal.getId());
        }
    }
}
