/**
 * Class Character - a character in an adventure game.
 *
 * This class is part of the James Bond application.
 *
 * A "Character" represents persons or animals. Characters are in rooms.
 * Characters can move around by themselves.
 *
 * Each character has a name, a description, its own inventory and a current room location.
 *
 * @author Aymen Berbache
 * @version 2021.11.28
 */

public class Character
{
    private final String name;
    private final String description;
    private final Inventory inventory;
    private boolean isKilled;
    private Room currentRoom;

    /**
     * Contstructor that creates a character
     * @param name The name of the character.
     * @param description The description of the character.
     */
    public Character(String name, String description)
    {
        this.name = name;
        this.description = description;
        isKilled = false;
        inventory = new Inventory();
    }

    /**
     * Accessor method that returns the current room of the character.
     * @return The character current location.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Accessor method that returns the name of a character.
     * @return The name of the character
     */
    public String getName()
    {
        return name;
    }

    /**
     * Accessor method that returns the description of the character.
     * @return Something like "The guard of kryzko".
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Mutator method that set the initial room of the character when assigned a room.
     * @param destination The room where the character is at the beginning of the game.
     */
    public void setCurrentRoom(Room destination)
    {
        currentRoom = destination;
    }


    /**
     * Kill a character.
     */
    public void kill()
    {
        isKilled = true;
    }

    /**
     * Accessor method that show wether or not a character has been killed.
     * @return true if the character is dead, false if not.
     */
    public boolean isKilled(){return isKilled;}

    /**
     * @return The inventory of the character.
     */
    public Inventory getInventory()
    {
        return inventory;
    }

}
