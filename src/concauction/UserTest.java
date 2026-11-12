package concauction;
import student.TestCase;
// -------------------------------------------------------------------------
/**
 *  tests the user class
 *  @author krish
 *  @version Nov 11, 2026
 */
public class UserTest extends TestCase
{
    /**
     * tests the user constructor
     */
    public void testConstructor()
    {
        User user = new User();

        assertTrue(user.getID() > 0);
        assertEquals(1000, user.getMoney());
        assertTrue(user.getInventory().isEmpty());
    }


    /**
     * tests creating multiple users
     */
    public void testConstructorMultiple()
    {
        User user1 = new User();
        User user2 = new User();

        assertEquals(user1.getID() + 1, user2.getID());
    }


    /**
     * tests getting the starting money
     */
    public void testGetMoney()
    {
        User user = new User();

        assertEquals(1000, user.getMoney());
    }


    /**
     * tests getting money after changing it
     */
    public void testGetMoneyChange()
    {
        User user = new User();

        user.setMoney(100);

        assertEquals(100, user.getMoney());
    }


    /**
     * tests getting an empty inventory
     */
    public void testGetInventory()
    {
        User user = new User();

        assertNotNull(user.getInventory());
        assertTrue(user.getInventory().isEmpty());
    }


    /**
     * tests getting an inventory containing an item
     */
    public void testGetInventoryItem()
    {
        User user = new User();
        Item item = new Item("Laptop", 200, 300);

        user.inventoryAdd(item);

        assertEquals(1, user.getInventory().size());
        assertSame(item, user.getInventory().get(0));
    }


    /**
     * tests setting a user's money
     */
    public void testSetMoney()
    {
        User user = new User();

        user.setMoney(500);

        assertEquals(500, user.getMoney());
    }


    /**
     * tests setting money to a negative value
     */
    public void testSetMoneyNegative()
    {
        User user = new User();

        user.setMoney(-100);

        assertEquals(0, user.getMoney());
    }


    /**
     * tests setting money to zero
     */
    public void testSetMoneyZero()
    {
        User user = new User();

        user.setMoney(0);

        assertEquals(0, user.getMoney());
    }


    /**
     * tests adding an item to inventory
     */
    public void testInventoryAdd()
    {
        User user = new User();
        Item item = new Item("Laptop", 200, 300);

        user.inventoryAdd(item);

        assertTrue(user.getInventory().contains(item));
    }


    /**
     * tests adding multiple items to inventory
     */
    public void testInventoryAddMultiple()
    {
        User user = new User();
        Item laptop = new Item("Laptop", 200, 300);
        Item necklace = new Item("Necklace", 50, 100);

        user.inventoryAdd(laptop);
        user.inventoryAdd(necklace);

        assertEquals(2, user.getInventory().size());
        assertSame(laptop, user.getInventory().get(0));
        assertSame(necklace, user.getInventory().get(1));
    }


    /**
     * tests adding a null item to inventory
     */
    public void testInventoryAddNull()
    {
        User user = new User();

        user.inventoryAdd(null);

        assertEquals(1, user.getInventory().size());
        assertNull(user.getInventory().get(0));
    }


    /**
     * tests getting the user's id
     */
    public void testGetID()
    {
        User user1 = new User();
        User user2 = new User();

        assertTrue(user1.getID() > 0);
        assertEquals(user1.getID() + 1, user2.getID());
    }
}
