package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.UpdateAnimalPayload;
import com.example.zoo.service.AnimalService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/animals/{animalId:\\d+}")
public class AnimalController {

    private final AnimalService animalService;

    private final MessageSource messageSource;

    @ModelAttribute("animal")
    public Animal animal(@PathVariable("animalId") int animalId) {
        return this.animalService.findAnimal(animalId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.animal.not_found"));
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
    public String updateAnimal(@ModelAttribute(name = "animal", binding = false) Animal animal, @Valid UpdateAnimalPayload payload,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/animals/edit";
        } else {
            this.animalService.updateAnimal(animal.getId(), payload.species(), payload.color(),
                    payload.habitat(), payload.name(), payload.age(), payload.weight());
            return "redirect:/catalogue/animals/%d".formatted(animal.getId());
        }
    }

    @PostMapping("delete")
    public String deleteAnimal(@ModelAttribute("animal") Animal animal) {
        this.animalService.deleteAnimal(animal.getId());
        return "redirect:/catalogue/animals/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
