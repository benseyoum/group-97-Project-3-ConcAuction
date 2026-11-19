package concauction;

// -------------------------------------------------------------------------
/**
 * Receives bid inputs from ConsoleVie and validates them before passing them to
 * BiddingManager. It checks that bids are valid. If valid, it calls the
 * corresponding BiddingManager method to process it. If invalid, it signals
 * ConsoleView to display an error and request another bid.
 * 
 * @author Alex Zhao (zalex)
 * @version 2026.11.9
 */
public class ActionController
{
    // ~ Fields ................................................................
    private BiddingManager biddingManager;
    private ConsoleView view;
    private User currentBidder;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Create a new ActionController object.
     * 
     * @param biddingManager
     * @param consoleView
     */
    public ActionController(
        BiddingManager biddingManager,
        ConsoleView consoleView)
    {
        if (biddingManager != null)
        {
            this.biddingManager = biddingManager;
        }
        else
        {
            this.biddingManager = new BiddingManager();
        }

        if (consoleView != null)
        {
            view = consoleView;
        }
        else
        {
            NavigationController navigationController =
                new NavigationController(biddingManager, view, this);
            view = new ConsoleView(this, navigationController);
        }
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks if the bid input is valid
     * 
     * @param bid
     * @return true if all checks passed
     */
    public boolean checkBiddingInput(String bid)
    {
        try
        {
            int bidInt = Integer.parseInt(bid);
            if (currentBidder == null) // Null bidder check
            {
                errorAndAuctionMenu();
                return false;
            }
            if (bidInt <= 0) // Non-positive check
            {
                errorAndAuctionMenu();
                return false;

            }
            if (bidInt > currentBidder.getMoney()) // Exceeded money check
            {
                errorAndAuctionMenu();
                return false;

            }
            if (bidInt < biddingManager.getHighestBid()) // Under money
                                                         // check
            {
                errorAndAuctionMenu();
                return false;

            }

            biddingManager.addBid(bidInt, currentBidder);
            view.auctionMenu();
            return true;
        }
        catch (NumberFormatException e) // Non-int check
        {
            errorAndAuctionMenu();
            return false;
        }
    }


    // ----------------------------------------------------------
    /**
     * Get current bidder
     * 
     * @return current bidder
     */
    public User getCurrentBidder()
    {
        return currentBidder;
    }


    // ----------------------------------------------------------
    /**
     * Set current bidder
     * 
     * @param newBidder
     */
    public void setCurrentBidder(User newBidder)
    {
        currentBidder = newBidder;
    }

    // ~Private Methods ........................................................


    // ----------------------------------------------------------
    private void errorAndAuctionMenu()
    {
        view.auctionMenu();
        view.errorMenu();
    }
}
