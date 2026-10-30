import java.util.HashMap;

public class Player
{
    private String name;
    private Room currentRoom;
    private HashMap<String, Item> inventory;
    private int maxWeight;
    private int currentWeight;

    public Player(String name, Room startingRoom, int maxWeight)
    {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new HashMap<>();
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    /**
     * Tilføj et item til spillerens inventar, hvis vægten tillader det.
     */
    public boolean takeItem(String name, Item item)
    {
        if (currentWeight + item.getWeight() > maxWeight) {
            return false; // Vægten overskrides
        }
        inventory.put(name, item);
        currentWeight += item.getWeight();
        return true;
    }

    /**
     * Fjern et item fra spillerens inventar (når man taber det).
     */
    public Item dropItem(String name)
    {
        Item item = inventory.remove(name);
        if (item != null) {
            currentWeight -= item.getWeight();
        }
        return item;
    }

    /**
     * Hent et item fra inventaret uden at fjerne det.
     */
    public Item getItem(String name)
    {
        return inventory.get(name);
    }

    /**
     * Returner en tekststreng med alle ting i inventaret og total vægt (Exercise 6.32).
     */
    public String getInventoryString()
    {
        if (inventory.isEmpty()) {
            return "You are carrying no items.";
        }
        String returnString = "Items carried:";
        for (String itemName : inventory.keySet()) {
            Item item = inventory.get(itemName);
            returnString += "\n - " + item.getDescription() + " (weight: " + item.getWeight() + ")";
        }
        returnString += "\nTotal weight: " + currentWeight + " / " + maxWeight + " kg";
        return returnString;
    }
}
