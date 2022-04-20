/**
 * Class Player
 *
 * This class is part of the James Bond application.
 *
 * The player is the main character of the game.
 * He has several fields, his current room, previous room and his inventory.
 *
 * He also can be killed, if so he loses the game.
 *
 * @author Aymen Berbache
 * @version 2021.11.28
 */

public class Player
{
    private Room currentRoom;
    private Room previousRoom;
    private final Inventory inventory;
    private boolean isKilled;

    /**
     * Creates the player object. At first, the player has no previous room and an empty inventory.
     */
    public  Player()
    {
        inventory = new Inventory();
        previousRoom = null;
        isKilled = false;
    }

    /**
     * @return The inventory of the player.
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    /**
     * @return The current room of the player.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * @return The previous room of the player.
     */
    public Room getPreviousRoom()
    {
        return previousRoom;
    }

    /**
     * Change the player current room.
     * @param destination The next room.
     */
    public void setCurrentRoom(Room destination)
    {
        currentRoom = destination;
    }

    /**
     *  Change the previous room of the player.
     * @param destination The next room.
     */
    public void setPreviousRoom(Room destination)
    {
        previousRoom = destination;
    }

    /**
     * @return true if the player has been killed, false if not.
     */
    public boolean isKilled(){return isKilled;}

    /**
     * Kill the player.
     */
    public void kill(){
        if(this.getInventory().getItem("gun") == null && this.getInventory().getItem("knife") == null){
            System.out.println("\nYou had no weapons on you!");
        }
        else {
            System.out.println("\nYou had no ammunition on you!");
        }
        System.out.println("You have thus been killed. Mission failed. RIP agent 007.");
        isKilled = true;
    }
}
