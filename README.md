## Description

This web application is designed to manage information about animals in a zoo. The user can add new animals to the table, change information about them, and also remove them from the table if the animal has left the zoo. The application contains also a method for filtering the list of animals by their species. Filtering by other fields can be added in the same way.
The Animal class has six fields: species, color, habitat, name, age, and weight. The *color* field has no restrictions and can be left blank, while the other fields have restrictions. If the user tries to enter an incorrect value, a special error message is displayed.

## Structure

I'm using the **Maven** build system, **Java version 22** and **Spring Boot version 3.2.4**. To work on the application faster and more efficiently, I use the following dependencies:
 - **lombok** to write less boilerplate code (getters, setters, constructors, etc.).
 - **spring-boot-devtools** to be able to reload the application while the application is running in the IDE.
 - **spring-boot-starter-validation** to validate data.
 - **spring-boot-starter-web** because it is a web application.
 - **spring-boot-starter-thymeleaf** as a template engine.
 - **spring-boot-starter-test** to test the application.

The **ZooApplication** class starts the application. After launching, you need to open the browser and enter the address *http://localhost:8080/*, after which the user will see a page with a greeting and a button to go to the list of animals.
The application uses a four-level architecture: View -> Controller -> Service -> Repository:
 - The **View** provides the data display to the user.
 - The **Controller** generates data and passes it through the model to the View. The Vontroller processes all actions performed on the web page via http requests.
 - The **Service** layer contains the business logic of the application.
 - A **Repository** is used to store data.

The **Animal** class contains a description of animals and contains six fields.

The application contains two controllers to avoid code duplication. **AnimalsController** works with a list of animals and implements the logic for adding an animal, and **AnimalController** works with a specific animal.

To obtain data from a form in the View as an object, **Payload** records are used that describe these objects.

The **test** folder contains tests that cover all controller methods.
	
