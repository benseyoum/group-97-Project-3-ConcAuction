package concauction;

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * A user participating in the auction. A user has an ID, an inventory and money
 * 
 * @author krish
 * @version Nov 11, 2026
 */
public class User
{
    // ~ Fields ................................................................
    private static int nextID = 1;
    private final int id;
    private int money;
    private ArrayList<Item> inventory;
    // ~ Constructors ..........................................................
    public User()
    {
        id = nextID;
        nextID++;

        money = 1000;
        inventory = new ArrayList<Item>();
    }

    // ~Public Methods ........................................................
    
    
    /**
     * returns the user's ID
     *
     * @return the user's ID
     */
    public int getID()
    {
        return id;
    }
    
    
    /**
     * returns the user's current money
     *
     * @return the user's money
     */
    public int getMoney()
    {
        return money;
    }
    
    
    /**
     * returns the user's inventory
     *
     * @return the user's inventory
     */
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
    
    
    /**
     * adds an item to the user's inventory
     *
     * @param newItem
     *            the item to add
     */
    public void inventoryAdd(Item newItem)
    {
        inventory.add(newItem);
    }
    
    
    

    /**
     * sets the user's money
     * negative values are changed to zero
     *
     * @param money the new money amount
     */
    public void setMoney(int money)
    {
        if (money < 0)
        {
            this.money = 0;
        }
        else
        {
            this.money = money;
        }
    }
}
