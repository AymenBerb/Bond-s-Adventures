import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 *  This class is the main class of the James Bond game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, all characters and all items. Also, it creates the parser, starts the game,
 *  evaluates and executes the commands that the parser returns.
 * 
 * @author  Aymen Berbache, Michael Kölling and David J. Barnes
 * @version 2021.11.28
 */

public class Game {

    private final Parser parser;
    private final Player player;
    private final SoundEffect backgroundSound;
    private final SoundEffect openDoorSound;
    private final SoundEffect kryzkoSound;
    private final Character kryzko;
    private final Character kryzkoGuard;
    private final CharacterManager characterManager;
    private final HashMap<String,Room> unlockedRoomHashMap;
    /**
     * Create the game and initialise its internal structure (rooms, items, characters...).
     */
    public Game() {

        backgroundSound = new SoundEffect("Song.wav", 25.0);
        openDoorSound = new SoundEffect("door-3-open.wav", 10);
        kryzkoSound = new SoundEffect("Kryzko.wav", 10);
        kryzkoGuard = new Character("guard", "The guard of kryzko");
        kryzko = new Character("kryzko", "the serial killer");
        characterManager = new CharacterManager();
        unlockedRoomHashMap = new HashMap<>();
        player = new Player();
        parser = new Parser();
        createGameStructure();

    }


    /**
     * Create the game structure. That is to say instantiate the rooms and their exits, and assigns all the items to different rooms.
     */
    private void createGameStructure() {

        Room outside, hall, livingRoom, toilets, kitchen, attic, undergroundHall, laundryRoom, storageRoom, transporterRoom;

        // create the rooms
        outside = new Room("Outside", "outside the main entrance of the Scary House", false, null);
        hall = new Room("hall", "in the entry Hall", false, null);
        livingRoom = new Room("living room", "in the living room", false, null);
        kitchen = new Room("kitchen", "in the kitchen", false, null);
        toilets = new Room("toilets", "in the toilets", false, null);
        attic = new Room("attic", "in the attic", false, null);
        undergroundHall = new Room("underground hall", "in the underground hall", false, null);
        laundryRoom = new Room("laundry room", "in the laundry room", true, null);
        storageRoom = new Room("storage room", "in the storage room", false, null);
        transporterRoom = new Room("transporter room", "in the transporter room", false, null);

        // Add all unlocked rooms to the room HashMap
        unlockedRoomHashMap.put(outside.getName(),outside);
        unlockedRoomHashMap.put(hall.getName(),hall);
        unlockedRoomHashMap.put(livingRoom.getName(),livingRoom);
        unlockedRoomHashMap.put(kitchen.getName(),kitchen);
        unlockedRoomHashMap.put(toilets.getName(),toilets);
        unlockedRoomHashMap.put(attic.getName(),attic);
        unlockedRoomHashMap.put(undergroundHall.getName(),undergroundHall);
        unlockedRoomHashMap.put(storageRoom.getName(),storageRoom);
        unlockedRoomHashMap.put(laundryRoom.getName(),laundryRoom);
        unlockedRoomHashMap.put(transporterRoom.getName(),transporterRoom);


        // initialize room exits
        outside.setExit("north", hall);

        hall.setExit("west", livingRoom);
        hall.setExit("east", toilets);
        hall.setExit("north", kitchen);
        hall.setExit("south", outside);
        hall.setExit("down", undergroundHall);


        livingRoom.setExit("east", hall);

        kitchen.setExit("south", hall);
        kitchen.setExit("up", attic);

        attic.setExit("down", kitchen);

        undergroundHall.setExit("west", laundryRoom);
        undergroundHall.setExit("south", storageRoom);
        undergroundHall.setExit("up", hall);

        laundryRoom.setExit("east", undergroundHall);

        storageRoom.setExit("north", undergroundHall);

        toilets.setExit("west", hall);

        attic.setExit("north", transporterRoom);

        player.setCurrentRoom(outside);  // start the game of the player outside

        {
            Item paint, tv, key, ammunition, gun, knife, poison, vase;

            //create the items
            knife = new Item("knife", "butcher knife", 500, true);
            paint = new Item("paint", "Picasso paint", 800, true);
            tv = new Item("tv", "HD tv", 8000, false);
            key = new Item("key", "mysterious key", 400, true);
            ammunition = new Item("ammo", "gun munition", 200, true);
            gun = new Item("gun", "heavy gun", 1000, true);
            poison = new Item("poison","death poison",300, true);
            vase = new Item("vase","empty vase",500, false);

            //initialize items location
            livingRoom.addItem("tv", tv);
            livingRoom.addItem("paint", paint);
            toilets.addItem("ammo", ammunition);
            kitchen.addItem("gun", gun);
            attic.addItem("key", key);
            storageRoom.addItem("knife", knife);
            kitchen.addItem("poison",poison);
            hall.addItem("vase",vase);

            //Assign keys to rooms.
            laundryRoom.setKey(key);
        }

        {
            //Set the maximum weight of the player inventory
            player.getInventory().setMaxWeight(1600);
        }

        {   //Play the background music until the game ends
            backgroundSound.play(true);
        }

        {
            //Set the initial position of characters
            characterManager.setCharacterInRoom(storageRoom,kryzkoGuard);
            characterManager.setCharacterInRoom(laundryRoom,kryzko);
        }
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {

        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (player.isKilled() || kryzko.isKilled()) {
                finished = true;
            }
        }
        System.out.println("\nThank you for playing. Good bye.");
        backgroundSound.stop();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println("Welcome to the world of Bond, James Bond !");
        System.out.println();
        System.out.println("You are James Bond. You are on a high level mission. On your own.");
        System.out.println();
        System.out.println("Description of the mission: The police found a dead student body in the middle of Hyde Park.");
        System.out.println("They've investigated and found out that the person behind the crime was a well known serial killer in the region. His name: Robert Kryzko.");
        System.out.println("The CIA may have a clue on where to find Kryzko tonight. They expect him to be in an abandoned house, also called the 'Scary House' nowhere near the city.");
        System.out.println("They also know that he's not alone in the house, he is with one of his guard. The CIA has installed several motion detectors in the house before the mission.");
        System.out.println("They will thus keep you updated on the position of kryzko's guard during your mission. However the detectors have never discovered any movement of kryzko.");
        System.out.println();
        System.out.println("Your mission: find kryzko and kill him.");
        System.out.println();
        System.out.println("Beginning of the game:  ");
        System.out.println("You are " + player.getCurrentRoom().getShortDescription() + "," + " the next step is to dive in and explore the house, seeking for Kryzko.");
        System.out.println("But be aware! You forgot your weapons and thus need to find weapons inside the house before finding Kryzko, otherwise he will kill you.");
        System.out.println();
        System.out.println("Warning! You can carry around a maximum of " + player.getInventory().getMaxWeight() + " g on you.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getExitString());
        System.out.println();
        System.out.println("You can type 'help' at any moment during the game to see all the commands available.");
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("open")) {
            tryToOpenDoor(command);
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("explore")) {
            explore();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("grab")) {
            grabItem(command);
        }
        else if (commandWord.equals("weight")) {
            getWeight(command);
        }
        else if (commandWord.equals("throw")) {
            throwItem(command);
        }
        else if (commandWord.equals("show")) {
            showInventory();
        }
        else if (commandWord.equals("kill")) {
            tryToKill(command);
        }
        else if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("give")) {
            giveItem(command);
        }
         // else command not recognised.
        return wantToQuit;
    }
 
    // implementations of user commands:
    /**
     * Try to open the door of a room. If inventory of player contains key, open it. Else, print issue.
     * @param command The command to be processed.
     */
    private void tryToOpenDoor(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which door to open...
            System.out.println("Open where?");
            return;
        }
        String roomString = command.getSecondWord();
        Room roomToOpen = player.getCurrentRoom().getExit(roomString);

        if (roomToOpen == null) {
            System.out.println("There is no such exit!");
        }
        else if  (roomToOpen.isOpen()) {
            System.out.println("This room is not locked, you can enter it right away.");
        }
        else if (player.getInventory().containsItem(roomToOpen.getRoomKey())){
            roomToOpen.openDoor();
            System.out.println("You've just opened the "+roomToOpen.getName()+" door!");
        }
        else {
            System.out.println("You don't have the key of this room to open it !");
        }

    }

    /**
     * Try to exit in to one direction. If there is an exit, enter the new
     * room, otherwise print the issue.
     * @param command The command to be processed.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if (nextRoom.isOpen()){
            player.setPreviousRoom(player.getCurrentRoom());
            if (nextRoom == unlockedRoomHashMap.get("transporter room")) {
                teleportPlayer();
                return;
            }
            player.setCurrentRoom(nextRoom);
            if (nextRoom != kryzkoGuard.getCurrentRoom()){
                characterManager.moveRandomly(kryzkoGuard);
            }
            {
                tryToFight(player,kryzkoGuard);
                tryToFight(player,kryzko);
            }
            if (!player.isKilled() && player.getCurrentRoom() != kryzkoGuard.getCurrentRoom()) {
                System.out.println(player.getCurrentRoom().getLongDescription());
            }
            openDoorSound.play(false);
        }
        else {
            System.out.println("The door is locked! Try to find a key to open it. If you have the key try to open it first.");
        }

        if (player.getCurrentRoom().getName().equals("laundry room")){
            kryzkoSound.play(false);
        }
    }

    /**
     * Verify if the player and a character are in the same room. If yes, fight.
     * @param player Player concerned.
     * @param character Character concerned.
     */
    public void tryToFight(Player player,Character character)
    {
        if (player.getCurrentRoom() == character.getCurrentRoom() && !character.isKilled()){
            System.out.print("You are in the same room as "+character.getDescription()+"! ");
            if (!weaponInInventory(player) && player.getInventory().getItem("poison") == null) {            //If the player doesn't have any weapon in his inventory, the player is killed.
                player.kill();
            }
            else if (weaponInInventory(player)){
                System.out.println("You can kill him using the 'kill "+character.getName()+"' command.");
            }
            else {
                System.out.println("You can kill him using the 'give "+character.getName()+" poison' command.");
            }
        }
    }

    /**
     * Verify if a player has a weapon in his inventory.
     * @param player The player concerned.
     */
    public boolean weaponInInventory(Player player) {
        return player.getInventory().getItem("gun") != null && player.getInventory().getItem("ammo") != null
                || player.getInventory().getItem("knife") != null;
    }


    /**
     * Shows the items available in a particular room.
     */
    private void explore()
    {
        if (!player.getCurrentRoom().getRoomItems().equals(" ")) {
            System.out.println("You found these items in here: " + player.getCurrentRoom().getRoomItems());
        }else {
            System.out.println("There are no items in this location !");
        }
    }

    /**
     * Takes the player back to the last room he's been in.
     */
    private void back()
    {
        if (player.getPreviousRoom() == null || player.getCurrentRoom() == player.getPreviousRoom()) {
            System.out.println("This is the last location you've been in !");
        }else {
            player.setCurrentRoom(player.getPreviousRoom());
            {
                tryToFight(player, kryzkoGuard);
            }
            if (!player.isKilled()) {
                System.out.println(player.getCurrentRoom().getLongDescription());
            }
            openDoorSound.play(false);
        }
    }

    /**
     * This method allows the player to grab an item and to put it into its inventory.
     * @param command The command to be processed.
     */
    private void grabItem(Command command)
    {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to grab...
            System.out.println("Grab what?");
            return;
        }

        String NameOfItemToGrab = command.getSecondWord();
        Item itemToGrab= player.getCurrentRoom().getItem(NameOfItemToGrab);

        if (itemToGrab == null) {
            System.out.println("There is no such item there !");
        }
        else {
            if (!itemToGrab.getCanBeCarried()){
                System.out.println("It is not possible to grab the " + itemToGrab.getItemDescription()+".");
                return;
            }
            else if (player.getInventory().isPossibleToGrab(itemToGrab)) {
                player.getInventory().addItem(itemToGrab);
                System.out.println("You've just grabbed a "+ itemToGrab.getItemDescription()+" !");

                //Remove the item from the room, it is now in the inventory of the player.
                player.getCurrentRoom().removeItem(itemToGrab);
            }
            else {
                System.out.println("You can't grab this object, otherwise your inventory will be to heavy. Try to free some storage.");
            }
        }
    }

    /**
     * Give an item to a character.
     * @param command The command to be processed.
     */
    private void giveItem(Command command)
    {
        if(!command.hasSecondWord() || !command.hasThirdWord()) {
            // if there is no second word, we don't know which item to open...
            System.out.println("Command incomplete. The command should be under the form : give 'character' 'item'\nPlease try again.");
            return;
        }

        String nameOfCharacterThatReceive = command.getSecondWord();
        Character characterThatReceive = characterManager.getCharacter(nameOfCharacterThatReceive);
        String nameOfItemToGive = command.getThirdWord();
        Item itemToGive = player.getInventory().getItem(nameOfItemToGive);


        if (itemToGive == null && characterThatReceive == null){
            System.out.println("Neither the item nor the character you are trying to give the item to have been found. Please try again.");
        }
        else if (itemToGive == null){
            System.out.println("The item your searching for has not been found. Please try again.");
        }
        else if (characterThatReceive == null){
            System.out.println("The character your searching for has not been found. Please try again.");
        }
        else if (player.getCurrentRoom() != characterThatReceive.getCurrentRoom()) {
            System.out.println("You have to be in the same room as the character you want to give the object to.");
        }
        else {
            characterThatReceive.getInventory().addItem(itemToGive); //Add item to inventory of the character
            player.getInventory().removeItem(itemToGive); //Remove the item from the inventory of the player

            //If item "poison" is given, kill instantly the character
            if (characterThatReceive.getInventory().containsItem(characterThatReceive.getInventory().getItem("poison"))){
                tryToKill(command); //Here only the second word, which holds the name of the character to kill, is considered by the tryToKill method.
            }
        }
    }


    /**
     * Gives the weight of the inventory or any item in the inventory o the player.
     * @param command The command to be processed.
     */
    private void getWeight(Command command){

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to weight...
            System.out.println("Weight what ?");
            return;
        }

        int weight;

        String objectToWeight = command.getSecondWord();
        Item itemToWeight = player.getInventory().getItem(objectToWeight);

        if (objectToWeight.equals("inventory")){
            weight = player.getInventory().getInventoryWeight();
            System.out.println("The weight of your inventory is: "+weight+" g.");
        }
        else if (itemToWeight == null) {
            System.out.println("You don't have such item in your inventory !");
        }
        else {
            weight = itemToWeight.getItemWeight();
            System.out.println("The weight of the "+itemToWeight.getItemDescription()+" is: "+ weight+" g.");
        }

    }

    /**
     * This method allows the player to remove an item from its inventory.
     * @param command The command to be processed.
     */
    private void throwItem(Command command)
    {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to grab...
            System.out.println("Which item do you want to throw ?");
            return;
        }

        String nameOfItemToThrow = command.getSecondWord();
        Item itemToThrow = player.getInventory().getItem(nameOfItemToThrow);

        if (itemToThrow != null){
            player.getInventory().removeItem(itemToThrow);
            System.out.println("You've threw away "+ itemToThrow.getItemDescription()+" !");

            //Add the item thrown to the room
            player.getCurrentRoom().addItem(itemToThrow.getItemName(), itemToThrow);
        }
        else {
            System.out.println("There is no such item in your inventory !");
        }
    }

    /**
     * Kill the character when the player is in a fight.
     * @param command The command to be processed.
     */
    public void tryToKill(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to weight...
            System.out.println("Kill who ?");
            return;
        }

        String nameOfCharacterToKill = command.getSecondWord();
        Character characterToKill = characterManager.getCharacter(nameOfCharacterToKill);

        if (characterToKill == null){
            System.out.println("Character not recognized.");
        }
        else if (player.getCurrentRoom() != characterToKill.getCurrentRoom()) {
            System.out.println("You have to be in the same room as the character you want to kill.");
        } else if (weaponInInventory(player) || characterToKill.getInventory().getItem("poison") != null ) {
            characterToKill.kill();
            System.out.println("You have just killed the " + characterToKill.getDescription() + "!");
            if(nameOfCharacterToKill.equals("guard")) {
                System.out.println("You may now explore the house freely.");
                 System.out.println("Mission Succeeded. Great job agent 007.");
            }
            else if (nameOfCharacterToKill.equals("kryzko")){
                System.out.println("Mission Succeeded. Great job agent 007.");
            }
        }
        else {
            System.out.println("Command not allowed.");
        }
    }


    /**
     * Shows the inventory in the form of a set of items.
     */
    private void showInventory()
    {
       if (player.getInventory().getSize() > 0) {
           System.out.println(player.getInventory().showInventory());
       }else {
           System.out.println("Your inventory is empty !");
       }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println();
        if (player.getPreviousRoom() == null) {
            System.out.println("You are " + player.getCurrentRoom().getShortDescription()+".");
        }else {
            System.out.println(player.getCurrentRoom().getLongDescription() + ". You entered from " +  player.getPreviousRoom().getName()+".");
        }
        System.out.println();
        System.out.println("Your commands are: ");
        parser.showCommands();
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Return a list containing the rooms of the game.
     * @return List of the rooms in the game.
     */
    public List<Room> getRoomsList()
    {
        List roomsList = new ArrayList(unlockedRoomHashMap.values());
        return roomsList;
    }
    
    /**
     * Generate a random unlocked room in the game.
     * @return A random room.
     */
    public Room generateRandomRoom()
    {
        List<Room> roomsList = this.getRoomsList();
        int randomNum = new Random().nextInt(roomsList.size());
        return roomsList.get(randomNum);
    }

    /**
     * Teleports the player to a random room.
     */
    private void teleportPlayer()
    {
        System.out.println("You have entered the transporter room.");
        Room nextRoom = generateRandomRoom();
        player.setPreviousRoom(player.getCurrentRoom());
        player.setCurrentRoom(nextRoom);
        System.out.println("You've thus been teleported "+nextRoom.getShortDescription()+".\n"+nextRoom.getExitString());
    }

}
