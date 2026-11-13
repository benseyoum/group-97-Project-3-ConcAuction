package concauction;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Console presentation for ConcAuction.
 * Auction decisions belong to the model;
 * validation and navigation belong to the controllers.
 *
 * @author Ben Seyoum (benseyoum)
 * @version 2026-11-13
 */
public class ConsoleView implements Observer
{
    private ActionController actionController;
    private NavigationController navigationController;
    private final Scanner input;
    private final PrintStream output;

    private int highestBid;
    private String auctionItem;
    private User currentWinningUser;
    private Item currentAuctionItem;
    private int playerCount;
    private List<String> finalStandingLines;

    public ConsoleView(
        ActionController actionController,
        NavigationController navigationController)
    {
        this(actionController, navigationController,
            new Scanner(System.in), System.out);
    }

    ConsoleView(
        ActionController actionController,
        NavigationController navigationController,
        Scanner input,
        PrintStream output)
    {
        this.actionController = actionController;
        this.navigationController = navigationController;
        this.input = input;
        this.output = output;
        this.auctionItem = "";
        this.finalStandingLines = new ArrayList<String>();
    }

    public void setActionController(ActionController controller)
    {
        actionController = controller;
    }

    public void setNavigationController(NavigationController controller)
    {
        navigationController = controller;
    }

    public void setPlayerCount(int count)
    {
        playerCount = count;
    }

    public void setCurrentAuctionItem(Item item)
    {
        currentAuctionItem = item;
    }

    public void setFinalStandings(List<String> lines)
    {
        finalStandingLines = new ArrayList<String>();

        if (lines != null)
        {
            finalStandingLines.addAll(lines);
        }
    }

    @Override
    public void update(int bid, String itemName, User leadingUser)
    {
        highestBid = bid;
        auctionItem = itemName == null ? "" : itemName;
        currentWinningUser = leadingUser;
    }

    public void userSelectMenu()
    {
        output.println("\n=== CONCAUCTION ===");
        output.println("Each player starts with $1000.");
        output.print("Number of players (2-4): ");
    }

    public void auctionMenu()
    {
        output.println("\n------------------------------");

        if (auctionItem.isEmpty())
        {
            output.println("No active auction item.");
            return;
        }

        output.println("Item: " + auctionItem);
        output.println("Current highest bid: $" + highestBid);

        if (currentWinningUser == null)
        {
            output.println("Leading player: none yet");
        }
        else
        {
            output.println(
                "Leading player: " + currentWinningUser.getID());
        }

        output.println("------------------------------");
    }

    public void userBidSelectMenu()
    {
        output.println("\nChoose a player to bid"
            + (playerCount > 0
                ? " (" + playerCount + " players)"
                : "")
            + ".");

        output.print(
            "Enter player ID, or 0 to finish this auction: ");
    }

    public void biddingMenu()
    {
        if (actionController != null
            && actionController.getCurrentBidder() != null)
        {
            User bidder = actionController.getCurrentBidder();

            output.println(
                "Player " + bidder.getID()
                + " has $" + bidder.getMoney()
                + " available.");
        }

        output.print(
            "Enter bid (at least $" + highestBid + "): ");
    }

    public void bidWinScreenMenu()
    {
        output.println("\n=== AUCTION RESULT ===");

        if (currentAuctionItem == null)
        {
            output.println("Auction item unavailable.");
            return;
        }

        output.println(
            "Item: " + currentAuctionItem.getItemName());

        boolean sold = currentWinningUser != null
            && currentWinningUser.getInventory()
                .contains(currentAuctionItem);

        if (sold)
        {
            output.println(
                "Sold to player "
                + currentWinningUser.getID()
                + " for $" + highestBid + "!");

            output.println(
                "Remaining balance: $"
                + currentWinningUser.getMoney());
        }
        else
        {
            output.println(
                "Unsold: no qualifying winning bid.");
        }
    }

    public void finalWinScreenMenu()
    {
        output.println("\n=== FINAL STANDINGS ===");

        if (finalStandingLines.isEmpty())
        {
            output.println(
                "Standings not supplied yet by "
                + "the model/controller.");
        }
        else
        {
            for (String line : finalStandingLines)
            {
                output.println(line);
            }
        }
    }

    public void errorMenu()
    {
        showError(
            "Invalid selection or bid. Please try again.");
    }

    public void showError(String message)
    {
        output.println("Error: " + message);
    }

    public String readInput()
    {
        if (!input.hasNextLine())
        {
            return null;
        }

        return input.nextLine().trim();
    }

}
