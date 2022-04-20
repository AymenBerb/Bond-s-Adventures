# Bond-s-Adventures
Bond's Adventures is a text-based game, based on the framework of ‘The Word of Zuul Adventure’. The player embodies James Bond, a high level agent which is appointed to complete a high-risk mission for the sake of the CIA. The mission consist of finding and killing a well known murderer in a house. Thus, the map of the game consists of common types of rooms that you can find in a house. In total, there are 10 rooms and 3 floors. Kryzko, the serial-killer, is sleeping in a locked room, while his guard is moving around the house to watch-out for any intruder. There are a total of 8 items spread among all rooms. They can vary from common objects (i.e tv, paint, keys, knife), to weapons (i.e gun, ammo, poison). James Bond is carrying with him a backpack that serves as the player’s inventory. However, he can carry with him up to a certain limited amount of weight (in the game it is set to 1600 grams), to be the lightest and the fastest possible. Therefore, the player should be careful on the usefulness of the items he takes. At the beginning of the game, James Bond has no weapon on him, and thus, the player should find weapons in the house before being found by the guard, otherwise the game is lost. As it is a text-based game, the game is played through commands entered by the player. The game recognizes a list of well defined commands that can be shown using the ‘help’ command.

**Implementation features:**
In total there are 11 classes composing the game. First I created the Player class which holds all the characteristics of the player. That is to say, its current room, its previous room, its inventory and a boolean isKilled to know whether or not the player is still alive. The classes Room, Item, Character, CharacterManger and Inventory are responsible for the game structure. The classes Parser, Command and CommandWords are responsible for reading an input from the player and interpret it as an command. Then there is the Game class, which initializes the game structure and executes all commands received, and the Launcher class which runs the game when executed. Finally we have the SoundEffect class which is at the origin of all sounds heard in the game. I implemented three sounds: one background sound that keeps playing while the game is on, an opening door sound effect that appears every time the player changes room and finally a scary deep voice that appears when Kryzko is found.

Base Task completed and How ?
● The game has several locations/rooms
I created a Room Class that holds all the characteristics of a room, that is to say its: name, description, items, exits and key. It also holds a boolean that shows whether or not the room is locked. All rooms are instantiated in the Game Class and stored in a HashMap called roomHashMap with the string name of the room as the key and the room object as the value.
● The player can walk through the locations.
Each room has one or more exits, which are instantiated in the Game Class and stored in a HashMap in the Room Class with directions as its keys and exit rooms as its values. There are 6 possible directions: “west”, “east”, “south”, “north”, “down” and “up”. To move from a room to another, a go command has been implemented. It reads the direction written and puts the player in the wanted room.
● There are items in some rooms. Some items can be picked up by the player, others can’t.
I first created an Item Class that holds the name, the description and the weight of each item object. I instantiated each item in the Game Class and then spread the items among all the rooms. To do so, I created for each room a HashMap containing all items of the room, with the name of the items as its keys and the items objects as its values. A boolean canBeCarried stores whether or not the item can be carried.
● The player can carry some items with him. The player can carry items up to a certain total weight.
I created an Inventory Class. Each Inventory object holds a HashMap of the name of the items contained as keys and the items objects as values. It also holds a maximum weight which can be carried in the inventory. The player inventory is set in the Game Class at 1600 grams. In the inventory class I implemented a method that returns a boolean that tells whether or not an item can be added to the inventory by comparing the max weight and the inventory weight + the weight of the item wanted to grab. It is used in the grab method inside the Game Class.
● The player can win
In the main play loop in the Game Class. I added an if statement that checks after each command if kryzko is dead, if yes the boolean is true and the player wins !
● Implement a command “back” that takes you back to the last room you’ve been in.
To implement the back command I added a field, previousRoom, to the Player class that stores the room that the player was in previously, retrieving this information from the “go room” method in the Game Class. The “back” method sets the current room of the player to its previous room. And if the player has no previous room he gets noticed.
● Add at least four new commands
In total I added 9 new commands. What I did is that, for each new command I wanted to add, I added the command word and its description in the commandWords HashMap inside the CommandWords Class. Then inside the Game Class I implemented a new method linked to the newly created command Word by the ‘processCommand’ method so that each time the common word is typed the command the program knows which command to execute. All new commands can be found by typing the help command.
Challenge Tasks completed and How ?
● Add characters to your game. They can move around by themselves.
For this task, I created a new class named Character. Each object of this class holds a name, a description, an inventory, a currentRoom and a boolean isKilled. The class Character is very similar to the Player class, in that it has approximately the same fields and methods. However, characters are not controlled by the player and some of them move around by themselves, thanks to a method named ‘moveRandomly’.
This method is implemented inside another Class : CharacterManager that holds a HashMap which combines each character with a room. Basically, what I did is that I’ve created a ‘generateRandomExit’ method inside my Room class that I use in my ‘moveRandomly’ method. This method allows me to get a random exit among the exits of the character’s current room, then I remove the character from its current room and I add it into the new randomly generated room. The ‘moveRandomly’ method is called when the player changes rooms.
● Extend the praser to recognize three-word commands.
I just used the initial framework of the Parser Class and added a third field ‘thirdword’ in the Command Class.
● Add a magic transporter room
To do so, I added a HashMap field to the Game Class so as to store all unlocked rooms of the game. Then, I created a method to generate a random room and a ‘teleportPlayer’ method that teleports the player to the random room generated.
Additional challenge tasks :
● Having the possibility to begin the game with locked rooms that can be opened using unique keys. Each room has its own keys. I also implemented a new ‘open’ command to open the room if the inventory of the player contains the corresponding key.

● Implemented background sound & sound effects by importing SoundEffect Class from the internet. Code Quality Example :
**Coupling**
I considered coupling when I created my characterManager Class. What happened is that I wanted my Character Class to have a current room field so that I can access any character's current room easily. However, when I was doing the method that moves characters randomly, I realized that I also needed to add a field named “characterIn Room” in my Room Class. As a consequence, my Room and Character classes were really tightly coupled. So I decided to create a new class that I called characterManager which has as a field a HashMap containing rooms as its keys and Characters as its values. The HashMap stores all characters’ positions in the map. Thanks to this Class I no longer encountered any difficulties in trying to make my ‘moveRandomly' method and I have reduced to nothing the interdependence of both my Room and Character classes.

**Cohesion**
I considered cohesion when I wanted to create an inventory for my player. At first, what I did is that I created an object of the Room class that played the role of an inventory. The unique difference that it had with the others same class objects, it’s that it was not holding any exits but rather only items. But then, I had to implement all the methods that were useful only for the inventory object and were not used at all by all the other ‘true’ room objects of the class. At this point, I thought that everything would get messy, so I decided to create a new Inventory class especially reserved for the characteristics of the inventory and all its methods. Hence, increasing cohesion and lowering confusion of my Room Class.

**Responsibility-driven design**
I considered RDD for my ‘teleportPlayer’ method for the transporter room. At first, I created the method inside the Player Class, thinking that it concerned the player solely. However, when I wanted to get the randomly generated room I couldn't because the data was in the Game Class. Hence, I decided to put the ‘teleportPlayer’ method in the Game Class, where the data is easily accessible.

**Maintainability**
I considered maintainability in the character class. In fact, it is quite easy to extend this class with new functionalities. At first, characters had not the boolean isKilled, I added it afterwards and it didn’t impact badly any part of the code. Also, it is really easy to create new characters, as they just have to be instantiated in the constructor method of the game class.

**The Walk-Through**
There are many ways of completing the game. One way is by entering the following command in order.

Go north 
Go east 
Grab ammo 
back
Go north 
Explore 
Grab gun 
Go up 
Grab key 
Back
Go south
Go down
Open west
Go west
Kill kryzko or Give kryzko poison

If the guard is encountered at any time during the game: 16. Kill guard or give guard poison
