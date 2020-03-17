# Tower-Defense-Game
<b>Intitial Tasks due by March 25</b>

<b>Goal:</b> Get the game working with a single enemy & tower type with simple (non-animated) movement and straight paths.
 
<b>In the Professor's words:</b>
> You should spend some time deciding on your class hierarchy and structure. You will supply
simple CRC cards (or a reasonable facsimile) of your design. The majority of the
requirements are outlined below. For this first version you only need implement the most
basic enemy and tower type. Towers are fixed, and enemies follow a basic movement
strategy along the path. No animation is required for enemies or towers.

<b>Tasks:</b>
* [ ] Develop initial class hierarchy and control structure
* [ ] Create CRC cards
* [ ] Implement "GameWorld" which holds a collection of game objects and other state variables
* [ ] Implement a top-level Game class to manage the flow of controling the game (touch, manipulate data, etc)
* [ ] Implement a GameView class which will assume the responsibility of displaying information about the state of the game
* [ ] Implement abstract GameObject (implement fixed & moveable interfaces for different types
* [ ] Implement a single tower type. Not rotational and with a fixed location and cost.
* [ ] Implement a single enemy type. That follows a basic movement strategy
* [ ] Implement a simple GUI that holds basic game info. In addition, tower selection is done here. We will not use buttons but simply rectangular regions<br/>

<b>Notes:</b>
* Enemies must implement various strategies, I.E. movement and/or attack strategies.
* Enemies will be constructed using a factory pattern. A strategy will be chosen during this initial construction, and other properties set.
* All game objects have an attribute size. The size can be retrieved (getter) but can be changed once created.
* All game objects have a location (x, y point) as the center of the object. GameObjects location can be retrieved (getter) and/or set (setter). Unless explicitly stated that a location cannot be changed once created.
* GameObjects that implement the movable interface must have integer attributes for heading and speed. Telling an object to move() causes the object to move based on this information. All objects use their strategy to move. Heading is specified by a compass angle in degrees (90 = east, 0 = north).
* No animation is required for this first part of the assignment.
* The game world does not need to extend pass the visible display for the first part of the assignment.
* The "gameMap" consists of a background image with pre-defined paths for enemies.
* Towers are bought using an in-game currency or resource.
* The player can pause and resume the game.
* The player will be presented with a win/loss message when appropriate.

<b>For documentation purposes:<b></br>
https://docs.google.com/document/d/1jzjNmYaw-pGcg-SYwLC3hfwqSHZ0u_GVHPIjyIMxmWo/edit?usp=sharing
 </br>
 </br>
 <b>For clarifying hierarchy:<b></br>
![](Images/tower.png)</br>
see Image folder for uml diagram
