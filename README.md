# RoboRally

## Running the game from IntelliJ

1. Clone repository and open it in IntelliJ.
1. Make sure you have Maven installed and enabled in IntelliJ.
1. Make sure you use a configuration with Java 8.
1. Run Main.

## Known bugs

- Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on Windows machines, but you may have to use Java 8 on Linux.

## Testing

There are three different methods of testing used in this project:
Manual tests, unit tests and assertions.

Unit tests are located in `src/test`.

Manual tests are documented in `ManualTests.md`.

Runtime assertions can be enabled with the Java VM flag "-ea".

## Controls

- WASD: Manual movement card injection (for debugging).
- 1-9: Stage cards
- Shift + 1-5: Unstage cards
- Return: Commit cards

## Compatibility

The game has been tested on Windows and Linux, it should also run on MAC as Java runs on all platforms. As of now we do not have access to a Mac so we cannot test the game on a Mac.
