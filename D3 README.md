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
  - Change prompted by team discussion about how `biddingManager` would lose the winner's data on reset before trying to print thata data. 
- Originally, field `auctionQueue` had no getter.
  - Added method `getAuctionQueue()`
  - Reasoning is that this supports `ActionControllerTest`, `NavigationController`, and `NavigationControllerTest`
  - Change prompted by necessity to check if `auctionQueue` is empty when running `NavigationController`'s `checkUserBidSelect(int userId)`
- Originally, field `highestBid` had no getter.
  - Added method `getHighestBid()`
  - Reasoning is that this supports `ActionControllerTest`
   - Change prompted by challenge of writing test cases without the `getHighestBid` value. 

**`ConsoleView` Changes**
- Originally, fields `actionController` and `navigationController` could not change after construction
  - Both were changed to changeable via setters
  - Reasoning is to enable better initialization of `ConsoleView` by allowing placeholders null values to be edited into proper controller references.
  - Change prompted by the difficulty of getting `ConsoleView`, `ActionController`, and `NavigationController` objects all initialized without having at least one value be null in any of the three's constructors. 
- Originally did not include setters for fields `actionController` and `navigationController`
  - Added methods `setActionController(ActionController actionController)` and `setNavigationController(NavigationController navigationController)`
  - Reasoning is the same as the point above.
  - Change promtped by the same point above.

**`ActionController` Changes**
- Originally field `currentBidder` had no getter
  - Added method `getCurrentBidder()`
  - Reasoning is to support `NavigationControllerTest`
  - Change prompted by challenge of writing test cases without the `currentBidder` value. 

- Originally `currentBidder` had no setter
  - Added method `setCurrentBidder(User newBidder)`
  - Reasoning is that this supports `ActionControllerTest` and `NavigationController`
    - Change prompted by necessity to set the `currentBidder` in `NavigationController` 

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
Most of our MVP works as described in the specification. There only remains minor issues like occasional double printing in the view. 

## Next Steps 
### What's Left to Finish
There is not much to finish in the MVP. The only thing to finish is fixing the issue of the view occasionally printing a prompt twice. However, we have many stretch goals to focus on in the next delvierables. These are discussed in "Stretch Goal Status."

### Non-trivial feature
An option before any player bids to postpone bidding on a selected item. This will dequeue the current item and enqueue it to the back. The item's starting price will decrease. Users will continue on normally after postponing a bid, simply seeing that item at the end. 

It will be important to ensure that this option is only available BEFORE any player bids. After any one player bids, this option should become unavailable. 

### Non-trivial Feature Impact
This new feature would touch the following classes and methods
- Model
  - `Item`
    - `Item(String name, int startingBid, int boughtInValue)`: Constructor necessary to replicate the removed item and placing it at the back of the queue with a lower `startingBid`
  - `Queue`
    - `dequeue()`: Removes the current item
    - `enqueue(E element)`: Creates a new item at the back w/ a lower `startingBid`
  - `BiddingManager`
    - Would use a new method (`postponeAuction()`) that modifies field `auctionQueue` using the methods described in `Queue`
- Controller
  - `NavigationController`
    - Would use a new field (`firstBid`) to track whether users have bid already.
    - `checkUserBidSelect(int userID)`: This function already handles the quite request ('0') so it is reasonable that is should handle postpone requests, likely mapped to a different impossible integer - perhaps -1. If implemented, `postponeMenu` (described below) will have to be called by this new branch. 
- View
  - `ConsoleView`
    - `userBidSelectMenu()` would require more text explaining this mechanic.
    - Potentially include a new method (`postponeMenu()`) that informs the users of a successful postponing. 

It would NOT touch `NavigationController`, `Item`, or `User`. Any methods not directly mentioned within the specified classes should also, theoretically, not be touchded. 

### Stretch Goal Status
- **GUI**: Commited to Implementing in the Future
- **Auction History**: Commited to Implementing in the Future
- **Randomized Items and Values**: Commited to Implementing in the Future
- **Bidding Time Limit**: Commited to Implementing in the Future

### New Tests
- **GUI**
  - Requires a new class in View that opens outside the terminal
  - Undergoes the same testing and performance expectations as `ConsoleView`
  - Everything that is currently tested in `ConsoleView`, should be tested in a future `WindowView`
- **Auction History**
  - BiddingManager requires a new data structure to store transaction history; likely a stack
  - Test `empty()`, `peek()`, `pop()`, `push(E item)` and `search(Object o)` of a stack class. 
    - Test popping an empty stack
    - Test popping on a one-element stack
    - Test peeking at an empty stack
    - Test pushing a null item 
    - If array-based, test pushing when underlying array is full
    - Test searching for an item that doesn't exists
  - Likely will be implemented via `NavigationController`, a getter method in `BiddingManager`, and a new menu method(s) in `ConsoleView`
    - Test if `NavigationController` can reach to print the auction history
    - Test if `NavigationController` crashes when inputting invalid navigation to get to auction history
- **Randomized Items and Values**
  - Create a large backend dataset and have `BiddingManager` randomly assign them to itself
  - In `BiddingManagerTest`, test if randomly generated items are present in the backend dataset.
- **Bidding Time Limit**
  - Admittedly the implementation of this stretch goal has not yet been figured out
  - Likely will require a new class, something like `Timer`
  - `Timer` will likely have to include methods like `tick()`, `timeOut()`, `resetTimer(int time)`, `getTime()` and fields like `time`. The following should be tested in a new test class `TimerTest`
    - Test if `tick()` properly decrements `time` at the correct intervals
    - Test if `tick()` calls `timeOut` when it hits zero, stopping the countdown
    - Test how `resetTimer(int time)` will handle a negative or zero time integer input.
- **Postpone Bids**
  - In `BiddingManagerTest`, test if `postponeAuction()` edits the queue as expected
  - In `NavigationControllerTest`, test the new branch in `checkUserBidSelect(int userID)` for leading to the correct menu
  - In 'ConsoleView', test if `postponeMenu()` reports the necessary values.

### Ownership
| Feature | Owner(s) | 
| --- | --- | 
| GUI | Ben |
| Auction History | Krish |
| Randomized Items and Values | Krish |
| Bidding Time Limit | Alex |
| Postpone Bids | Alex |


## Attribution Table 
| Class | Contributor(s) | Contribution |
| --- | --- | --- |
| ActionController | Alex | Wrote, revised, completed |
| ActionControllerTest | Alex | Wrote, revised, completed |
| BiddingManager | Krish & Alex | Wrote, revised, completed |
| BiddingManagerTest | Krish & Alex | Wrote, revised, completed |
| ConsoleView | Ben | Wrote, revised, completed |
| ConsoleViewTest | Ben | Wrote, revised, completed |
| Item | Krish | Wrote, revised, completed |
| ItemTest | Krish | Wrote, revised, completed |
| NavigationController | Alex | Wrote, revised, completed |
| NavigationControllerTest | Alex | Wrote, revised, completed |
| Observer | Alex | Wrote, revised, completed |
| Queue | Krish | Wrote, revised, completed |
| QueueTest | Krish | Wrote, revised, completed |
| User | Krish | Wrote, revised, completed |
| UserTest | Krish | Wrote, revised, completed |

**GenAI Note**: 
Alex: has not used any GenAI. 
Krish: used AI for method scaffolding generation, making sure test classes have no unexecuted methods and making sure my work was correct.
Ben: used AI for view and test scaffolding, and troubleshooting integration issues.
