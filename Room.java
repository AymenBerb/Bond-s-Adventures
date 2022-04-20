import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the James Bond application.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits. For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * Each room is initialized with a set of items in it.
 * Locked rooms are created with their own key as an item, that could be found in the map.
 * 
 * @author Aymen Berbache, Michael Kölling and David J. Barnes.
 * @version 2021.11.28
 */

public class Room 
{
    private Item key;
    private boolean isLocked;
    private final String name;
    private final String description;
    private final HashMap<String, Room> exits;        // Store exits of this room.
    private final HashMap<String, Item> itemsInRoom;


    /**
     * Create a room that may or may not be locked. Initially, it has no exits, items and characters.
     * "description" is something like "in the kitchen" or "in the storage room".
     * "name" is something like "kitchen"
     * @param name The room's name.
     * @param description The room's description.
     * @param isLocked The room's locked state.
     * @param key The key of the room.
     */
    public Room(String name, String description, boolean isLocked, Item key)
    {
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.key = key;
        exits = new HashMap<>();
        itemsInRoom = new HashMap<>();

    }

    /**
     * Accessor method tha return the name of a room.
     * @return The name of the room.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return  "You are " + description + ".\n" + getExitString();
    }

    /**
     * Accessor method that returns the locked state of the room.
     * @return true of it is open, false if not.
     */
    public boolean isOpen()
    {
        return !isLocked;
    }

    /**
     * Mutator method that opens the room.
     */
    public void openDoor()
    {
        isLocked = false;
    }

    /**
     * Assigns a key to the room
     * @param keyName The name of the key assigned.
     */
    public void setKey(Item keyName)
    {
        key = keyName;
    }

    /**
     * Accessor method that returns the key of the room.
     * @return The key of the room.
     */
    public Item getRoomKey()
    {
        return key;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        StringBuilder returnString = new StringBuilder("Your exits are:");
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    /**
     * Return a list containing the room's exits
     * @return List of the room's exits.
     */
    public List<String> getExitList()
    {
        List exitsKeyList = new ArrayList(exits.keySet());
        return exitsKeyList;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Add an item to the map of items of a particular room.
     * @param itemName The name of the item.
     * @param item The item object.
     */
    public void addItem(String itemName, Item item)
    {
        itemsInRoom.put(itemName,item);
    }

    /**
     * Accessor method to get the items available in a room;
     * @return The string that lists all the items available in a room.
     */
    public String getRoomItems()
    {
        StringBuilder itemsInRoomString = new StringBuilder(" ");
        for (Item items : itemsInRoom.values()) {
            itemsInRoomString.append(items.getItemName()).append("; ");
        }
        return itemsInRoomString.toString();
    }

    /**
     * Accessor method that returns the item object from the room.
     * @param itemName  The name of the item.
     * @return The item himself.
     */
    public Item getItem(String itemName)
    {
        return itemsInRoom.get(itemName);
    }

    /**
     * Remove item from room.
     * @param itemToRemove The item that will be removed.
     */
    public void removeItem(Item itemToRemove)
    {
        itemsInRoom.remove(itemToRemove.getItemName(),itemToRemove);
    }

    /**
     * Generate a random exits and return its room.
     * @return The room issued.
     */
    public Room generateRandomExit()
    {
        List<String> exitOfTheRoom = this.getExitList();
        int randomNum = new Random().nextInt(exitOfTheRoom.size());

        return this.getExit(exitOfTheRoom.get(randomNum));
    }

}

