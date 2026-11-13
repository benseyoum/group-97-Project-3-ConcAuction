package concauction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import student.TestCase;

/**
 * Tests for ConsoleView.
 *
 * @author Ben Seyoum (benseyoum)
 * @version 2026-11-13
 */

public class ConsoleViewTest extends TestCase
{
    private ByteArrayOutputStream outputBytes;
    private ConsoleView view;

    public void setUp()
    {
        outputBytes = new ByteArrayOutputStream();

        view = new ConsoleView(
            null,
            null,
            new Scanner("3\n"),
            new PrintStream(outputBytes));
    }

    public void testUserSetupMenu()
    {
        view.userSelectMenu();

        assertTrue(
            outputBytes.toString()
                .contains("Number of players (2-4)"));

        assertEquals("3", view.readInput());
    }

    public void testAuctionMenuWithoutUpdate()
    {
        view.auctionMenu();

        assertTrue(
            outputBytes.toString()
                .contains("No active auction item"));
    }

    public void testObserverUpdateAndAuctionMenu()
    {
        User bidder = new User();

        view.update(350, "Laptop", bidder);
        view.auctionMenu();

        String printed = outputBytes.toString();

        assertTrue(printed.contains("Item: Laptop"));
        assertTrue(
            printed.contains("Current highest bid: $350"));

        assertTrue(
            printed.contains(
                "Leading player: " + bidder.getID()));
    }

    public void testNullUpdateSafe()
    {
        view.update(0, null, null);
        view.auctionMenu();

        assertTrue(
            outputBytes.toString()
                .contains("No active auction item"));
    }

    public void testBiddingMenuWithoutController()
    {
        view.biddingMenu();

        assertTrue(
            outputBytes.toString().contains("Enter bid"));
    }

    public void testUserSelectionMenu()
    {
        view.setPlayerCount(3);
        view.userBidSelectMenu();

        assertTrue(
            outputBytes.toString().contains("3 players"));

        assertTrue(
            outputBytes.toString().contains("0 to finish"));
    }

    public void testSoldItemScreen()
    {
        User winner = new User();
        Item laptop = new Item("Laptop", 200, 250);

        winner.inventoryAdd(laptop);
        winner.setMoney(700);

        view.setCurrentAuctionItem(laptop);
        view.update(300, "Laptop", winner);
        view.bidWinScreenMenu();

        assertTrue(
            outputBytes.toString().contains(
                "Sold to player "
                + winner.getID()
                + " for $300"));

        assertTrue(
            outputBytes.toString()
                .contains("Remaining balance: $700"));
    }

    public void testUnsoldItemScreen()
    {
        User highestBidder = new User();
        Item laptop = new Item("Laptop", 200, 500);

        view.setCurrentAuctionItem(laptop);
        view.update(300, "Laptop", highestBidder);
        view.bidWinScreenMenu();

        assertTrue(
            outputBytes.toString().contains("Unsold"));
    }

    public void testErrorMenu()
    {
        view.errorMenu();

        assertTrue(
            outputBytes.toString()
                .contains("Invalid selection or bid"));
    }

    public void testFinalStandingsProvidedByModel()
    {
        view.setFinalStandings(Arrays.asList(
            "1. Player 2 - $1200",
            "2. Player 1 - $1050"));

        view.finalWinScreenMenu();

        String printed = outputBytes.toString();

        assertTrue(printed.contains("FINAL STANDINGS"));
        assertTrue(printed.contains("Player 2 - $1200"));
    }
}
