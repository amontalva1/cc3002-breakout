# cc3002-breakout
GUI implementation of a Breakout game. The objective is to link the logic with the GUI with no modifications on the logic previously made.

### Prerequisites
To run and test this program you need JDK 8, FXGL at the 0.5.4 version and Junit 4.12.

### Installing
-First clone this project into your computer.
-Open the project with your favourite IDE, ItelliJ recommended.
-Run any test in the Test directory to make sure everything works.
-If the test works fine, then you succesfully installed the program!:D

## Authors
* **Juan-Pablo Silva** - *Made the template of the structure of the program*
* **Antonio Montalva** - *Implemented every class needed to make the facade work*

## Methodology
The purpose of this program is to test the knowledge adquired in the classes of the CC-3002 Metodologías de
Diseño y Programación. That beign said the specific topics in this program are the creation of the graphical user interface for our last program, which had the logic of this game and was used without modifications in this program, to put in practice the facade design pattern which separates the logic and the gui of projects.

## Game Controls
Once the game is launched, you'll find a blue rectangle on the bottom of the screen (the player), and a ball on it. The current score will be shown in the middle of the screen and once the game started on the left will be displayed the game's ball left.
- The 'N' button will add a level to the current game, and if there are no levels being displayed on the screen it'll also display the first level to be played.
- Once there are bricks on the screen the 'Space-bar' button will launch the ball.
- You can control the player with 'A' and 'D' to move left and right.
- The 'L' button will disable the test-mode for the bricks on screen.

## Features
The chosen Mayor Features used in the game were the display of different states for damaged bricks, which in this case change the texture of the metal bricks when it's remaining hits are 7 and 3, and the texture of the wooden brick on 1 remaining hit. The other Mayor feature implemented was the Testing feature, which in this case consist on the simulation of hits on brick with a mouse's click. This last implementation has an issue, an started game is already in test-mode with bricks ready to be clicked, however once a brick is clicked all the other controls are disabled, even the 'restart game' button. So once you're ready to begin the test(by start clicking) make sure you have all the levels you want to play already, and on finishing the game by winning every level you'll need to re-launch the game to play again. Other little issue with the testing is the fact that you cannot click a brick twice in a row, once you click one brick you wont be able to damage the same again inmediatly, you'll need to attack another brick in order to be able to attack the same again, this could be a BIG problem if you end up a level with a single wooden brick with more than 1 remaining hit, since you wont be able to win the level, however you can easily avoid this problem making good use of every brick available on the screen. Also there's a keybind 'L' which makes every brick on screen unclickable, to avoid start a test-mode by accident.
The minor features implemented are first the sound of collisions of the ball with different bricks, the player and the walls, and second the crashing animation of the ball hitting the bricks. The first one works with no problems at all, but the second one makes use of particles built in the library which are not the prettiest animations, and for some reason it wont show up in every click-test situation, but runs smoothly in a normal game.
