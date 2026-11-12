package concauction;

import student.TestCase;


// -------------------------------------------------------------------------
/**
 *  tests the item class
 * 
 *  @author krish
 *  @version Nov 11, 2026
 */
public class ItemTest extends student.TestCase
{

    /**
     * Tests the Item constructor.
     */
    public void testItemConstructor()
    {
        Item item = new Item("Laptop", 200, 300);

        assertEquals("Laptop", item.getItemName());
        assertEquals(200, item.getStartingBid());
        assertEquals(300, item.getBoughtInValue());
    }


    /**
     * tests getting an item's name
     */
    public void testGetItemName()
    {
        Item item = new Item("Laptop", 200, 300);

        assertEquals("Laptop", item.getItemName());
    }


    /**
     * tests getting the starting bid
     */
    public void testGetStartingBid()
    {
        Item item = new Item("Laptop", 200, 300);

        assertEquals(200, item.getStartingBid());
    }


    /**
     * tests a starting bid of zero
     */
    public void testGetStartingBidZero()
    {
        Item item = new Item("Laptop", 0, 300);

        assertEquals(0, item.getStartingBid());
    }


    /**
     * tests getting the bought-in value
     */
    public void testGetBoughtInValue()
    {
        Item item = new Item("Laptop", 200, 300);

        assertEquals(300, item.getBoughtInValue());
    }


    /**
     * tests a bought-in value of zero
     */
    public void testGetBoughtInValueZero()
    {
        Item item = new Item("Laptop", 200, 0);

        assertEquals(0, item.getBoughtInValue());
    }


    /**
     * tests getting an item with a null name
     */
    public void testGetItemNameNull()
    {
        Item item = new Item(null, 200, 300);

        assertEquals("", item.getItemName());
    }


    /**
     * tests creating an item with a null name
     */
    public void testItemConstructorNullName()
    {
        Item item = new Item(null, 200, 30);

        assertEquals("", item.getItemName());
        assertEquals(200, item.getStartingBid());
        assertEquals(30, item.getBoughtInValue());
    }
   

}
