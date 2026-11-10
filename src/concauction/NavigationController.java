package concauction;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Alex Zhao (zalex)
 * @version 2026.11.9
 */
public class NavigationController
{
    // ~ Fields ................................................................
    private BiddingManager biddingManager;
    private ConsoleView view;
    private ActionController actionController;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Create a new NavigationController object.
     * 
     * @param biddingManager
     * @param consoleView
     * @param actionController
     */
    public NavigationController(
        BiddingManager biddingManager,
        ConsoleView consoleView,
        ActionController actionController)
    {
        if (biddingManager != null)
        {
            this.biddingManager = biddingManager;
        }
        else
        {
            this.biddingManager = new BiddingManager();
        }

        if (actionController != null)
        {
            this.actionController = actionController;
        }
        else
        {
            actionController = new ActionController(biddingManager, null);
        }

        if (consoleView != null)
        {
            view = consoleView;
        }
        else
        {
            view = new ConsoleView(actionController, this);
        }
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks if the user count is valid.
     * 
     * @param userCount
     */
    public void checkUserSelect(int userCount)
    {
        if (2 <= userCount && userCount <= 4) // User range check
        {
            for (int i = 0; i < userCount; i++)
            {
                biddingManager.addUser();
                view.userSelectMenu();
            }
        }
        else
        {
            view.errorMenu();
            view.userSelectMenu();
        }
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param userId
     */
    public void checkUserBidSelect(int userId)
    {
        User user = biddingManager.findUser(userId);
        if (user != null) // Null user check
        {
            actionController.setCurrentBidder(user);
            view.biddingMenu();
        }
        else if (userId == 0) // Finish check
        {
            biddingManager.finishAuction();
            if (biddingManager.getAuctionQueue().isEmpty()) // No auction items
                                                            // left check
            {
                view.finalWinScreenMenu();
            }
            else
            {
                view.bidWinScreenMenu();
            }
        }
        else
        {
            view.errorMenu();
            view.userBidSelectMenu();
        }
    }
}
