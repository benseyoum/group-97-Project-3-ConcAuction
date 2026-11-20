package concauction;

import java.util.*;

// -------------------------------------------------------------------------
/**
 * Manages auctions, users and bids.
 * 
 * @author krish
 * @version Nov 11, 2026
 */
public class BiddingManager
{
    // ~ Fields ................................................................
    private Queue<Item> auctionQueue;
    private Queue<User> userQueue;
    private User[] users;
    private int usersSize;
    private int highestBid;
    private ArrayList<Observer> observerList;

    // ~ Constructors ..........................................................
    /**
     * Creates a BiddingManager with auction items and empty user and observer
     * lists
     */
    public BiddingManager()
    {
        auctionQueue = new Queue<Item>();
        userQueue = new Queue<User>();
        users = new User[4];
        usersSize = 0;
        observerList = new ArrayList<Observer>();

        Random rand = new Random();
        int itemCount = 5;

        boolean[] used = new boolean[ItemCatalog.ItemPool.length];

        for (int i = 0; i < itemCount; i++)
        {
            int index = rand.nextInt(ItemCatalog.ItemPool.length);

            while (used[index])
            {
                index = rand.nextInt(ItemCatalog.ItemPool.length);
            }

            used[index] = true;

            Item item = ItemCatalog.ItemPool[index];
            auctionQueue.enqueue(item);
        }

        highestBid = auctionQueue.peek().getStartingBid();
    }
    // ~Public Methods ........................................................


    /**
     * Adds a new user to the auction
     */
    public void addUser()
    {
        for (int i = 0; i < users.length; i++)
        {
            if (users[i] == null)
            {
                users[i] = new User();
                usersSize++;
                notifyObservers();
                return;
            }
        }
    }


    /**
     * Finds a user with the given ID
     *
     * @param id
     *            the ID to search for
     * @return the matching user or null if not found
     */
    public User findUser(int id)
    {
        for (User user : users)
        {
            if (user != null && user.getID() == id)
            {
                return user;
            }
        }

        return null;
    }


    /**
     * Returns the auction queue
     *
     * @return the auction queue
     */
    public Queue<Item> getAuctionQueue()
    {
        return auctionQueue;
    }


    /**
     * Returns the current highest bid
     *
     * @return the highest bid
     */
    public int getHighestBid()
    {
        return highestBid;
    }


    /**
     * Returns the current winning user
     *
     * @return the winning user
     */
    public User getWinningUser()
    {
        return userQueue.peek();
    }


    // ----------------------------------------------------------
    /**
     * adds a bid and keeps track of the highest bid
     * 
     * @param bid
     *            the amount being bid
     * @param origin
     *            the user placing the bid
     */
    public void addBid(int bid, User origin)
    {

        if (origin == null || auctionQueue.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        if (bid <= 0 || bid < highestBid || bid > origin.getMoney())
        {
            throw new IllegalArgumentException();
        }

        if (bid > highestBid)
        {
            userQueue = new Queue<User>();
            highestBid = bid;
        }

        userQueue.enqueue(origin);
        notifyObservers();
    }


    // ----------------------------------------------------------
    /**
     * Dequeue the current item and enqueue it to the back.
     */
    public void postponeBid()
    {
        Item first = auctionQueue.dequeue();
        Item updated = new Item(
            new String(first.getItemName()),
            first.getStartingBid() / 2,
            first.getBoughtInValue());
        auctionQueue.enqueue(updated);
        notifyObservers();
    }


    /**
     * Adds an observer to the observer list
     *
     * @param o
     *            the observer to add
     */
    public void addObserver(Observer o)
    {
        if (o != null)
        {
            observerList.add(o);
        }
    }


    /**
     * Notifies observers when auction information changes
     */
    private void notifyObservers()
    {
        Item newItem = null;
        User winningUser = null;

        if (!auctionQueue.isEmpty())
        {
            newItem = auctionQueue.peek();
        }

        if (!userQueue.isEmpty())
        {
            winningUser = userQueue.peek();
        }

        for (Observer observer : observerList)
        {
            observer.update(highestBid, newItem, winningUser);
        }
    }


    /**
     * Finishes the auction and determines the winner Updates money and
     * inventory if the item sells
     */
    public void finishAuction()
    {
        if (auctionQueue.isEmpty())
        {
            return;
        }

        Item item = auctionQueue.peek();

        if (!userQueue.isEmpty() && highestBid >= item.getBoughtInValue())
        {
            User winner = userQueue.peek();
            winner.setMoney(winner.getMoney() - highestBid);
            winner.inventoryAdd(item);
        }

        notifyObservers();
    }


    /**
     * Resets variables used to finish auction
     * 
     * @param shouldDequeue
     */
    public void resetFinish(boolean shouldDequeue)
    {
        if (shouldDequeue)
        {
            auctionQueue.dequeue();
        }
        userQueue = new Queue<User>();
        if (auctionQueue.peek() != null)
        {
            highestBid = auctionQueue.peek().getStartingBid();
        }
        else
        {
            highestBid = 0;
        }
        notifyObservers();
    }


    // ----------------------------------------------------------
    /**
     * Calculates final rankings
     * 
     * @return list of standings in string format
     */
    public List<String> calculateFinalRanking()
    {
        ArrayList<String> standings = new ArrayList<String>();
        int[] used = new int[usersSize];
        int usedInterval = 0;
        
        for (int i = 0; i < usersSize; i++) {
            used[i] = -1;
        }
        
        for (int i = 0; i < usersSize; i++)
        {
            int worthCounter = 0;
            int associatedIterator = 0;
            User associatedUser = null;
            
            
            for (int j = 0; j < usersSize; j++)
            {
                int netWorth = getNetWorth(users[j]);
                if (worthCounter < netWorth && !contains(j, used))
                {
                    worthCounter = netWorth;
                    associatedIterator = j;
                    associatedUser = users[j];
                }
            }
            used[usedInterval] = associatedIterator;
            usedInterval++;


            if (associatedUser != null)
            {
                standings.add(
                    "$" + worthCounter + ": Player " + associatedUser.getID());
            }
            
        }

        return standings;
    }


    private int getNetWorth(User user)
    {
        int netWorth = user.getMoney();
        for (Item item : user.getInventory())
        {
            netWorth += item.getBoughtInValue();
        }
        return netWorth;
    }
    
    private boolean contains(int target, int[] used) {
        for (int usedInt: used) {
            if (target==usedInt) {
                return true;
            }
        }
        return false;
    }
}
