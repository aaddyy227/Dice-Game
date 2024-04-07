Dice-Game: An Odds-Based Betting Game
Dice-Game is a simple, yet engaging betting game developed with Kotlin and Spring Boot. Players can register, place bets on numbers between 1 and 10, and win credits based on the odds. The game is designed to be simple, allowing for quick registration and betting actions through a REST API.

Getting Started
This guide will help you get a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
JDK 17
Maven

Installing
First, clone the repository to your local machine:

bash
Copy code
git clone https://github.com/aaddyy227/Dice-Game.git
Change to the project directory:

bash
Copy code
cd Dice-Game
Build the project with Maven:

Copy code
mvn clean install
Run the application:

arduino
Copy code
mvn spring-boot:run
The application will start and be accessible at http://localhost:8080.

Usage
The game provides several REST API endpoints for player interactions:

Player Registration
Register a new player:

arduino 
Copy code
POST /players/register
{
"name": "John",
"surname": "Doe",
"username": "johndoe"
}
Place a Bet
Place a bet with a playerId, betAmount, and chosenNumber:

bash
Copy code
POST /game/placebet
{
"playerId": 1,
"betAmount": 100,
"chosenNumber": 7
}
Leaderboard
Get the current leaderboard showing top players:

bash
Copy code
GET /game/leaderboard
Player Transactions
View a player's transactions:

bash
Copy code
GET /players/{playerId}/transactions
View All Players
Get a list of all registered players:

bash
Copy code
GET /players/all
Running the Tests
Execute the unit tests with:

bash
Copy code
mvn test
This command runs all the tests included in the project and outputs the results.

Built With
Kotlin - The programming language.
Spring Boot - The framework for creating stand-alone, production-grade Spring based Applications.
H2 Database - An in-memory database used for the project.
Maven - Dependency Management.

Authors
Adrian Zimbran - aaddyy227
