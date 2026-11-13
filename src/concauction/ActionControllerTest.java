package concauction;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class for ActionController
 * 
 * @author Alex Zhao (zalex)
 * @version Nov 10, 2026
 */
public class ActionControllerTest
    extends student.TestCase
{
    private ActionController actionController;
    private ActionController emptyActionController;
    private NavigationController navigationController;
    private BiddingManager biddingManager;
    private ConsoleView view;

    // ----------------------------------------------------------
    /**
     * Initializes testing values
     */
    public void setUp()
    {
        biddingManager = new BiddingManager();

        view = new ConsoleView(null, null);

        actionController = new ActionController(biddingManager, view);
        view.setActionController(actionController);

        navigationController =
            new NavigationController(biddingManager, view, actionController);
        view.setNavigationController(navigationController);
    }


    // ----------------------------------------------------------
    /**
     * Tests if constructor works
     */
    public void testConstructor()
    {
        User testUser = new User();
        biddingManager.addBid(300, testUser);
        
        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("300");
        assertEquals(300, biddingManager.getHighestBid());

        emptyActionController = new ActionController(biddingManager, view);

        emptyActionController.setCurrentBidder(testUser);
        emptyActionController.checkBiddingInput("400");
        assertEquals(400, biddingManager.getHighestBid());

    }


    // ----------------------------------------------------------
    /**
     * Tests if a valid bid processes
     */
    public void testValidBid()
    {
        User testUser = new User();
        biddingManager.addBid(200, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("300");

        assertEquals(300, biddingManager.getHighestBid());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a negative bid is blocked
     */
    public void testNegativeBid()
    {
        User testUser = new User();
        biddingManager.addBid(200, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("-100");

        assertEquals(200, biddingManager.getHighestBid());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a zero bid is blocked
     */
    public void testZeroBid()
    {
        User testUser = new User();
        biddingManager.addBid(200, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("0");

        assertEquals(200, biddingManager.getHighestBid());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a low bid is blocked
     */
    public void testLowBid()
    {
        User testUser = new User();
        biddingManager.addBid(300, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("200");

        assertEquals(300, biddingManager.getHighestBid());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a equal bid processes
     */
    public void testEqualBid()
    {
        User testUser = new User();
        biddingManager.addBid(300, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("300");

        assertEquals(300, biddingManager.getHighestBid());

    }


    // ----------------------------------------------------------
    /**
     * Tests if a bid exceeding a user's budget is blocked
     */
    public void testTooHighBid()
    {
        User testUser = new User();
        testUser.setMoney(500);
        biddingManager.addBid(200, testUser);

        actionController.setCurrentBidder(testUser);
        actionController.checkBiddingInput("600");

        assertEquals(200, biddingManager.getHighestBid());

    }


    // ----------------------------------------------------------
    /**
     * Tests if a null bidder is blocked
     */
    public void testNullBid()
    {
        actionController.setCurrentBidder(null);
        actionController.checkBiddingInput("600");

        assertEquals(200, biddingManager.getHighestBid());

    }
}
