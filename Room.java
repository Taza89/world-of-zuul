import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * Tilføj et item til dette rum.
     */
    public void addItem(String name, Item item)
    {
        items.put(name, item);
    }

    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public String getExitString()
    {
        String returnString = "Exits:";
        for(String direction : exits.keySet()) {
            returnString += " " + direction;
        }
        return returnString;
    }

    /**
     * Return a description of the room, including its exits and items.
     */
    public String getLongDescription()
    {
        String longDesc = "You are " + description + ".\n" + getExitString();

        // <--- NYT: Tilføj info om tingen, hvis der er en i rummet
        if (item != null) {
            longDesc += "\nItem here: " + item.getDescription() + " (weight: " + item.getWeight() + ")";
        }

        return longDesc;
    }
}