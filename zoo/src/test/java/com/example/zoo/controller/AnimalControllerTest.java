package com.example.zoo.controller;

import com.example.zoo.entity.Animal;
import com.example.zoo.payload.UpdateAnimalPayload;
import com.example.zoo.service.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ConcurrentModel;
import org.springframework.validation.BindingResult;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Modular tests of the AnimalController class")
class AnimalControllerTest {

    // Create a mock-object of the AnimalService interface using Mockito library
    AnimalService animalService = Mockito.mock(AnimalService.class);

    MessageSource messageSource = Mockito.mock(MessageSource.class);

    AnimalController controller = new AnimalController(this.animalService, this.messageSource);

    @Test
    @DisplayName("animal method returns existing animal")
    void animal_AnimalExists_ReturnsAnimal() {
        // given
        var animal = new Animal(1, "Species", "Color",
                "Habitat", "Name", 4, 16);

        doReturn(Optional.of(animal)).when(this.animalService).findAnimal(1);

        // when
        var result = this.controller.animal(1);

        // then
        assertEquals(animal, result);

        verify(this.animalService).findAnimal(1);
        verifyNoMoreInteractions(this.animalService);
    }

    @Test
    @DisplayName("animal method throws exception for not existing animal")
    void animal_AnimalDoesNotExist_ThrowsNoSuchElementException() {
        // given

        // when
        var exception = assertThrows(NoSuchElementException.class, () -> this.controller.animal(1));

        // then
        assertEquals("catalogue.errors.animal.not_found", exception.getMessage());

        verify(this.animalService).findAnimal(1);
        verifyNoMoreInteractions(this.animalService);
    }

    @Test
    @DisplayName("getAnimal returns animal's page")
    void getAnimal_ReturnsAnimalPage() {
        // given

        // when
        var result = this.controller.getAnimal();

        // then
        assertEquals("catalogue/animals/animal", result);

        verifyNoInteractions(this.animalService);
    }

    @Test
    @DisplayName("getAnimalEditPage returns animal's edit page")
    void getAnimalEditPage_ReturnsAnimalEditPage() {
        // given

        // when
        var result = this.controller.getAnimalEditPage();

        // then
        assertEquals("catalogue/animals/edit", result);

        verifyNoInteractions(this.animalService);
    }

    @Test
    @DisplayName("updateAnimal returns animal's page")
    void updateAnimal_RequestIsValid_RedirectsToAnimalPage() {
        // given
        var animal = new Animal(1, "Species", "Color",
                "Habitat", "Name", 4, 16);
        var payload = new UpdateAnimalPayload("Species", "Color",
                "Habitat", "Name", 4, 16);
        var model = new ConcurrentModel();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        // when
        var result = this.controller.updateAnimal(animal, payload, bindingResult, model);

        // then
        assertEquals("redirect:/catalogue/animals/1", result);

        verify(this.animalService).updateAnimal(1, "Species", "Color",
                "Habitat", "Name", 4, 16);
        verifyNoMoreInteractions(this.animalService);
    }

    @Test
    @DisplayName("deleteAnimal redirects to the list of animals")
    void deleteAnimal_RedirectsToAnimalsListPage() {
        // given
        var animal = new Animal(1, "Species", "Color",
                "Habitat", "Name", 4, 16);

        // when
        var result = this.controller.deleteAnimal(animal);

        // then
        assertEquals("redirect:/catalogue/animals/list", result);

        verify(this.animalService).deleteAnimal(1);
        verifyNoMoreInteractions(this.animalService);
    }

    @Test
    @DisplayName("handleNoSuchElementException redirects to error 404 page")
    void handleNoSuchElementException_Returns404ErrorPage() {
        // given
        var exception = new NoSuchElementException("error");
        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();
        var locale = Locale.of("en");

        doReturn("Error").when(this.messageSource)
                .getMessage("error", new Object[0], "error", Locale.of("en"));

        // when
        var result = this.controller.handleNoSuchElementException(exception, model, response, locale);

        // then
        assertEquals("errors/404",  result);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

        verify(this.messageSource).getMessage("error", new Object[0],
                "error", Locale.of("en"));
        verifyNoMoreInteractions(this.messageSource);
        verifyNoInteractions(this.animalService);
    }
}