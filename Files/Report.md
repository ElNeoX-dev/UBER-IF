Séparation des préoccupations : MVC divise l'application en trois composantes principales, ce qui facilite la maintenance et l'évolution du code. Le modèle gère les données et la logique métier, la vue s'occupe de l'affichage des données, et le contrôleur fait le lien entre le modèle et la vue.

Facilité de maintenance : Cette séparation permet aux développeurs de travailler sur des composantes individuelles sans affecter les autres. Par exemple, le design de l'interface utilisateur peut être modifié sans toucher à la logique métier.

Développement parallèle : Comme les composantes sont déconnectées, différentes équipes peuvent travailler en parallèle sur le modèle, les vues, et les contrôleurs, ce qui peut accélérer le processus de développement.

Réutilisabilité du code : Les modèles peuvent souvent être réutilisés à travers différentes vues, et les vues peuvent être réutilisées avec différents contrôleurs.

Facilité de test : La séparation claire entre la logique métier et l'interface utilisateur facilite le test unitaire et le débogage. Les modèles, vues, et contrôleurs peuvent être testés séparément.

Flexibilité dans la présentation des données : Comme la vue est séparée du modèle, la même donnée peut être présentée de différentes manières. C'est particulièrement utile pour les applications qui nécessitent différentes interfaces utilisateur.

Adaptabilité et évolutivité : Le modèle MVC permet une plus grande flexibilité pour évoluer et adapter l'application aux besoins changeants, sans nécessiter une refonte complète.

Support des interactions complexes : MVC peut gérer efficacement les interactions complexes entre l'interface utilisateur et la logique métier, ce qui est crucial pour les applications web modernes.


Pour les test unitaires, nous allons utiliser JUnit, ainsi que les librairies Mockito et JFixture.

Bien sûr, je vais vous fournir un exemple simple de test unitaire en Java qui utilise JFixture pour générer des données de test et Mockito pour mocker des dépendances. Imaginons que nous avons une classe UserService qui dépend d'un UserRepository pour accéder aux données des utilisateurs.

Voici un exemple de ces classes :

```java

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId);
    }
}

public interface UserRepository {
    User findById(String userId);
}

public class User {
    private String id;
    private String name;
    // Getters, setters, etc.
}
```

Voici un exemple de test unitaire.

``` java
    import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.flextrade.jfixture.JFixture;

public class UserServiceTest {

    private UserRepository userRepositoryMock;
    private UserService userService;
    private JFixture fixture;
    private String userId;
    private User expectedUser;

    @Before
    public void setUp() {
        // Création d'un mock pour UserRepository
        userRepositoryMock = mock(UserRepository.class);
        
        // Initialisation de UserService avec le mock
        userService = new UserService(userRepositoryMock);

        // Initialisation de JFixture pour la génération de données
        fixture = new JFixture();
        
        // Création d'un ID utilisateur et d'un objet User
        userId = fixture.create(String.class);
        expectedUser = fixture.create(User.class);
        
        // Configuration du comportement du mock
        when(userRepositoryMock.findById(userId)).thenReturn(expectedUser);
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        // Action: Appel de la méthode à tester
        User result = userService.getUserById(userId);

        // Vérification: Le résultat doit correspondre à l'objet User attendu
        assertEquals(expectedUser, result);

        // Vérification que le mock a été appelé comme prévu
        verify(userRepositoryMock).findById(userId);
    }
}
```

Dans cet exemple :

JFixture est utilisé pour créer automatiquement des instances de String et User. Cela simplifie la création des données de test.
Mockito est utilisé pour créer un mock de UserRepository et configurer son comportement avec when(...).thenReturn(...).
Le test vérifie que userService.getUserById renvoie l'objet User attendu et que le mock userRepository est appelé correctement.
Cet exemple illustre la combinaison de JFixture pour la génération de données de test et de Mockito pour le mocking dans un contexte de test unitaire Java.
# Agile
## First Iteration Report


### Table of Contents

1. [Use Case Diagram](#use-case-diagram)
2. [Description of the main success scenario of all identified use cases](#description-of-the-main-success-scenario-of-all-identified-use-cases)
3. [Description of all use cases](#description-of-all-use-cases)
4. [Class and Package Diagrams](#class-and-package-diagrams)
5. [Planning of the first iteration](#planning-the-first)
6. [Glossary](#glossary)

## 1. Use Case Diagram

Here's the use case diagram for our application:

![Use Case Diagram](useCase.png "Use Case Diagram")

## 2. Description of the main success scenario of all identified use cases

| USE CASE                  | Main Success Scenario                                                                                     |
|---------------------------|-----------------------------------------------------------------------------------------------------------|
| Create Delivery Request   | 1. Manager logs into the system<br>2. Manager selects the option to create a new delivery request<br>3. System displays the form to enter necessary details (courier, time window, destination address)<br>4. Manager fills in the form and submits it<br>5. System checks and validates data<br>6. System creates the delivery request<br>7. System initiates the process of finding an available courier and computing the best tour |
| Load City Map             | 1. Manager logs into the system<br>2. Manager selects the option to load the city map<br>3. System gets all the necessary information from an XML file<br>4. System confirms successful loading of the map                                 |
| Modify Number of Couriers | 1. Manager logs into the system<br>2. Manager clicks on “+” or “-” to modify the number of available couriers<br>3. System adjusts the number of couriers                                                             |
| Save Tours                | 1. Manager logs into the system<br>2. Manager clicks on saving the tours running at the moment<br>3. System saves tour details to an XML file<br>4. System acknowledges successful saving of the tour                                       |
| Restore Tours             | 1. Manager logs into the system<br>2. Manager clicks on restoring the tours<br>3. Manager chooses the tours they want to restore<br>4. System retrieves and displays the selected tour details                                        |


## 3. Description of all use cases

| USE CASE                  | DESCRIPTION                                                                                               |
|---------------------------|-----------------------------------------------------------------------------------------------------------|
| Create Delivery Request   | Allows the manager to create a new request and input details of the delivery. The manager has to select a courier, a 1-hour time window, and the destination intersection. This will enable the computing of the best tour by the computer. Then, the departure and arrival times, as well as the tour, are displayed.      |
| Load City Map             | Display the map of the chosen city on the screen, with the name of the road.      |
| Modify Number of Couriers | Modify the number of active couriers in the system.      |
| Save Tours                | Store in a file all the tours in the city, as well as the departure and arrival time, corresponding courier, and destination address.      |
| Restore Tours             | Retrieve and load from file old tours and corresponding information, departure and arrival times as well as destination address, and display them.      |


## 4. Class and Package Diagrams

## 5. Planning of the first iteration
### a. Sprint 1
- Being able to load the map of intersections with the file given at the beginning
- Being able to display the best route
- Change the number of couriers
- Entering and managing delivery requests within the right time window

### b. Sprint 2
- Add new features: being able to add manually a delivery at an intersection that's not in the XML file given initially
- Telling the courier a road is not cyclable and he can't go deliver by this road


## 6. Glossary

- **_Application_**: The software system designed for optimizing delivery tours in cities using bicycles.
- **_City Map_**: A digital representation of a city's layout, including intersections and road segments, used for planning delivery tours.
- **_Intersection_**: A point where two or more roads meet in the city map, characterized by its geographical coordinates: latitude and longitude.
- **_Latitude:_** The geographic coordinate that specifies the north-south position of a point on the Earth's surface.
- **_Longitude:_** The geographic coordinate that specifies the east-west position of a point on the Earth's surface.
- **_Road Segment_**: A stretch of road connecting two intersections, with attributes like origin, destination, name, and length.
- **_XML File_**: A file format used to describe the city map, including details of intersections, road segments, and the warehouse address.
- **_Warehouse:_** The starting and ending point for courier tours, where deliveries are dispatched from.
- **_Courier:_** An individual responsible for carrying out deliveries on a bicycle.
- **_Delivery Request:_** An order for goods to be delivered on a bicycle to a specific location within a designated time window.
- **_Time-Window:_** A specified duration, in our case, one hour, within which a delivery must be made. Starts at fixed hours in the morning (8, 9, 10, or 11 a.m.).
- **_Tour:_** A sequence of deliveries assigned to a courier, including start and end times, delivery locations, and time windows.
- **_Tour Optimization:_** The process of determining the most efficient route for a courier to complete all assigned deliveries within their time window.
- **_Address:_** The specific location for delivery, typically including details like street name, number, city, and corresponding to latitude and longitude coordinates.
- **_Travel Speed:_** The assumed constant speed of the couriers, used for calculating tour duration and feasibility.
- **_Delivery Performance Time:_** The time taken to perform a delivery, assumed to be constant (e.g., 15 minutes).
- **_User:_** The person operating the application, responsible for loading maps, inputting delivery requests, and managing couriers. In our case, the user is the manager of UBER'IF.

