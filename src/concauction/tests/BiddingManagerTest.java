
package concauction.tests;

import concauction.*;
import java.util.ArrayList;
import student.TestCase;

/**
 * tests the biddingManager class
 *
 * @author krish
 * @version Nov 13, 2026
 */
public class BiddingManagerTest
    extends TestCase
{
    private BiddingManager manager;

    private static class MockObserver
        implements Observer
    {
        private int lastBid;
        private String lastItemName = "";
        private User lastWinner;
        private int updateCount;

        public void update(int highestBid, Item item, User winningUser)
        {
            lastBid = highestBid;
            if (item != null)
            {
                lastItemName = item.getItemName();
            }
            else {
                lastItemName = "";
            }
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

        assertEquals(queue.peek().getStartingBid(), manager.getHighestBid());

        int count = 0;

        while (!queue.isEmpty())
        {
            Item item = queue.dequeue();

            assertNotNull(item);

            count++;
        }

        assertEquals(5, count);
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

        String itemName = manager.getAuctionQueue().peek().getItemName();

        int bid = manager.getHighestBid() + 1;
        manager.addBid(bid, user);

        assertEquals(1, observer1.updateCount);
        assertEquals(1, observer2.updateCount);

        assertEquals(bid, observer1.lastBid);
        assertEquals(itemName, observer1.lastItemName);
        assertSame(user, observer1.lastWinner);

        assertEquals(bid, observer2.lastBid);
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

        int bid = item.getBoughtInValue();

        manager.addBid(bid, user1);

        assertEquals(bid, manager.getHighestBid());

        manager.addBid(bid, user2);

        manager.finishAuction();

        assertTrue(user1.getInventory().contains(item));
        assertFalse(user2.getInventory().contains(item));

        assertEquals(1000 - bid, user1.getMoney());
        assertEquals(1000, user2.getMoney());
    }


    /**
     * tests rejecting invalid bids
     */
    public void testInvalidBids()
    {
        User user = new User();
        boolean caught = false;

        try
        {
            manager.addBid(-100, user);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);
        assertEquals(1000, user.getMoney());

        caught = false;

        try
        {
            manager.addBid(0, user);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);
        assertEquals(1000, user.getMoney());

        caught = false;

        try
        {
            manager.addBid(1001, user);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);
        assertEquals(1000, user.getMoney());

        caught = false;

        try
        {
            manager.addBid(400, null);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);

        int validBid = manager.getHighestBid() + 1;
        manager.addBid(validBid, user);

        caught = false;

        try
        {
            manager.addBid(validBid - 1, user);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);
        assertEquals(validBid, manager.getHighestBid());

        Queue<Item> queue = manager.getAuctionQueue();

        while (!queue.isEmpty())
        {
            queue.dequeue();
        }

        caught = false;

        try
        {
            manager.addBid(500, user);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
        }

        assertTrue(caught);
        assertTrue(queue.isEmpty());
    }


    /**
     * tests finishing a successful auction
     */
    public void testFinishAuctionSold()
    {
        User user = new User();
        Item item = manager.getAuctionQueue().peek();

        int bid = item.getBoughtInValue();

        manager.addBid(bid, user);
        manager.finishAuction();

        assertEquals(1000 - bid, user.getMoney());
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

        manager.resetFinish(true);

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

        manager.addBid(manager.getHighestBid() + 1, user);
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
     * tests resetting between auction and after the last item
     */
    public void testResetFinish()
    {
        Queue<Item> queue = manager.getAuctionQueue();
        Item firstItem = queue.peek();

        manager.finishAuction();
        manager.resetFinish(true);

        assertFalse(firstItem == queue.peek());

        assertEquals(queue.peek().getStartingBid(), manager.getHighestBid());

        while (!queue.isEmpty())
        {
            manager.finishAuction();
            manager.resetFinish(true);
        }

        assertTrue(queue.isEmpty());
        assertEquals(0, manager.getHighestBid());
    }


    /**
     * tests that random items come from the item catalog
     */
    public void testRandomItems()
    {
        BiddingManager randomManager = new BiddingManager();
        Queue<Item> queue = randomManager.getAuctionQueue();

        assertEquals(5, queue.size());

        assertEquals(
            queue.peek().getStartingBid(),
            randomManager.getHighestBid());

        while (!queue.isEmpty())
        {
            Item item = queue.dequeue();
            boolean found = false;

            for (Item catalogItem : ItemCatalog.ItemPool)
            {
                if (catalogItem.getItemName().equals(item.getItemName()))
                {
                    found = true;
                }
            }

            assertTrue(found);
        }
    }


    /**
     * tests that random items have no duplicates
     */
    public void testNoDuplicateItems()
    {
        BiddingManager randomManager = new BiddingManager();
        Queue<Item> queue = randomManager.getAuctionQueue();

        ArrayList<String> names = new ArrayList<String>();

        while (!queue.isEmpty())
        {
            String name = queue.dequeue().getItemName();

            assertFalse(names.contains(name));

            names.add(name);
        }

        assertEquals(5, names.size());
    }


    // ----------------------------------------------------------
    /**
     * tests if postponing sets order correctly
     */
    public void testPostponeBid()
    {
        BiddingManager randomManager = new BiddingManager();
        Queue<Item> queue = randomManager.getAuctionQueue();
        Queue<Item> queue2 = new Queue<>();
        Item[] itemArr = new Item[5];
        Item[] itemArrPostPoned = new Item[5];

        int i = 0;
        while (!queue.isEmpty())
        {
            Item item = queue.dequeue();

            queue2.enqueue(item);
            itemArr[i] = item;
            i++;
        }

        while (!queue2.isEmpty())
        {
            Item item = queue2.dequeue();
            queue.enqueue(item);
        }
        randomManager.postponeBid();

        i = 0;
        while (!queue.isEmpty())
        {
            Item item = queue.dequeue();
            itemArrPostPoned[i] = item;
            i++;
        }

        assertEquals(
            itemArr[1].getItemName(),
            itemArrPostPoned[0].getItemName());
        assertEquals(
            itemArr[0].getItemName(),
            itemArrPostPoned[4].getItemName());
    }


    /**
     * tests observer updates when the queue is empty
     */
    public void testObserverEmptyQueue()
    {
        MockObserver observer = new MockObserver();
        manager.addObserver(observer);

        Queue<Item> queue = manager.getAuctionQueue();

        while (!queue.isEmpty())
        {
            manager.finishAuction();
            manager.resetFinish(true);
        }

        assertEquals(10, observer.updateCount);
        assertEquals("", observer.lastItemName);
        assertNull(observer.lastWinner);
        assertEquals(0, observer.lastBid);
    }


    // ----------------------------------------------------------
    /**
     * tests observer updates
     */
    public void testObserverUpdates()
    {
        MockObserver observer = new MockObserver();
        manager.addObserver(observer);

        manager.addUser();
        assertEquals(1, observer.updateCount);

        manager.finishAuction();
        assertEquals(2, observer.updateCount);

        manager.resetFinish(true);
        assertEquals(3, observer.updateCount);

        assertEquals(
            manager.getAuctionQueue().peek().getItemName(),
            observer.lastItemName);

        assertEquals(manager.getHighestBid(), observer.lastBid);
    }

}
