# 🎮 Warzone Game App U12

## Project Overview

**Warzone Game App** is a turn-based strategy game developed for COMP 6481 (Software Engineering) at Concordia University. The objective of the game is to dominate a map by conquering territories and eliminating opponents. The game is structured around phases such as reinforcement, attack, and fortification, mimicking the logic of the classic Risk board game.

The project emphasizes software engineering principles such as modular design, test-driven development, MVC architecture, and SOLID principles.

## Features

- **Map Editor**: Create, load, and validate custom maps (.map format).
- **Player Modes**: Supports both human and AI players (aggressive, benevolent, random, cheater).
- **Game Phases**: Reinforcement, Attack, and Fortification with clear transitions.
- **Save/Load Game**: Game state persistence using serialization and file I/O.
- **Tournament Mode**: Run automated games across multiple maps with multiple AI strategies.
- **Graphical User Interface**: Built with Java Swing for game interaction.
- **Observer Pattern**: Used to update different game views in real-time.
- **Logging**: Detailed log output to track game events.

## Technology Stack

- **Language**: Java
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA
- **Testing**: JUnit 5
- **UI**: Java Swing
- **Design Patterns**: MVC, Observer, Strategy, Singleton, Builder
- **Serialization**: Java’s built-in object serialization for saving/loading game state

## Project Structure

```plaintext
Warzone-Game-APP-U12/
├── src/
│   ├── Controller/         # Handles logic and state transitions
│   ├── Model/              # Contains core data classes (e.g., Country, Player, Map)
│   ├── View/               # Swing GUI implementation
│   ├── Resources/          # Map files, assets
│   └── Main.java           # Entry point of the application
├── test/                   # JUnit test cases
├── maps/                   # Sample and custom .map files
├── logs/                   # Game logs
├── savedgames/             # Serialized game states
├── README.md
├── pom.xml                 # Maven project descriptor
└── .gitignore
```

## Installation Instructions

### Prerequisites

- Java 17 or later
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

### Setup Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/Warzone-Game-APP-U12.git
   cd Warzone-Game-APP-U12
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run the Game**
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

   Alternatively, run `Main.java` from your IDE.

## Configuration

- Maps should be placed inside the `maps/` folder with `.map` extension.
- Saved games are stored in `savedgames/` using Java serialization.
- Logs are stored in `logs/` and appended each game session.
- Tournament configurations are set via the GUI prompt.

## Usage Guide

### Gameplay Flow

1. **Start Game**: Choose between single game or tournament mode.
2. **Player Setup**: Add human or AI players and assign strategies.
3. **Map Selection**: Load or create a map using the map editor.
4. **Game Phases**:
   - **Reinforcement**: Allocate armies to owned territories.
   - **Attack**: Attempt to conquer neighboring countries.
   - **Fortification**: Move armies strategically between controlled countries.
5. **Victory Condition**: A player wins by controlling the entire map.

### Controls

- Use buttons in the GUI to advance phases.
- Dropdowns and dialogs guide map selection, player setup, and game saving/loading.

## Development Guide

- Follow the **MVC pattern** strictly for controller, view, and model interactions.
- All new classes must follow **SOLID principles**.
- For adding new strategies, implement the `Strategy` interface and inject via the player controller.
- All new features must include accompanying JUnit tests in the `/test` directory.
- Use the `Logger` class for consistent logging across modules.

## Testing

### Unit Tests

Run the test suite using:

```bash
mvn test
```

Includes tests for:
- Map validation and loading
- Game state transitions
- Phase logic (attack, reinforce, fortify)
- Strategy behavior for AI
- Serialization and deserialization

## Deployment

- Build executable JAR:
  ```bash
  mvn package
  ```

- Run with:
  ```bash
  java -jar target/warzone-game-app-u12-1.0-SNAPSHOT.jar
  ```

## Contribution Guidelines

1. Fork the repo and create your branch.
2. Add meaningful commit messages.
3. Ensure all new features are documented and tested.
4. Follow naming conventions and comment major classes/methods.
5. Submit a pull request after testing your changes locally.

---

> Developed as a group project for COMP 6481 - Software Engineering  
> Concordia University | Fall 2023  
> Authors: [Jenish Akhed, Shruti Pavasiya,  Nidhi Patel, Alay Parikh, Ayush Patel, Sahil Khunt]
