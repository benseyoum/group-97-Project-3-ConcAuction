package concauction;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console presentation for ConcAuction. Auction decisions belong to the model;
 * validation and navigation belong to the controllers.
 *
 * @author Ben Seyoum (benseyoum)
 * @version 2026-11-13
 */
public class ConsoleView
    implements Observer
{
    private ActionController actionController;
    @SuppressWarnings("unused")
    private NavigationController navigationController;
    private final Scanner input;
    private final PrintStream output;

    private int highestBid;
    private String currentAuctionItemName;
    private User currentWinningUser;
    private Item currentAuctionItem;
    private int playerCount;
    private List<String> finalStandingLines;

    // ----------------------------------------------------------
    /**
     * Create a new ConsoleView object.
     * 
     * @param actionController
     * @param navigationController
     */
    public ConsoleView(
        ActionController actionController,
        NavigationController navigationController)
    {
        this(
            actionController,
            navigationController,
            new Scanner(System.in),
            System.out);
    }


    // ----------------------------------------------------------
    /**
     * Create a new ConsoleView object.
     * 
     * @param actionController
     * @param navigationController
     * @param input
     * @param output
     */
    public ConsoleView(
        ActionController actionController,
        NavigationController navigationController,
        Scanner input,
        PrintStream output)
    {
        this.actionController = actionController;
        this.navigationController = navigationController;
        this.input = input;
        this.output = output;
        this.currentAuctionItemName = "";
        this.finalStandingLines = new ArrayList<String>();
    }


    // ----------------------------------------------------------
    /**
     * Set action controller
     * 
     * @param controller
     */
    public void setActionController(ActionController controller)
    {
        actionController = controller;
    }


    // ----------------------------------------------------------
    /**
     * Set navigation controller
     * 
     * @param controller
     */
    public void setNavigationController(NavigationController controller)
    {
        navigationController = controller;
    }


    // ----------------------------------------------------------
    /**
     * Set player count
     * 
     * @param count
     */
    public void setPlayerCount(int count)
    {
        playerCount = count;
    }


    // ----------------------------------------------------------
    /**
     * Set current action item
     * 
     * @param item
     */
    public void setCurrentAuctionItem(Item item)
    {
        currentAuctionItem = item;
    }


    // ----------------------------------------------------------
    /**
     * Set final standings
     * 
     * @param lines
     */
    public void setFinalStandings(List<String> lines)
    {
        finalStandingLines = new ArrayList<String>();

        if (lines != null)
        {

            finalStandingLines.addAll(lines);
        }
    }


    @Override
    public void update(int bid, Item item, User leadingUser)
    {
        highestBid = bid;
        currentAuctionItem = item;
        currentWinningUser = leadingUser;
        
        if (item != null) {
            currentAuctionItemName = item.getItemName();
        }
        else {
            currentAuctionItemName = "";
        }
    }


    // ----------------------------------------------------------
    /**
     * View user select menu
     */
    public void userSelectMenu()
    {
        output.println("\n=== CONCAUCTION ===");
        output.println("Each player starts with $1000.");
        output.print("Number of players (2-4): ");
    }


    // ----------------------------------------------------------
    /**
     * View auction menu
     */
    public void auctionMenu()
    {
        output.println("------------------------------");

        if (currentAuctionItemName.isEmpty())
        {
            output.println("No active auction item.");
            return;
        }

        output.println("Item: " + currentAuctionItemName);
        output.println("Current highest bid: $" + highestBid);

        if (currentWinningUser == null)
        {
            output.println("Leading player: none yet");
        }
        else
        {
            output.println("Leading player: " + currentWinningUser.getID());
        }

    }


    // ----------------------------------------------------------
    /**
     * View user bid select menu
     */
    public void userBidSelectMenu()
    {
        output.println("------------------------------");
        output.println(
            "Choose a player to bid"
                + (playerCount > 0 ? " (" + playerCount + " players)" : "")
                + ".");

        output.print("Enter player ID\nEnter 'quit' to finish this auction\n");
    }


    // ----------------------------------------------------------
    /**
     * Prints when the user(s) are allowed to skip
     */
    public void userBidSelectMenuSkipLine()
    {
        output.print("Enter 'skip' to postpone current item\n");
    }


    // ----------------------------------------------------------
    /**
     * View postpone success menu
     */
    public void postponeMenu()
    {
        output.println("------------------------------");
        output
            .println("Successfully postponed " + currentAuctionItemName + "!");
    }


    // ----------------------------------------------------------
    /**
     * View bidding menu
     */
    public void biddingMenu()
    {
        output.println("------------------------------");
        if (actionController != null
            && actionController.getCurrentBidder() != null)
        {
            User bidder = actionController.getCurrentBidder();

            output.println(
                "Player " + bidder.getID() + " has $" + bidder.getMoney()
                    + " available.");
        }

        output.print("Enter bid (at least $" + highestBid + "): ");
    }


    // ----------------------------------------------------------
    /**
     * View bid win menu
     */
    public void bidWinScreenMenu()
    {
        output.println("\n=== AUCTION RESULT ===");

        if (currentAuctionItem == null)
        {
            output.println("Auction item unavailable.");
            return;
        }

        output.println("Item: " + currentAuctionItem.getItemName());

        boolean sold = currentWinningUser != null
            && currentWinningUser.getInventory().contains(currentAuctionItem);

        if (sold)
        {
            output.println(
                "Sold to player " + currentWinningUser.getID() + " for $"
                    + highestBid + "!");

            output.println(
                "Remaining balance: $" + currentWinningUser.getMoney());
        }
        else
        {
            output.println("Unsold: no qualifying winning bid.");
            output.println(
                "Bought in value: $" + currentAuctionItem.getBoughtInValue());
        }
    }


    // ----------------------------------------------------------
    /**
     * View final winner menu
     */
    public void finalWinScreenMenu()
    {
        output.println("\n=== FINAL STANDINGS ===");

        if (finalStandingLines.isEmpty())
        {
            output.println(
                "Standings not supplied yet by " + "the model/controller.");
        }
        else
        {
            for (String line : finalStandingLines)
            {
                output.println(line);
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * View error menu
     */
    public void errorMenu()
    {
        output.println("------------------------------");
        showError("Invalid selection or bid. Please try again.");
    }


    // ----------------------------------------------------------
    /**
     * Show specific error
     * 
     * @param message
     */
    public void showError(String message)
    {
        output.println("Error: " + message);
    }


    // ----------------------------------------------------------
    /**
     * Read user input
     * 
     * @return String user input
     */
    public String readInput()
    {
        if (!input.hasNextLine())
        {
            return null;
        }

        return input.nextLine().trim();
    }

}
