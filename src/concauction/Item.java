package concauction;

// -------------------------------------------------------------------------
/**
 * Represents an item that can be auctioned Each item has a name, starting bid
 * and bought-in value
 * 
 * @author krish
 * @version Nov 11, 2026
 */
public class Item
{
    // ~ Fields ................................................................
    private final String name;
    private final int startingBid;
    private final int boughtInValue;

    // ----------------------------------------------------------
    /**
     * Create a new Item object.
     * 
     * @param name
     * @param startingBid
     * @param boughtInValue
     */
    public Item(String name, int startingBid, int boughtInValue)
    {
        if (name == null)
        {
            this.name = "";
        }
        else
        {
            this.name = name;
        }

        this.startingBid = startingBid;
        this.boughtInValue = boughtInValue;
    }


    // ----------------------------------------------------------
    /**
     * The name of the item
     * 
     * @return the items name
     */
    public String getItemName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Returns the starting bid of the item
     * 
     * @return the starting bid of the item
     */
    public int getStartingBid()
    {
        return startingBid;
    }


    // ----------------------------------------------------------
    /**
     * Represents the starting bid of the item
     * 
     * @return the items bought-in value
     */
    public int getBoughtInValue()
    {
        return boughtInValue;
    }
}
