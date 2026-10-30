import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items; // Gør det muligt at have flere ting i rummet

    /**
     * Create a room described "description". Initially, it has
     * no exits and no items. "description" is something like "a kitchen"
     * or "an open court yard".
     */
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

    /**
     * Define an exit for this room.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items in room: a knife (2)
     */
    public String getLongDescription()
    {
        String longDesc = "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
        return longDesc;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        for(String direction : exits.keySet()) {
            returnString += " " + direction;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Return a string describing the items in this room.
     */
    private String getItemString()
    {
        String returnString = "Items in room:";
        if(items.isEmpty()) {
            return "No items here.";
        }
        for(String itemName : items.keySet()) {
            Item item = items.get(itemName);
            returnString += " " + item.getDescription() + " (" + item.getWeight() + ")";
        }
        return returnString;
    }
    /**
     * Fjern et item fra rummet.
     */
    public Item removeItem(String name)
    {
        return items.remove(name);
    }
}