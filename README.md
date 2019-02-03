# Simple Shooter

##### A simple shooter 2D game made in Java with Swing for a school assignment 

- [Latest Release (.jar)](https://github.com/johnvictorfs/simple-shooter/releases/latest)

***

- Pressing Enter on the Start Screen menu starts the Game

- Player can movement anywhere in the screen with the Arrow keys of the keyboard and shoot with the Spacebar

- A NPC dies when shot with a fireball

- NPC's move randomly on the upper part of the screen, and shoot at random intervals at the player, the player dies if a NPC shoots him

- Global player score is saved in a .dat file everytime a user gains score, global score is loaded at start

- Game score is only retained for a game session, and resets if a player dies

|           Start Screen         |           Gameplay GIF             |
| ------------------------------ | ---------------------------------- |
| ![start_screen][start_screen]  | ![shooter-gameplay][gameplay_gif]  |


[start_screen]: https://user-images.githubusercontent.com/37747572/52179720-6b5b6f00-27bc-11e9-9955-3cf120bd8fa6.png

[gameplay_gif]: https://user-images.githubusercontent.com/37747572/52179719-6b5b6f00-27bc-11e9-9239-ca3d4c267a0a.gif

***

## Building

- Intellij IDEA
    - Run with `Alt + Shift + F10`
    - Select `0 (Edit configurations)`
    - Use `com.game.Main` as the Main Class
    - Run main with `Shift + F10`
