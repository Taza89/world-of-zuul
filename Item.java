/**
 * Class Item - represents an item in the adventure game.
 *
 * @author (Dit navn)
 * @version (Et eller andet versionsnummer)
 */
public class Item
{
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     * @param description The item's description.
     * @param weight The item's weight.
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return The description of the item.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
}
