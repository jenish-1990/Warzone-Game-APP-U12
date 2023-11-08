
# Risk Game Project

This project involves building a Java program for a simplified version of the classic "Risk" board game. The game is designed to be played via the command line interface, following the rules and map structures similar to the "Warzone" version of Risk, which can be found at [Warzone](https://www.warzone.com/).

## Project Overview
- Team Members : Alay Parikh, Jenish Akhed, Ayush Patel, Sahil Khunt, Nidhi Patel and Shruti Pavasiya 
- Project Number: U12

The objective of the game is to conquer all countries on the map. It consists of the following main components:

- **Map:** Represented as a connected graph, where each node is a country and each edge signifies adjacency between countries. Continents are defined subgraphs, with control values determining the number of armies given to a player who controls an entire continent.

- **Game:** The game involves a startup phase where players are assigned territories randomly, followed by a turn-based main play phase where players issue different types of orders (deployment, advance, special orders using cards). The main goal is to conquer territories and eliminate opponents by simulating battles.

## Features

The implemented program is expected to offer the following functionalities:

- Create and play on any connected graph defined by the user and saved as a text file representation.
- Manage territories, armies, and continent control values.
- Implement different types of orders: deployment, advancing armies, special orders (bomb, blockade, airlift, negotiate).
- Simulate battles and apply rules for conquering territories.
- Card-based actions influencing the game (bomb, reinforcement, blockade, airlift, diplomacy).

## Implementation

- **Languages:** Java
- **Tools:** IntelliJ Idea IDE, Junit, Javadocs
- **Design Decisions:** MVC Architecture
- **Structure:** Code is distributed into Model, View, Controller

## Usage
    ##Clone the Repository:
    - git clone https://github.com/your-username/risk-game.git
    ##Navigate to the Project Directory:
    - cd warzone/service/
    ##Compile the Java Code:
    - javac GameEngine.java
    ##Run the Game:
    - java GameEngine

## References

- [Warzone](https://www.warzone.com/)
- [Domination game maps](https://domination.sourceforge.io/getmaps.shtml)
- [Warzone wiki](https://www.warzone.com/wiki/Main_Page)

