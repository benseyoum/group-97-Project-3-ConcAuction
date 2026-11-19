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

### How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?

### Did the change cross the model/view line? Did the view have to learn anything new about the model? If a change rippled through layers it shouldn't have, where and why?

### What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.

### What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

### What design change would make the next feature cheaper?

## Next Steps 
### What is left for the final build? Remaining features and fixes, in order.

### Which stretch goals are you committing to, and which are you dropping?

### What will your 3-minute demo show? The path you'll walk through, including the new feature.

### Specific timining plan

### Who does what? Which team member owns each remaining item.


## Attribution Table 
| Class | Contributor(s) | Contribution |
| --- | --- | --- |
| GUI | Ben | |
| Auction History | Krish | |
| Randomized Items and Values | Krish | |
| Bidding Time Limit | Alex | |
| Postpone Bids | Alex | |

**GenAI Note**: 
- Alex: has not used any GenAI. 
- Krish: 
- Ben: 
