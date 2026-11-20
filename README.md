# ConcAuction

ConcAuction is an auction simulation program. It allows up to 4 players to simulate bidding on auction items. 

## Installation and Setup
The device or platform that you are running ConcAuction on must be able to run Java. 
After Java is set up, you can run the following command in terminal to clone this repository. 

```
git clone https://github.com/PIsForPotato/group-97-Project-3-ConcAuction
```

This should include the concauction package setup necessary to run. 

### Compiling and Running in Eclipse

#### Running the Model
To compile and run the model in the Eclipse IDE, run `BiddingManagerTest` as JUnit Test. `BiddingManager` represents the ultimate aggregation of the other model classes (`Item`, `Queue`, and `User`) and is used to represent the model as a whole by the controller and view. The test classes `ItemTest`, `QueueTest`, and `UserTest` can also be ran as JUnit Test to view smaller parts of the model. 

#### Running the View
To compile and run the view in the Eclipse IDE, run `ConcAuctionMain` as a Java Application because it contains the main method necessary to initialize the program loop. Any other IDE should similarly run `ConcAuctionMain` to start the main method loop.


### Running Tests in Eclipse
To run our JUnit tests, run any test classes as "JUnit Test." Test classes can be identified by the suffix Test in their file name (____Test.java). In this project, the following files should be ran for testing:
  - `ActionControllerTest`
  - `BiddingManagerTest`
  - `ConsoleViewTest`
  - `ItemCatalogTest`
  - `ItemTest`
  - `NavigationControllerTest`
  - `QueueTest`
  - `UserTest`

### Important note about JUnit testing
JUnit testing is done using the 'CS2-Support' package as provided by the CS 2114 class this project was built for. The project assumes that CS2-Support is both downloaded and within the project's build path. Anyone not in our university will not have access to said package. Therefore, unfortunately, our JUnit testing is not accessible to external viewers.

## Reflection

### How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?
Implementing the postponing feature toruched `BiddingManager`, `ConsoleView`, and `NavigationController`. These class' respective test classes were updated to test for the new feature. This matches what was expected. As expected, `ActionController`, `Item`, `Queue`, and `User` were not modified. Nothing about the touched classes was unexpected; however there were some surprises about their implementation WITHIN the touched classes. This will be elaborated on in the prompts and answers below. 

### Did the change cross the model/view line? Did the view have to learn anything new about the model? If a change rippled through layers it shouldn't have, where and why?
Yes, the change had to cross the model/view line. The user had to see the option presented in the view and make an input on the view to the controller which, after some validating, sends it to the model. The model is the one that handles all the data processing and manipulation of queues. Additionally, the view had to be able to visually update and respond once the model completes the postponing process; this was achieved by the same process described above but in reverse with the model sending to controller which validates into view. 

The view did not have to learn anything new about the model. The processes in view impacted by this feature largely centered around additional menu text and user instruction. 

Although, the controller had to learn some things about the model in order to properly validate certain inputs. Notably, the controller had to learn who the winning user, or the top of `BiddingManager`'s `userQueue`, is via `getWinningUser()`. Nonetheless, these changes were controlled and did not cause any major rippling across layers; that is to say, the model and view sitll remain independent. 

### What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.
The most notable change is how `NavigationController`'s `checkUserBidSelect(String inputUserId)`. Originally the parameter for this method was an integer; this is the same reason why the D3 plan mentions the use of numeric integer `0` as the quit key and the potential use of numeric integer `-1` as the skip (postpone) key as `0` and `-1` can not be player IDs. 

However, the current versions uses a String input that may be converted into numeric integers using `Integer.parseInt(inputUserId)` for player selection OR as specific keyword inputs. Currently, "quit" concludes the current auctioning round and "skip" postpones the current auction. In accordance to the original D3 plan, "skip" can only be used if no bids had been issued. 
This choice was made to improve user experience as `-1` is unintuitive and clunky for a user input. Additionially, this solution takes advantage of how user inputs are natively read as Strings.

### What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.
All MVP features and their associated testing and bad-input cases are working or handled. The next steps include polish for user experience and additional stretch goals. 

### What design change would make the next feature cheaper?
Across both prototypes, we often found that we underestimated what data we had to pull. This meant we often had to spend time adding extra getters. For example, `BiddingManager ` originally lacked a getter for `highestBid`, but this was implemented later as it an incredibly useful value, especially for user experience. 
Adding more getters (and setters; although it is more likely setters will be unnecessary) would make future features cheaper as we won't have to either use alternative methods to retrieve a related value (as in the case for some of the test cases) or spend time implementing unplanned ones. 

## Next Steps 
### What is left for the final build? Remaining features and fixes, in order.
All MVP features are complete along with postpone bids and randomized items. Our priorities right now are to finish the stretch goals of auction history and bidding time limit. We also will continue developing the GUI and testing any new features.

### Which stretch goals are you committing to, and which are you dropping?
We have completed postpone bids and randomized items. We plan on continuing the Auction History and Bidding time limit. The GUI is also something we would like to complete. As of now, we have not planned to drop any features.

### What will your 3-minute demo show? The path you'll walk through, including the new feature.
Our demo will start by starting a game and selecting players. As of right now the game will have five generated items and are randomly selected from our catalog with no repeats.
  - Showcase all bad inputs including nothing, non-valid integer, and Strings
  - Enter 3 for 3 users
We will show our bidding process:
  - Item 1 will showcase the regular process with multiple players placing increasingly higher bids.
  - Item 2 will showcase ties and the implementation of `userQueue` by having each player bid the same value. Bad input handling of `checkBiddingInput(String bid)` by showing no bid, low bid, high bid, and String input. 
  - Item 3 will showcase the bad input handling of `checkUserBidSelect(String inputUserId)` by showing invalid integer, String keyword, and null inputs. Then, it will showcase the postpone feature using the 'skip' keyword.
  - Item 4 will showcase what happens when the 'quit' keyword is used before any bids (no valid winner)
  - Item 5 will showcase what happens when the users bid below the hidden `boughtInValue` (no valid winner)
  - Item 3 (Postponed) will show how postponed items have a lower starting bid value than their first iteration. Quit out immediately.
The final winning screen now appears.
  - Showcase how the players are ordered and explain the way `netWorth` is internally calculated as it is purposefully hidden to users. 
Once completed we will also show the auction history and bidding time limit.
  - Bidding time limit will be introduced with Item 1
  - Auction history will be introduced with Item 3 (Postponed)

### Specific timing plan
  - 0:00 - Intro and player selection showcase
  - 0:25 - Item 1 
  - 0:50 - Item 2
  - 1:30 - Item 3
  - 2:00 - Item 4
  - 2:10 - Item 5
  - 2:30 - Item 3 (Postponed)
  - 2:50 - Final screen
  - 3:00 - Done

### Who does what? Which team member owns each remaining item.
| Feature | Owner | 
| --- | --- |
| GUI | Ben | 
| Bidding Time Limit | Alex |
| Auction History | Krish |
As of right now, Ben will be handling the GUI. Krish will be handling the auction history and Alex will be handling the bidding time limit.
However, this is all subject to change and we can all each go further in depth on certain features depending on time and any problems that arise.

## Attribution Table 
| Class | Contributor(s) | Contribution |
| --- | --- | --- |
| GUI | Ben | WIP |
| Auction History | Krish | WIP |
| Randomized Items and Values | Krish | Implemented with `ItemCatalog`, `ItemCatalogTest`, and updates to `BiddingManager`'s initialization. |
| Bidding Time Limit | Alex | WIP |
| Postpone Bids | Alex | Implemented with `ConsoleView`'s `postponeMenu()`, new branch in `NavigationController`'s `checkUserBidSelect(String inputUserId)`, and `BiddingManager`'s `postponeAuction()` along with their related test cases.  |
| Previous Prototype Code Cleanup | Alex | Ensured consistency in `ConsoleView` prints and the function of final winner reports. |


**GenAI Note**: 
- Alex: has not used any GenAI. 
- Krish: Used AI to generate Items for the game as well as making sure in the test every part was executed.
- Ben: 
