package concauction;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class for NavigationController
 * 
 * @author Alex Zhao (zalex)
 * @version Nov 10, 2026
 */
public class NavigationControllerTest
    extends student.TestCase
{
    private ActionController actionController;
    private NavigationController navigationController;
    private NavigationController emptyNavigationController;

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

        actionController = ActionController(biddingManager, view);
        view.setActionController(actionController);
        
        navigationController =
            NavigationController(biddingManager, view, actionController);
        view.setNavigationController(navigationController);
    }


    // ----------------------------------------------------------
    /**
     * Tests if constructor works
     */
    public void testConstructor()
    {
        Item temp1 = biddingManager.getAuctionQueue()[0];
        navigationController.checkUserSelect(3);

        emptyNavigationController =
            new NavigationController(biddingManager, view, actionController);

        emptyNavigationController.checkUserBidSelect(0);

        Item temp2 = biddingManager.getAuctionQueue()[0];

        assertNull(actionController.currentBidder());
        assertFalse(!temp1.getItemName().equals(temp0.getItemName()));
    }


    // ----------------------------------------------------------
    /**
     * Tests if a valid user count input processes
     */
    public void testUserCount()
    {
        navigationController.checkUserSelect(3);
        assertNotNull(biddingManager.findUser(3));
    }


    // ----------------------------------------------------------
    /**
     * Tests if a invalid high user count input is blocked
     */
    public void testInvalidUserCount()
    {
        navigationController.checkUserSelect(5);
        assertNull(biddingManager.findUser(1));
    }


    // ----------------------------------------------------------
    /**
     * Tests if a invalid low user count input is blocked
     */
    public void testLowUserCount()
    {
        navigationController.checkUserSelect(1);
        assertNull(biddingManager.findUser(1));
    }


    // ----------------------------------------------------------
    /**
     * Tests if a valid bid user id count input processes
     */
    public void testUserSelect()
    {
        navigationController.checkUserSelect(3);
        navigationController.checkUserBidSelect(1);
        assertNotNull(actionController.currentBidder());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a invalid bid user id count input is blocked
     */
    public void testInvalidUserSelect()
    {
        navigationController.checkUserSelect(3);
        navigationController.checkUserBidSelect(4);
        assertNull(actionController.currentBidder());
    }


    // ----------------------------------------------------------
    /**
     * Tests if a valid user id count input is blocked
     */
    public void testUserSelectZero()
    {
        Item temp1 = biddingManager.getAuctionQueue()[0];

        navigationController.checkUserSelect(3);
        navigationController.checkUserBidSelect(0);

        Item temp2 = biddingManager.getAuctionQueue()[0];

        assertNull(actionController.currentBidder());
        assertFalse(!temp1.getItemName().equals(temp0.getItemName()));
    }

}
