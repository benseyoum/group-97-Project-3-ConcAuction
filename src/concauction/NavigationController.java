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
        boolean updateView = false;
        
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
            updateView = true;
            view = new ConsoleView(null, this);
        }
        
        if (actionController != null)
        {  
            this.actionController = actionController;
        }
        else
        {
            actionController = new ActionController(biddingManager, view);
        }
        
        if (updateView) {
            view.setActionController(actionController);
        }
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks if the user count is valid and runs next steps.
     * @param inputUserCount 
     */
    public void checkUserSelect(String inputUserCount)
    {
        int userCount;
        
        if (checkIfInt(inputUserCount)) {
            userCount = Integer.parseInt(inputUserCount);
            
            if (2 <= userCount && userCount <= 4) // User range check
            {
                for (int i = 0; i < userCount; i++)
                {
                    biddingManager.addUser();
                }
                view.auctionMenu();
            }
            else
            {
                view.errorMenu();
                view.userSelectMenu();
            }
        }
        else {
            view.errorMenu();
            view.userSelectMenu();
        }
    }


    // ----------------------------------------------------------
    /**
     * Checks if the user id to bid selection is valid and runs next steps.
     * @param inputUserId 
     */
    public void checkUserBidSelect(String inputUserId)
    {
        int userId;
       
        if (checkIfInt(inputUserId)) {
            userId = Integer.parseInt(inputUserId);
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
                    biddingManager.resetFinish();
                    view.auctionMenu();
                }
            }
            else
            {
                view.errorMenu();
                view.userBidSelectMenu();
            }
        }
        else {
            view.errorMenu();
            view.userBidSelectMenu();
        }
    }


    // ----------------------------------------------------------
    /**
     * Checks if the user's input can be parsed as a String
     * 
     * @param intInput
     * @return true if is int
     */
    public boolean checkIfInt(String intInput)
    {
        try
        {
            Integer.parseInt(intInput);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
