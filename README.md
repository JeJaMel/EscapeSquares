# EscapeSquares

EscapeSquares is a simple yet challenging game developed as the first project for the Object-Oriented Programming course at Rafael Urdaneta University.

## Game Description

The game is based on the concept of survival. The player has to avoid the enemy as long as possible and reach the goal. The game is divided into rounds, with each round consisting of three moves.

## Game Modes

The game currently features two modes:

1. **Easy Mode**: This mode presents a 7x10 grid where the goal is to survive as long as possible against the enemy and reach the goal.

2. **Normal Mode**: This mode presents a more challenging 4x6 grid.

## Gameplay

The speed of the enemy can be adjusted in the code with the `enemyTimeline` variable:

```java
enemyTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
```
## Development

EscapeSquares is developed using JavaFX, making it a great project for learning about game development with this framework.

I recommend using a Java IDE such as IntelliJ IDEA or Eclipse for development.

## Getting Started

To get a local copy up and running follow these simple steps:

1. Clone the repo `git clone https://github.com/JeJaMel/EscapeSquares.git`
2. Open the project in your preferred Java IDE
3. Run the `main` function

## Contributing

Contributions are welcome! Please feel free to submit a pull request.

## License

[MIT](https://choosealicense.com/licenses/mit/)
