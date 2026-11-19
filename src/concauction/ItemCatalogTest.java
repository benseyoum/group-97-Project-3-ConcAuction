package concauction;
import student.TestCase;
/**
 * tests itemcatalog
 */
public class ItemCatalogTest extends TestCase
{
	/**
     * tests the number of items
     */
    public void testItemCount()
    {
        assertEquals(30, ItemCatalog.ItemPool.length);
    }


    /**
     * tests that no items are null
     */
    public void testItemsNotNull()
    {
        for (Item item : ItemCatalog.ItemPool)
        {
            assertNotNull(item);
        }
    }


    /**
     * tests the item values
     */
    public void testItemValues()
    {
        Item first = ItemCatalog.ItemPool[0];

        assertEquals("Vintage Polaroid Camera",
            first.getItemName());
        assertEquals(50, first.getStartingBid());
        assertEquals(80, first.getBoughtInValue());

        Item last = ItemCatalog.ItemPool[29];

        assertEquals("Rare Gold Coin Collection",
            last.getItemName());
        assertEquals(650, last.getStartingBid());
        assertEquals(950, last.getBoughtInValue());
    }


    /**
     * tests that item prices are valid
     */
    public void testItemPrices()
    {
        for (Item item : ItemCatalog.ItemPool)
        {
            assertTrue(item.getStartingBid() > 0);
            assertTrue(item.getBoughtInValue() > 0);
            assertTrue(item.getBoughtInValue()
                > item.getStartingBid());
        }
    }
}
