package concauction;

/**
 * Starts the ConcAuction console application.
 *
 * Connects the model, view, and controllers.
 *
 * @author Ben Seyoum (benseyoum)
 * @version 2026-11-13
 */
public class ConcAuctionMain
{
    public static void main(String[] args)
    {
        BiddingManager model = new BiddingManager();

        ConsoleView view = new ConsoleView(null, null);

        ActionController bids =
            new ActionController(model, view);

        NavigationController navigation =
            new NavigationController(model, view, bids);

        view.setActionController(bids);
        view.setNavigationController(navigation);

        model.addObserver(view);

        Item first = model.getAuctionQueue().peek();

        view.update(
            model.getHighestBid(),
            first.getItemName(),
            null);

        int numberOfPlayers;

        while (true)
        {
            view.userSelectMenu();

            String response = view.readInput();

            if (response == null)
            {
                return;
            }

            try
            {
                numberOfPlayers =
                    Integer.parseInt(response);
            }
            catch (NumberFormatException ex)
            {
                view.showError(
                    "Enter a whole number from 2 to 4.");
                continue;
            }

            if (numberOfPlayers < 2
                || numberOfPlayers > 4)
            {
                view.showError(
                    "Enter 2, 3, or 4 players.");
                continue;
            }

            navigation.checkUserSelect(response);
            view.setPlayerCount(numberOfPlayers);

            break;
        }

        while (!model.getAuctionQueue().isEmpty())
        {
            view.setCurrentAuctionItem(
                model.getAuctionQueue().peek());

            view.auctionMenu();

            while (true)
            {
                view.userBidSelectMenu();

                String playerText = view.readInput();

                if (playerText == null)
                {
                    return;
                }

                int playerId;

                try
                {
                    playerId =
                        Integer.parseInt(playerText);
                }
                catch (NumberFormatException ex)
                {
                    view.showError(
                        "Enter a numeric player ID, or 0.");
                    continue;
                }

                if (playerId == 0)
                {
                    navigation.checkUserBidSelect(playerText);
                    break;
                }

                if (model.findUser(playerId) == null)
                {
                    navigation.checkUserBidSelect(playerText);
                    continue;
                }

                navigation.checkUserBidSelect(playerText);

                String bidText = view.readInput();

                if (bidText == null)
                {
                    return;
                }

                bids.checkBiddingInput(bidText);
            }
        }

        // Final standings will be provided by the
        // model/controller once the ranking is ready.

        view.finalWinScreenMenu();
    }
}
