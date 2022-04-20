/**
 * Class Item
 *
 * This class is part of the James Bond application.
 *
 * An item is any tangible object that can be found in the rooms of the game.
 * Each item has a name, a weight and a description.
 *
 * An item can be picked up, thrown away and carried around the rooms by the player.
 *
 * @author Aymen Berbache
 * @version 2021.11.28
 */

public class Item {

    private final String itemName;
    private final String description;
    private final int weight;
    private final boolean canBeCarried;

    /**
     * Creates the item.
     * @param itemName The name of the item.
     * @param description The description of the item.
     * @param weight The weight of the item.
     */
    public Item(String itemName, String description, int weight, boolean canBeCarried)
    {
        this.itemName = itemName;
        this.description = description;
        this.weight = weight;
        this.canBeCarried = canBeCarried;
    }

    /**
     * @return The description of the item. Something like that :"butcher knife".
     */
    public String getItemDescription()
    {
        return description;
    }

    /**
     * @return The name of the item.
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @return The weight of the item.
     */
    public int getItemWeight()
    {
        return weight;
    }

    /**
     * @return true if the item can be carried, false otherwise.
     */
    public boolean getCanBeCarried()
    {
        return canBeCarried;
    }
}
