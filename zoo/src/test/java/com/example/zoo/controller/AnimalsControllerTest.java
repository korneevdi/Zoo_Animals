package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.NewAnimalPayload;
import com.example.zoo.service.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Modular tests of the AnimalsController class")
class AnimalsControllerTest {

    // Create a mock-object of the AnimalService interface using Mockito library
    AnimalService animalService = Mockito.mock(AnimalService.class);

    AnimalsController controller = new AnimalsController(this.animalService);

    @Test
    @DisplayName("getFilteredAnimalList returns the list of animals filtered by species")
    void getFilteredAnimalList_ReturnsFilteredAnimalsListPage() {
        // given
        var model = new ConcurrentModel();
        var species = "fox";
        var filteredAnimals = List.of(
                new Animal(1, "Fox", "Red", "Forest", "Foxy", 3, 15.0),
                new Animal(2, "Fox", "Brown", "Meadow", "Rusty", 4, 17.5)
        );
        doReturn(filteredAnimals).when(animalService).findAnimalsBySpecies(species);

        // when
        var result = controller.getFilteredAnimalList(species, model);

        // then
        assertEquals("catalogue/animals/list", result);
        assertEquals(filteredAnimals, model.getAttribute("animals"));
    }

    @Test
    @DisplayName("getNewAnimalPage redirects to the animal's page")
    void getNewAnimalPage_ReturnsNewAnimalPage () {
        // given

        // when
        var result = this.controller.getNewAnimalPage();

        // then
        assertEquals("catalogue/animals/new_animal", result);
    }

    @Test
    @DisplayName("createAnimal creates a new animal and redirects to the animal's page")
    void createAnimal_RequestIsValid_ReturnsRedirectionToAnimalPage() {
        // given
        var payload = new NewAnimalPayload("Species", "Color", "Habitat",
                "Name", 5, 10);
        var model = new ConcurrentModel();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        doReturn(new Animal(1, "Species", "Color", "Habitat",
                "Name", 5, 10))
                .when(this.animalService)
                .createAnimal("Species", "Color", "Habitat",
                        "Name", 5, 10);

        // call the tested method
        var result = this.controller.createAnimal(payload, bindingResult, model);

        // check the result of testing
        assertEquals("redirect:/catalogue/animals/1", result);

        verify(this.animalService).createAnimal("Species", "Color", "Habitat",
                "Name", 5, 10);
        verifyNoMoreInteractions(this.animalService);
    }
}