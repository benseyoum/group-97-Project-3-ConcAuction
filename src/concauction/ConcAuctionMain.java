package concauction;

/**
 * Starts the ConcAuction console application. Connects the model, view, and
 * controllers.
 *
 * @author Ben Seyoum (benseyoum)
 * @version 2026-11-13
 */
public class ConcAuctionMain
{
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // Init
        BiddingManager model = new BiddingManager();

        ConsoleView view = new ConsoleView(null, null);

        ActionController bids = new ActionController(model, view);

        NavigationController navigation =
            new NavigationController(model, view, bids);

        view.setActionController(bids);
        view.setNavigationController(navigation);

        model.addObserver(view);

        Item first = model.getAuctionQueue().peek();
        view.update(model.getHighestBid(), first.getItemName(), null);

        // Check user select menu
        boolean userSelectMenuValid = false;
        view.userSelectMenu();
        while (!userSelectMenuValid)
        {
            String playerText = view.readInput();
            userSelectMenuValid = navigation.checkUserSelect(playerText);
        }

        // Main auctioning loop

        while (!model.getAuctionQueue().isEmpty())
        {
            view.userBidSelectMenu();
            String playerText = "";
            
            boolean userBidSelectMenuValid = false;
            while (!userBidSelectMenuValid)
            {
                playerText = view.readInput();
                userBidSelectMenuValid =
                    navigation.checkUserBidSelect(playerText);
            }
            
            if (!playerText.equals("quit") && !playerText.equals("skip")) {
                boolean biddingMenuValid = false;
                while (!biddingMenuValid)
                {
                    String bidText = view.readInput();
                    biddingMenuValid = bids.checkBiddingInput(bidText);
                }
            }
        }

        // Final standings will be provided by the
        // model/controller once the ranking is ready.

        view.finalWinScreenMenu();
    }
}
