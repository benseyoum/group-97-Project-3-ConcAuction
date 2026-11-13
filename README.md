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
WIP

#### Running the Model
To run the model, run `BiddingManagerTest` as JUnit Test. `BiddingManager` represents the ultimate aggregation of the other model classes (`Item`, `Queue`, and `User`) and is used to represent the model as a whole by the controller and view. The test classes `ItemTest`, `QueueTest`, and `UserTest` can also be ran as JUnit Test to view smaller parts of the model. 

#### Running the Thin View
WIP

### Running Tests in Eclipse
To run our JUnit tests, run any test classes as "JUnit Test." Test classes can be identified by the suffix Test in their file name (____Test.java). In this project, the following files should be ran for testing:
  - `ActionControllerTest`
  - `BiddingManagerTest`
  - `ConsoleViewTest`
  - `ItemTest`
  - `NavigationControllerTest`
  - `QueueTest`
  - `UserTest`

### Important note about JUnit testing
JUnit testing is done using the 'CS2-Support' package as provided by the CS 2114 class this project was built for. The project assumes that CS2-Support is both downloaded and within the project's build path. Anyone not in our university will not have access to said package. Therefore, unfortunately, our JUnit testing is not accessible to external viewers.

## Reflection
### Changes from Specification

**`BiddingManager` Changes**
- Originally, method `finishAuction()` was responsible for inventory management and resetting values for the next auction.
  - Tasks were split into `finishAuction()` which managed inventory and `resetFinish()` which resets values.
  - Reasoning is so that the controller can generate the win screen with the correct un-reset values before generating a new auction screen with the reset values
- Originally, field `auctionQueue` had no getter.
  - Added method `getAuctionQueue()`
  - Reasoning is that this supports `ActionControllerTest`, `NavigationController`, and `NavigationControllerTest`
- Originally, field `highestBid` had no getter.
  - Added method `getHighestBid()`
  - Reasoning is that this supports `ActionControllerTest`


**`ConsoleView` Changes**
- Originally, fields `actionController` and `navigationController` could not change after construction
  - Both were changed to changeable via setters
  - Reasoning is to enable better initialization of `ConsoleView` by allowing placeholders null values to be edited into proper controller references.
- Originally did not include setters for fields `actionController` and `navigationController`
  - Added methods `setActionController(ActionController actionController)` and `setNavigationController(NavigationController navigationController)`
  - Reasoning is the same as the point above.

**`ActionController` Changes**
- Originally field `currentBidder` had no getter
  - Added method `getCurrentBidder()`
  - Reasoning is to support `NavigationControllerTest`
- Originally `currentBidder` had no setter
  - Added method `setCurrentBidder(User newBidder)`
  - Reasoning is that this supports `ActionControllerTest` and `NavigationController`


### Model Independence
Model truly does stand alone. The tests for all the model methods (`BiddingManagerTest`, `ItemTest`, `QueueTest`, `UserTest`) can run without the view or controller and none of the tests actively create a view or controller within them.

### Updates to Unknowns
- **CLOSED**: How do we implement player ownership of items and bids so that we can properly track them in our queues?
  - After the specification revision and MVP development, we discovered that only `BiddingMangers`' method `addBid` requires information about a bid's user ownership (this is to properly add the user to field `userQueue`). So `addBid` requires an `origin` input of an `User` object. 
---
- **CLOSED**: How do we make sure we always retrieve the earliest instance of a maximum bid value from the bid queue?
  - `BiddingManager` has fields `userQueue` and `highestBid`. `highestBid` updates with a check to see if its less than an inputted value. However, to ensure the earliest instance, `userQueue` enqueues the `User` who made the bid. The queue is reset every time `highestBid` is incremented; this ensures no 'garbage' values build up. 
---
- **CLOSED**: How long should the game last and when should we enforce a hard limit on gameplay such that meaningful progress is still possible in terms of game state?
  - Specification revised scope from a game to a simulation. This unknown became resolved during that shift. Additionally, the simulation inititally generates 3-6 items, so the simulation will always end.

### What Doesn't Work Yet

## Next Steps 
### What's Left to Finish
### Non-trivial feature
### Non-trivial Feature Impact
### Stretch Goal Status
### New Tests
### Ownership

## Attribution Table 
| Class | Contributor(s) | Contribution |
| --- | --- | --- |
| ActionController | Alex | Wrote, revised, completed |
| ActionControllerTest | Alex | Wrote, revised, completed |
| BiddingManager | Krish & Alex | Wrote, revised, completed |
| BiddingManagerTest | Krish | Wrote, revised, completed |
| ConsoleView | Ben | --- |
| ConsoleViewTest | Ben | --- |
| Item | Krish | Wrote, revised, completed |
| ItemTest | Krish | Wrote, revised, completed |
| NavigationController | Alex | Wrote, revised, completed |
| NavigationControllerTest | Alex | Wrote, revised, completed |
| Observer | Alex | Wrote, revised, completed |
| Queue | Krish | Wrote, revised, completed |
| QueueTest | Krish | Wrote, revised, completed |
| User | Krish | Wrote, revised, completed |
| UserTest | Krish | Wrote, revised, completed |

**GenAI Note**: Alex has not used any GenAI. 

