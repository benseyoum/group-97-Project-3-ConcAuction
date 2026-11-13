
package concauction;

import student.TestCase;

/**
 * tests the biddingManager class
 *
 * @author krish
 * @version Nov 13, 2026
 */
public class BiddingManagerTest extends TestCase
{
    private BiddingManager manager;

    private static class MockObserver implements Observer
    {
        private int lastBid;
        private String lastItemName;
        private User lastWinner;
        private int updateCount;

        public void update(
            int highestBid,
            String itemName,
            User winningUser)
        {
            lastBid = highestBid;
            lastItemName = itemName;
            lastWinner = winningUser;
            updateCount++;
        }
    }


    /**
     * sets up the bidding manager
     */
    public void setUp()
    {
        manager = new BiddingManager();
    }


    /**
     * tests the constructor
     */
    public void testConstructor()
    {
        Queue<Item> queue = manager.getAuctionQueue();

        assertFalse(queue.isEmpty());
        assertNotNull(queue.peek());

        assertEquals(
            queue.peek().getStartingBid(),
            manager.getHighestBid());

        int count = 0;

        while (!queue.isEmpty())
        {
            Item item = queue.dequeue();

            assertNotNull(item);
            assertEquals("Item " + (count + 1),
                item.getItemName());

            count++;
        }

        assertTrue(count >= 2);
        assertTrue(count <= 6);
    }


    /**
     * tests adding users and maximum capacity
     */
    public void testAddUserAndFullCapacity()
    {
        User previous = new User();
        int firstID = previous.getID() + 1;

        for (int i = 0; i < 4; i++)
        {
            manager.addUser();
        }

        for (int i = 0; i < 4; i++)
        {
            assertNotNull(manager.findUser(firstID + i));
        }

        manager.addUser();

        assertNull(manager.findUser(firstID + 4));
    }


    /**
     * tests finding valid and invalid users
     */
    public void testFindUser()
    {
        User previous = new User();
        int expectedID = previous.getID() + 1;

        manager.addUser();

        User user = manager.findUser(expectedID);

        assertNotNull(user);
        assertEquals(expectedID, user.getID());
        assertNull(manager.findUser(-1));
    }


    /**
     * tests adding observers and receiving updates
     */
    public void testAddObserver()
    {
        MockObserver observer1 = new MockObserver();
        MockObserver observer2 = new MockObserver();

        manager.addObserver(observer1);
        manager.addObserver(observer2);
        manager.addObserver(null);

        User user = new User();
        String itemName =
            manager.getAuctionQueue().peek().getItemName();

        manager.addBid(400, user);

        assertEquals(1, observer1.updateCount);
        assertEquals(1, observer2.updateCount);

        assertEquals(400, observer1.lastBid);
        assertEquals(itemName, observer1.lastItemName);
        assertSame(user, observer1.lastWinner);

        assertEquals(400, observer2.lastBid);
        assertEquals(itemName, observer2.lastItemName);
        assertSame(user, observer2.lastWinner);
    }


    /**
     * tests valid bids and equal bid priority
     */
    public void testAddBidValidAndTie()
    {
        User user1 = new User();
        User user2 = new User();
        Item item = manager.getAuctionQueue().peek();

        manager.addBid(400, user1);

        assertEquals(400, manager.getHighestBid());

        manager.addBid(400, user2);
        
        manager.finishAuction();
        
        assertTrue(user1.getInventory().contains(item));
        assertFalse(user2.getInventory().contains(item));

        assertEquals(600, user1.getMoney());
        assertEquals(1000, user2.getMoney());
    }


    /**
     * tests rejecting invalid bids
     */
    public void testAddBidExceptions()
    {
        User user = new User();

        try
        {
            manager.addBid(-100, user);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1000, user.getMoney());
        }

        try
        {
            manager.addBid(0, user);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1000, user.getMoney());
        }

        try
        {
            manager.addBid(1001, user);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1000, user.getMoney());
        }

        try
        {
            manager.addBid(400, null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(1000, user.getMoney());
        }

        manager.addBid(400, user);

        try
        {
            manager.addBid(300, user);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(400, manager.getHighestBid());
        }

        Queue<Item> queue = manager.getAuctionQueue();

        while (!queue.isEmpty())
        {
            queue.dequeue();
        }

        try
        {
            manager.addBid(500, user);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(queue.isEmpty());
        }
    }


    /**
     * tests finishing a successful auction
     */
    public void testFinishAuctionSold()
    {
        User user = new User();
        Item item = manager.getAuctionQueue().peek();

        manager.addBid(400, user);
        manager.finishAuction();

        assertEquals(600, user.getMoney());
        assertTrue(user.getInventory().contains(item));
        assertSame(item, manager.getAuctionQueue().peek());
    }


    /**
     * tests finishing an auction without bids
     */
    public void testFinishAuctionNoBids()
    {
        Item item = manager.getAuctionQueue().peek();

        manager.finishAuction();

        assertSame(item, manager.getAuctionQueue().peek());

        manager.resetFinish();

        assertFalse(item == manager.getAuctionQueue().peek());
    }


    /**
     * tests finishing an auction below the minimum price
     */
    public void testFinishAuctionBelow()
    {
        Queue<Item> queue = manager.getAuctionQueue();

        while (!queue.isEmpty())
        {
            queue.dequeue();
        }

        Item item = new Item("Laptop", 200, 900);
        queue.enqueue(item);

        User user = new User();

        manager.addBid(400, user);
        manager.finishAuction();

        assertEquals(1000, user.getMoney());
        assertTrue(user.getInventory().isEmpty());
        assertSame(item, queue.peek());
    }


    /**
     * tests finishing an empty auction
     */
    public void testFinishAuctionEmptyQueue()
    {
        Queue<Item> queue = manager.getAuctionQueue();

        while (!queue.isEmpty())
        {
            queue.dequeue();
        }

        manager.finishAuction();

        assertTrue(queue.isEmpty());
    }


    /**
     * tests resetting between auctions and after the last item
     */
    public void testResetFinish()
    {
        Queue<Item> queue = manager.getAuctionQueue();
        Item firstItem = queue.peek();

        manager.finishAuction();
        manager.resetFinish();

        assertFalse(firstItem == queue.peek());

        assertEquals(
            queue.peek().getStartingBid(),
            manager.getHighestBid());

        while (!queue.isEmpty())
        {
            manager.finishAuction();
            manager.resetFinish();
        }

        assertTrue(queue.isEmpty());
        assertEquals(0, manager.getHighestBid());
    }
}


