# RoboRally
## Running the game from IntelliJ
1. Clone repository and open it in IntelliJ.
1. Make sure you have Maven installed and enabled in IntelliJ.
1. Make sure you use a configuration with Java 8.
1. Run Main.

## Known bugs
- Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on Windows machines, but you may have to use Java 8 on Linux.
- The game crashes when you are trying to move outside the board and then back in.
- Walls currently does not restrict movement with cards if you move more than one step.

## Testing

There are three different methods of testing used in this project:
Manual tests, unit tests and assertions.

Unit tests are located in `src/test`.

Manual tests are documented in `ManualTests.md`.

Runtime assertions can be enabled with the Java VM flag "-ea".

## Controls
Arrow keys - Manually move player
WASD + F - Manually execute movement cards, see Game.keydown() line 69 - 73 for more info

## Compatibility
The game has been tested on Windows and Linux, it should also run on MAC as Java runs on all platforms. As of now we do not have access to a Mac so we cannot test the game on a Mac.
