import java.util.HashMap;
import java.util.Set;

/**
 * Class Inventory - the inventory of a player.
 *
 * This class is part of the James Bond application.
 *
 * Each player has an inventory. The inventory consists of an player
 * object (like a bag) that stores the items that the player grabs.
 *
 * The inventory can hold items up until a certain amount of weight.
 *
 * @author Aymen Berbache
 * @version 2021.11.28
 */

public class Inventory {

    private final HashMap<String, Item> inventory;
    private int maxWeight;

    /**
     * Create the inventory under the form of a HashMap of items.
     */
    public Inventory()
    {
        inventory = new HashMap<>();
        maxWeight  = 0;
    }

    /**
     * Add item to inventory.
     * @param item The item to add to the inventory.
     */
    public void addItem(Item item)
    {
        inventory.put(item.getItemName(),item);
    }

    /**
     * Remove item from inventory.
     * @param item The item to remove
     */
    public void removeItem(Item item)
    {
        inventory.remove(item.getItemName(),item);
    }

    /**
     * Set the maximum weight of that the inventory can hold.
     * @param weight The maximum weight.
     */
    public void setMaxWeight(int weight)
    {
        maxWeight = weight;
    }

    /**
     * @return The maximum weight that the inventory can hold.
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }

    /**
     * @param item The item to check.
     * @return true if the inventory contains the item, false otherwise.
     */
    public boolean containsItem(Item item) {
        return inventory.containsValue(item);
    }

    /**
     * @return A Set containing all the item of the inventory.
     */
    public Set<String> showInventory()
    {
        return inventory.keySet();
    }

    /**
     * @return The current weight of the inventory.
     */
    public int getInventoryWeight()
    {
        int inventoryWeight = 0;
        for (Item item : inventory.values()) {
            inventoryWeight += item.getItemWeight();
        }
        return inventoryWeight;
    }

    /**
     * @param item The item to check.
     * @return true if it is possible to grab the item, false if it is too heavy.
     */
    public boolean isPossibleToGrab(Item item)
    {
        return item.getItemWeight() + getInventoryWeight() <= maxWeight;
    }

    /**
     * @return The size of the inventory.
     */
    public int getSize()
    {
        return inventory.size();
    }

    /**
     * @param itemName The name of the item that we want to get.
     * @return The item object that we want.
     */
    public Item getItem(String itemName)
    {
        return inventory.get(itemName);
    }

}
