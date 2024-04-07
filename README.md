# Dice-Game: An Odds-Based Betting Game

Dice-Game is a simple, yet engaging betting game developed with Kotlin and Spring Boot. Players can register, place bets on numbers between 1 and 10, and win credits based on the odds. The game is designed to be simple, allowing for quick registration and betting actions through a REST API.

## Getting Started

This guide will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- JDK 17
- Maven

### Installing

First, clone the repository to your local machine:

```bash
git clone https://github.com/aaddyy227/Dice-Game.git
```

Change to the project directory:

```bash
cd Dice-Game
```

Build the project with Maven:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start and be accessible at http://localhost:8080.

## Usage

The game provides several REST API endpoints for player interactions:

### Player Registration

Register a new player:
```bash 
POST /players/register
```
```json
{
  "name": "John",
  "surname": "Doe",
  "username": "johndoe"
}
```

### Place a Bet

Place a bet with a `playerId`, `betAmount`, and `chosenNumber`:

```bash
POST "/game/placebet"
```

```json
{
  "playerId": 1,
  "betAmount": 100,
  "chosenNumber": 7
}
```

### Leaderboard

Get the current leaderboard showing top players:

```bash
GET /game/leaderboard
```

### Player Transactions

View a player's transactions:

```bash
GET /players/{playerId}/transactions
```

### View All Players

Get a list of all registered players:

```bash
GET /players/all
```

## Running the Tests

Execute the unit tests with:

```bash
mvn test
```

This command runs all the tests included in the project and outputs the results.

## Built With

- **Kotlin** - The programming language.
- **Spring Boot** - The framework for creating stand-alone, production-grade Spring based Applications.
- **H2 Database** - An in-memory database used for the project.
- **Maven** - Dependency Management.

## Authors

- **Adrian Zimbran** - *Initial work* - [aaddyy227](https://github.com/aaddyy227)
