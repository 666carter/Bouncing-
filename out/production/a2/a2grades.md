# CS349 A2

Student: j286zhao
Marker: Blaine

\$$$ TAs: This will be autofilled; no need to input. $$\$
Total: 86 / 100 (86.00%)

Code:
(CO: won’t compile, CR: crashes, FR: UI freezes/unresponsive, NS: not submitted)

\$$$ Notes must be between the backticks but can contain newlines. $$\$
Notes: `Your assignment didn't run because: Exception in thread "AWT-EventQueue-0" java.lang.ArithmeticException: / by zero`

## TECHNICAL REQUIREMENTS (30)

1. [4/5] Basic Requirements

-2 You had two unused imports that broke your build:

import com.sun.org.apache.xpath.internal.operations.Mod;

import javafx.scene.shape.Line;

2. [10/10] Handling Command-Line Parameters

3. [10/10] Window Behaviour

4) [2/5] Controls.

-3 and keyboard are used as input to control the game. The controls should be responsive enough to support gameplay.

## GAMEPLAY (40)

5. [5/5] The program opens with a splash screen, displaying the student's name and ID are displayed. Instructions for playing the game (key usage) are also displayed.

6. [10/10] There are at least 5 rows of coloured blocks arranged at the top of the screen, and a paddle at the bottom that can be moved left or right.

7) [5/5] When the game starts, the ball starts to move across the screen.

8) [5/5] The ball bounces when struck by the paddle, or when striking a brick, roughly conforming to the reflection law.

9) [5/5] If the ball hits a block, the block disappears, and the ball bounces.

10) [5/5] The game ends when the ball touches the bottom of the screen.

11) [5/5] There is a score system rewarding bounces of the ball or hits of the block. The score is displayed and updated in real-time when the ball hits the paddle (optionally, it can score for other factors, like hitting bricks).

## MODEL-VIEW-CONTROLLER (10)

12. [5/5] A model class exists which manages the animation timer and updates game elements.

13. [5/5] A view class exists that handles drawing the bricks, paddle and ball.

## ADDITIONAL FEATURE (10)

14. [0/10] An additional feature at the student's choice is described and implemented. The feature must be a significant enhacement.

Examples:

- Adding extra levels that appear when the current level is cleared.
- Randomly selecting one of many levels, each with a different configuration, when the game is launched.
- Adding power-ups that can be earned (e.g. special block that makes the paddle wider for a short period of time).

## AESTHETICS (10)

15. [5/5] The interface design is aesthetically pleasing.
16. [5/5] The game is enjoyable to play (responsive paddle, ball speed that makes for exciting gameplay, no lag).
