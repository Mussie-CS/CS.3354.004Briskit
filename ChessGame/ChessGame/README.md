# Chess Game

## Getting Started

Welcome to Chess Game, a Java-based implementation of the classic game of chess developed in Visual Studio Code. This project demonstrates object-oriented programming concepts such as inheritance, abstraction, encapsulation, and polymorphism while providing a fully playable two-player chess experience.

### Authors

- Mussie Wade
- Kerrie Means

---

## Folder Structure

The workspace contains the following folders:

- `src`: the folder containing all source code
- `bin`: the folder containing compiled Java class files
- `board`: classes for board management and positions
- `pieces`: classes representing chess pieces and movement rules
- `game`: classes managing gameplay and players
- `utils`: helper methods used throughout the project

The project structure is organized as follows:

```text
src
├── board
│   ├── board.java
│   └── position.java
│
├── game
│   ├── game.java
│   └── player.java
│
├── pieces
│   ├── pieces.java
│   ├── pawn.java
│   ├── rook.java
│   ├── knight.java
│   ├── bishop.java
│   ├── queen.java
│   ├── king.java
│   └── color.java
│
├── utils
│   └── utils.java
│
└── Main.java
```

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

---

## Environment & Dependencies

### Requirements

- Java JDK 17 or newer
- Visual Studio Code
- Java Extension Pack for VS Code

### Build Tool

This project uses the standard Java compiler (`javac`) included with the JDK. No external libraries or dependency managers are required.

---

## Running the Program

### Using VS Code

1. Open the project folder in Visual Studio Code.
2. Open `Main.java`.
3. Click the **Run** button in the upper-right corner.

### Using Terminal

Compile and run the game with a single command:

```bash
javac -d bin src/**/*.java && java -cp bin Main
```
***-

## How to Play

The game is p***ed by two players taking turns.
***# Move Input

Moves are entered ***ng standard chess coordinates:

***text
E2 E4
```

This moves a pie***from square E2 to square E4.

###Controls

- Enter the starting sq***e followed by the destination square.
- Press Enter to submit a move.
- Invalid moves will be rejected and the player will be prompted again.

### Objective

- Capture oppo***t pieces.
- Protect your king from attack.
- Place the opponent's king in checkmate to win the game.


## Feature List

### Implemented Features

- [x] 8×8 chess board
- [x] Object-oriented class structu***- [x] Position tracking
- [x] Tu***based gameplay
- [x] Move valida***n
- [x] Pawn movement
- [x] Rook***vement
- [x] Knight movement
- [x] Bishop movement
- [x] Queen move***t
- [x] King movement
- [x] Piece capturing
- [x] Input validation
- [x] Move parsing utilities
- [x] Board display
- [x] Check detection
- [x] Checkmate detection

### Not Implemented

- [ ] AI opponent
- [ ] Online multiplayer
- [ ] Save and load functionality
- [ ] Graphical usser interface (GUI)

### Known Limitations

- Console-based gameplay only
- Requires two human players
- Games cannot be saved between sessions

---

## Object-Oriented Design 

### Main Classes

#### board
Stores and manages the 8×8 chess board

#### position
Represents a location on the board.

#### pieces
Abstract base class for all chess pieces.

#### pawn, rook, knight, bishup, queen, king
Individual chess piece implementations with their own movement rules.

#### player
Stores player information.

#### game
Controls gameplay, turns, and win conditions.

#### utils
Provides helper meathods such as move parsing and c**r formatting.

---

## Dependencies management

This project relies on on the Java Standard Library.

*** dependencies are provided throu***the installed Java Development Kit (JDK), so no additional setup or package installation is required.
