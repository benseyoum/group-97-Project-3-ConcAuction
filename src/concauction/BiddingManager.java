package concauction;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 *  Manages auctions, users and bids.
 * 
 *  @author krish
 *  @version Nov 11, 2026
 */
public class BiddingManager
{
    //~ Fields ................................................................
    private Queue<Item> auctionQueue;
    private Queue<User> userQueue;
    private User[] users;
    private int highestBid;
    private ArrayList<Observer> observerList;
    //~ Constructors ..........................................................
    /**
     * Creates a BiddingManager with auction items
     * and empty user and observer lists
     */
    public BiddingManager()
    {
        auctionQueue = new Queue<Item>();
        userQueue = new Queue<User>();
        users = new User[4];
        observerList = new ArrayList<Observer>();
        
        highestBid = auctionQueue.peek().getStartingBid();
        
    }
    //~Public  Methods ........................................................
    
    
    
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
                return;
            }
        }
    }
    
    
    
    /**
     * Finds a user with the given ID
     *
     * @param id the ID to search for
     *            
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
    
    
    
    // ----------------------------------------------------------
    /**
     * adds a bid and keeps track of the highest bid
     * @param bid the amount being bid
     * @param origin the user placing the bid
     */
    public void addBid(int bid, User origin)
    {
        
        if (origin == null || auctionQueue.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        if (bid <= 0 || bid < highestBid
            || bid > origin.getMoney())
        {
            throw new IllegalArgumentException();
        }

        if (bid > highestBid)
        {
            highestBid = bid;
            userQueue = new Queue<User>();
        }

        userQueue.enqueue(origin);
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
        String itemName = "";
        User winningUser = null;

        if (!auctionQueue.isEmpty())
        {
            itemName = auctionQueue.peek().getItemName();
        }

        if (!userQueue.isEmpty())
        {
            winningUser = userQueue.peek();
        }

        for (Observer observer : observerList)
        {
            observer.update(highestBid, itemName, winningUser);
        }
    }
    
    
    
    /**
     * Finishes the auction and determines the winner
     * Updates money and inventory if the item sells
     */
    public void finishAuction()
    {
        if (auctionQueue.isEmpty())
        {
            return;
        }

        Item item = auctionQueue.peek();

        if (!userQueue.isEmpty()
            && highestBid >= item.getBoughtInValue())
        {
            User winner = userQueue.peek();

            winner.setMoney(winner.getMoney() - highestBid);
            winner.inventoryAdd(item);
        }
        
        auctionQueue.dequeue();

      
    }


}
