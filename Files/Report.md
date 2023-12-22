# Agile

## PLD Agile: Agile Development Report

Mark Beckmann, Back-End Developer & Product Owner

Evann Guillot, Back-End Developer

Noham Martin, Back-End Developer

Tim Morel, Front-End Developer

Marie Roulier, Full-Stack Developer & Scrum Master

Hugo Warin, Full-Stack Developer

Zyad Haddad, Front-End Developer



Group: H4132 Department: Computer Science

University: INSA Lyon

### Table of Contents

1. [Introduction](#1-introduction)
2. [Use Case Diagram](#2-use-case-diagram)
3. [Description of the main success scenario of all identified use cases](#3-description-of-the-main-success-scenario-of-all-identified-use-cases)
4. [Class and Package Diagrams](#4-class-and-package-diagrams)
5. [State-Transition Diagram](#5-state-transition-diagram)
6. [Plannings of the different iterations](#6-plannings-of-the-different-iterations)
7. [Architectural and Design Patterns choices](#7-architectural-and-design-patterns-choices)
8. [Why JUNIT?](#8-why-junit)
9. [Discussion on Social and Environmental Issues related to the application](#9-discussion-on-social-and-environmental-issues-related-to-the-application)
10. [Technical and Human Review](#10-technical-and-human-review)
11. [Possible Improvements](#11-possible-improvements)
12. [Glossary](#12-glossary)

## 1. Introduction
This report documents the Agile development process of the PLD Agile project. The project’s goal is to develop an application for optimizing delivery tours in urban settings using bicycles. Embracing Agile and SCRUM methodologies, the focus is on iterative development, robust team collaboration, and high adaptability.

## 2. Use Case Diagram

Here's the use case diagram for our application:

![Use Case Diagram](useCase.png "Use Case Diagram")


By Manager UberIF, we mean the user that get incoming delivery requests, calculates routes from the warehouse and that manages the couriers.

## 3. Description of the main success scenario of all identified use cases

| USE CASE                  | Main Success Scenario                                                                                                                                                                                                                                                                                                                                                |
| ------------------------- |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Create Delivery Request   | 1. Manager chooses a courier<br>2. Manager selects an intersection and enters a time window<br>3. System checks and validates data<br>4. System creates the delivery request<br>5. System initiates the process of finding the best tour for the chosen courier. If the tour is feasible, the directions are printed in a PDF document for the courier. |
| Load City Map             | 1. Manager selects another city map to load in the dropdown list. By default, the small map is loaded. <br>2. System gets all the necessary information from an XML file<br>3. System display successfully the map                                                                                                                                                   |
| Modify Number of Couriers | 1. Manager clicks on “+” or “-” to modify the number of available couriers. If they click on "+", they enter the name of the new courier. If they click on "-", the last courier added is removed. <br>2. System adjusts the number of couriers                                                                                                                      |
| Save Tours                | 1. Manager clicks on saving the tours running at the moment<br>2. System saves tour details to an XML file<br>3. System acknowledges successful saving of the tour                                                                                                                                                                                                   |
| Restore Tours             | 1. Manager clicks on restoring the tours<br>2. Manager chooses the tours they want to restore<br>3. System retrieves and displays the selected tour details                                                                                                                                                                                                          |

## 4. Class and Package Diagrams

The aim of this diagram is to explain how we want to build our software to answer the client needs. It is important to note that this diagram constantly evolves and therefore only should be used to understand the general idea of how our software is structured. It is not the actual nor final version and doe not exactly explain all classes and packages.

![Class Diagram](classDiag.png "Class Diagram")

This diagram is organized into several packages, which group related classes and interfaces that interact with each other to perform various functions within the application.


### Description and Explanation of the Diagram
#### Models Package
Contains the core data structures and logic.

* **Delivery:** Represents a delivery request, including location, time window, and assigned courier.
* **CityMap:** Holds the graph data structure of the city, with intersections and road segments.
* **Courier:** Stores information about each courier, including their current tour.
* **Tour:** Manages the sequence of deliveries and the computation of the optimal route.

#### Views Package
Deals with the user interface and presentation logic.

* **Window:** The main window of the application.
* **GraphicalView:** Responsible for rendering the map and tours visually.
* **MouseListener, KeyboardListener, ButtonListener:** Handle user inputs.

#### Controllers Package
Manages the application flow and responds to user actions.

* **Controller:** The main controller that orchestrates the application’s behavior.
* **ListOfCommands:** Maintains a list of commands for undo/redo functionality.
* **Command:** An abstraction for actions that can be performed, such as adding or removing a delivery.

#### XMLMapParser
A utility class for parsing the city map from an XML file.

### Architecture and Design Patterns
* The architecture follows the **Model-View-Controller (MVC)** pattern, separating concerns and allowing for modular development and testing.
* **Observer pattern** is used within the Models package, allowing views to react to changes in the model.
* The **Command Pattern**  is implemented in the Controllers package, encapsulating actions as objects, enabling sophisticated control structures such as undo/redo.
* **State Pattern** is used for managing different states of the application, such as initial, address, and delete states.


## 5. State-Transition diagram

We think that there's no need for a state-transition diagram since our application only has one single state.

## 6. Plannings of the different iterations

For a sprint planning for a team of seven members over eight sessions, focusing on the inception phase of your project, it’s important to allocate tasks effectively to meet all deliverables. We had 8 Sessions with 4 hours per session.

### a. Sprint 1 Review
#### Initial Planning

* Being able to load the map of intersections with the file given at the beginning. - 4 hours
* Being able to compute the best route. - 12 hours
* Change the number of couriers. - 2 hours

#### Actual Planning

**Session 1**
* **All members:** Setting up project environment for development and conception. - 2 hours per member
* **All members:** Brainstorming session for identifying main use cases and initial architecture. - 1 hour per member
* **Tim:** Start working on Glossary. - 1 hour
* **Marie & Noham:** Use Case Diagram. - 1 hour
* **Mark & Hugo:** Sequence and Class Diagram. - 1 hour
* **Evann:** Architecture conception. - 1 hour
* **Zyad:** Sick

**Session 2**
* **All members:** Daily discussion on where everybody is at. Synthesis of 1st session results. - 30 min
* **Evann & Hugo:** Continuation of architecture conception. - 3 hours
* **Marie & Noham:** Structured description of selected use cases. - 1.5 hour
* **Noham:** Joined Mark to work on algorithms. - 1.5 hour
* **Tim & Zyad:** Conception of UX/UI. - 3 hours
* **Marie:** Joined the front end team to design and program the UI. - 1.5 hour
* **Mark:** Research on algorithms and loook at given code for TSP. - 3 hour
* **All members:** Recapitulative Discussion. - 30 min

**Session 3**
* **All members:** Daily discussion on where everybody is at. Synthesis of 1st session results. - 30 min
* **All members:** Revisited packages, validation and implementation of packages and classes. - 2 hours
* **Marie & Noham & Hugo & Evann:** Continued work on algorithms. - 1.5 hour
* **Tim & Marie:** Front End Development, correction of bugs related to displaying and the UI. - 1.5 hour
* **Hugo & Evann:** Debugging Git. - 1 hour
* **All members:** Recapitulative Discussion. - 10 min

**Session 4**
* **All members:** Daily discussion on where everybody is at. Synthesis of 2nd session results. - 30 min
* **Noham & Evann:** Debugging backend. - 1.5 hour
* **Hugo & Zyad & Tim:** Front End Debugging and background improvement. - 1.5 hour
* **All members:** Class meeting. - 2.5 hours
* **Marie & Mark:** Developing deliverables. - 1.5 hour

**Outside Sessions work**
* **Tim:** Development of V1 XML Parser. - 2 hours
* **Tim:** Development of map for UI. - 3 hours
* **All members:** Back End Development and Code Cleaning. - 5 hours per member

**Overall**
* Being able to load the map of intersections with the file given at the beginning. - 5 hours
* Being able to compute the best route. - 14 hours
* Change the number of couriers. - 1 hours

### b. Sprint 2 Review
#### Initial Planning
* Being able to compute and manage delivery requests within the right time window. - 4 hours
* Being able to display on the map the best tours. - 2 hours
* Being able to manage different states (going back with Control+Z). - 3 hours
* Being able to manage multiple couriers at the same time. - 2 hours
* Improving selection of the intersections when creating a delivery request. - 30 min
* Starting Unit Tests. - 2 hours

#### Actual Planning
* Being able to compute and manage delivery requests within the right time window. - 6 hours
* Being able to display on the map the best tours. - 3 hours
* Being able to manage different states (going back with Control+Z). - 4 hours
* Being able to manage multiple couriers at the same time. - 1 hour
* Improving selection of the intersections when creating a delivery request. - 30 min
* Starting Unit Tests. - 8 hours

### c. Sprint 3 Review
#### Initial Planning
* Being able to generate a PDF file with the road maps for the couriers. - 3 hours
* Being able to display the delivery routes with different colours for different couriers. - 30 min
* Being able to save/store tours in a file & choose a file in which saving / from which restoring the tours. - 2 hours
* Being able to compute best tours for more deliveries for one courier by adding heuristics and modifying the iterator. - 5 hours
* Being able to adapt to the map the displayed best tour when changing maps. - 1 hour
* Doing more Unit Tests. - 8 hours

#### Actual Planning
* Being able to generate a PDF file with the road maps for the couriers. - 5 hours
* Being able to display the delivery routes with different colours for different couriers. - 1 hour
* Being able to choose a file in which saving / from which restoring the tours. - 3 hours
* Being able to compute best tours for more deliveries for one courier by adding heuristics and modifying the iterator. - 3 hours
* Being able to adapt to the map the displayed best tour when changing maps. - 1.5 hour
* Doing more Unit Tests. - 8 hours

**Outside Sessions work**
* PDF file. - 2 hours
* Improving UI. - 4 hours
* Being able to save/store tours in a file. - 3 hours


## 7. Architectural and Design Patterns choices
The application employs the Model-View-Controller (MVC) architecture, pro- viding several advantages:

**Separation of Concerns:** MVC divides the application into three main components—Model, View, and Controller—facilitating ease of mainte- nance and code evolution.

**Ease of Maintenance:** Developers can work on individual components without affecting others. For example, UI design changes do not impact business logic.

**Parallel Development:** Teams can work simultaneously on different components, accelerating development.

**Code Reusability:** Models can often be reused across different views, and views with different controllers.

**Ease of Testing:** Clear separation simplifies unit testing and debugging. Components can be tested independently.

**Data Presentation Flexibility:** The separation allows the same data to be presented in different ways, useful for applications requiring diverse UIs.

**Adaptability and Scalability:** MVC provides flexibility to evolve and adapt the application to changing needs without a complete overhaul.

**Complex Interaction Handling:** Efficient management of complex in- teractions between UI and business logic, crucial for modern web applica- tions.

### Back End Choices

We decided to implement at first a Dijkstra because it was the easiest solution for the first iteration, and we then decided to keep it instead of implementing an A* algorithm because it would have taken too long for the result.

We thought about using four times our TSP function to get the optimal path for each TimeWindow and link all of them afterwards. This solution could have decreased considerably the time to compute a tour, so it could have increased the number of deliveries in a tour.

We still didn't use this solution because, in reality, it wasn't possible to compute a unique optimal solution. We then decided to keep our simple TSP and optimise it thanks to:

**An heuristic bound:** This heuristic allows us to not explore solutions that are obviously bad and wouldn't lead to an optimal solution.

**The function iterator:** In this function, we sort the deliveries to explore the ones with earlier time windows first, so we get an optimal solution faster.

## 8. Why JUNIT?

For unit testing, we will use JUnit, Mockito, and JFixture. Here is a simplified example of a unit test in Java using JFixture for test data generation and Mockito for mocking dependencies:

**Example for the classes:**

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

**Example of Unitary Tests:**

```java
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
        // Creation of a mock for UserRepository
        userRepositoryMock = mock(UserRepository.class);

        // Initialisation of UserService with the mock
        userService = new UserService(userRepositoryMock);

        // Initialisation of JFixture for the data generation
        fixture = new JFixture();

        // Creation of a user ID and of a User object
        userId = fixture.create(String.class);
        expectedUser = fixture.create(User.class);

        // Configuration of the behaviour of the mock
        when(userRepositoryMock.findById(userId)).thenReturn(expectedUser);
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        // Action: Calling of the method to test
        User result = userService.getUserById(userId);

        // Checking: Result must correspond to expected User Object
        assertEquals(expectedUser, result);

        // Checking that the mock has been called as expected
        verify(userRepositoryMock).findById(userId);
    }
}
```
In this example, JFixture is used for automatic instance creation, and Mock- ito for mocking UserRepository and configuring its behavior. The test verifies that userService.getUserById returns the expected User object and that the mock repository is called correctly.

## 9. Discussion on social and environmental issues related to the application

Using bicycles for urban delivery services is a great step towards addressing both social and environmental issues. Indeed, it can not only reduce urban congestion, but also contribute to reduced delivery times, which makes it more efficient overall. However, it's also important to take a look at negative impacts it could have.

Let's start with the positive sides of using our application. Firstly, it creates employment opportunities for people who know how to bike. As talked about just above, it promotes a greener and cleaner urban environment. In fact, it promotes an eco-friendly mode of transport, which prompts cities to improve their cycling infrastructure. This will then lead to a more environmentally friendly urban landscape, and a fight against both climate change and air pollution.

Another key point of the application is that it's open sourec, which allows everybody to correct it and improve it if needed, for instance to make it more accessible and ergonomic. This would also help to spread the concept in other cities.

Finally, this could help people that can't do their groceries, whether it's because of a lack of time, or a lack of means, to have their products delivered directly at their home.

Nonetheless, our application could raise some important questions about the working conditions and rights of the couriers. Fair treatment and compensation should be at the heart of the discussions. What's more, even though accessibility should be considered in the app's design, for now, it excludes individuals with disabilities such as bad vision for instance. And the application can't be used by people who don't have credit cards or smartphones.

Sadly, this application brings the problem of increased consumption of single-use plastic, which is obviously not a good thing for our planet.

Lastly, one could say that this concept will definitely increase the price of products, making them less accessible for poor people.

## 10. Technical and Human Review
### Technical Review

Technically speaking, we haven't encountered a lot of problems in the three sprints we have made.

There are some features that we would have loved to implement, but that were too complicated. For instance, we would have liked to do more deliveries at the same time, or assign the courier to a delivery by computing who's the best one for this delivery, instead of choosing manually a courier before computing the road map.

Regarding the back-end language, we used Java. We think that this was a good language to use for the algorithms.

However, concerning the front-end language, we used JavaFX and it brought some problems such as the fact that it was harder to manipulate than HTML.

What's more, we separated the work into two teams: back-end and front-end team. This worked very well and we managed to work efficiently.

Nonetheless, we think that we could have worked differently regarding the management of the sprints. For instance, we should have implemented the use cases from the beginning to the end, and then improve it later on. Instead, we implemented multiple use cases at the same time, which isn't really Agile-like.

### Human Review

Regarding the human review, we loved working together. It was a great team and the mood was always good.

Everybody was always motivated to work, even at 8 AM. The task repartition was also great, and we worked evenly outside of class.

While team communication was generally direct and effective, it could have been improved from time to time. For instance, we could have communicated more effectively on where everybody was at during the sessions, and not only at the beginning of the sessions.

Team member satisfaction, with a mean score of 8, reflected a positive atmosphere. Importantly, no well-being concerns were raised, indicating a generally happy team.

Agile practices allowed team members to focus on their favourite areas of interest or desired learning.

## 11. Possible improvements

One of our biggest mistakes is that we didn't start by implementing the states (going back with the equivalent of Control + Z). Next time, we'll do it at the beginning.

We could have improved our application by using SOLID principles. For instance, we should have done 1 class for 1 responsibility. What's more, we should have injected dependencies so that high-level modules didn't depend on low-level modules. Both should depend on abstractions. By using these principles, we could have gotten more test coverage.

Also, our restoring of tours is not optimized. It doesn't automatically change map and update the list of couriers with the information contained in the file. For instance, if the user is on the small map and the restored tours were on the large map, it won't work.

What we could have done also is display departure and arrival time for each delivery.

What's more, by working more on the algorithms, we could have increased the number of delivery by courier. For now, a courier can only deliver 15 points at a time.

Lastly, we could have improved the algorithms even more so that it would compute the delivery tours for all couriers, and not only consider a courier one by one.

## 12. Glossary

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