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
                NavigationController(biddingManager, view, this);
            view = new ConsoleView(navigationController, this);
        }
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks if the bid input is valid
     * 
     * @param bid
     */
    public void checkBiddingInput(String bid)
    {
        try
        {
            int bidInt = Integer.parseInt(bid);
            if (currentBidder == null) // Null bidder check
            {
                errorAndAuctionMenu();
            }
            else
            {
                if (bidInt <= 0) // Non-positive check
                {
                    errorAndAuctionMenu();

                }
                if (bidInt > currentBidder.getMoney()) // Exceeded money check
                {
                    errorAndAuctionMenu();
                }
                if (bidInt < biddingManager.getHighestBid()) // Under money check
                {
                    errorAndAuctionMenu();
                }
                
                biddingManager.addBid(bid, currentBidder);
                view.auctionMenu();
            }
        }
        catch (NumberFormatException e) // Non-int check
        {
            errorAndAuctionMenu();
        }
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
        view.errorMenu();
        view.auctionMenu();
    }
}
