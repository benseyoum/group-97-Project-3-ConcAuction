package concauction;

// -------------------------------------------------------------------------
/**
 *  Observer interface for BiddingManager
 * 
 *  @author Alex Zhao (zalex)
 *  @version Nov 12, 2026
 */
public interface Observer {
    // ----------------------------------------------------------
    /**
     * Update observer
     * @param highestBid 
     * @param auctionItem 
     * @param currentWinningUser 
     */
    void update(int highestBid, String auctionItem, User currentWinningUser);
}