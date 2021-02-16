# README

## Stars
## Known bugs:
When running the program for the first time, a warning for **illegal reflective access** similar to the following would occur
```
+ WARNING: An illegal reflective access operation has occurred
+ WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) 
	to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
+ WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
+ WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
+ WARNING: All illegal access operations will be denied in a future release
```
This is a known [bug](https://github.com/google/guice/issues/1133) for Maven to occur occasionally due to system dependencies.
The error would appear twice in **mvn package** and when you run the program or conduct system tests.
This bug may cause intended tests to timeout, ./run to not execute properly, however, repeated executions of the system tests should resolve the issue.

## Design details:
The packages in the source code are subdivided into 7 packages:
- Command
- Validations
- CSVParse
- Stars
- kdTree
- People
- gui

### Command -----
#### Command.java
Rrather than connecting the logic of the comands directly to the REPLRunner, all commands extend from an Interface called "Command" with only two default methods:
- execute: the actual execution of the command
- matchArgsToMethod: which uses the arguments passed to determine which method (if any) should be used to execute the command. If no methods are found, the arguments are invalid, and their corresponding errors are printed.
- executeForGUI: the execution of the command if called by the GUI instead, by default this does nothing,
A command object can execute multiple different types of command (ex. A NaiveRadius Command could handle 2 arguments and 4 arguments without needing to create Two Separate Command Objects for each case), and how they decide which method to execute is handled under Validations.

#### REPLRunner.java
For the runner, a Hashmap is set up to keys to whatever command object the input matches too (ex. "naive_radius" to the Naive Radius Command).
For each line passed to the BufferedReader, the string is split by space (excluding that inside of doulbe quotes) and the first string is passed to the Hashmap to see if a key for it exists. If there is such a key for the first string, then the rest of the strings are passed into its corresponding Command Object.

Execute gives back a list of strings to be outputed sequentially line by line by the REPL.


### Validations -----
For each command object, whenever the rest of the strings is passed into them, these arguments will be checked by the validation packages for whether or not they are valid and, if they are valid, which method in the command should the command execute. The central idea of Validations is to use Anonmynous (Lambda) Methods to check if each String in the argument is valid. The main process of validation is **ArgsValidator.java**, but there are several building blocks contributing to it.

#### StringValidation.java:
An interface defining generic anonmynous methods that converts a single String parameter to Boolean
#### StringValFunctions.java:
An Interface storing generic String to Boolean Methods as Utilities for other classes to use.
#### ArgsInformation.java:
An Object Class defined an "ArgsInformation", which is a tuple storing 4 items - the **unqiueName** of the ArgsInformation, a list of human readable **argsFormat** to specify what the requirement for each argument is, a list of **requirements** which are StringValidation methods, and a list of corresponding **errorMessages** to print out.

The idea is that the ArgsInformation would be passed into a Validator against a list of Strings. For each String, they are tested against the StringValidation Method at the same index, if the method returns false, the corresponding errorMessage at the same index would be printed/stored. If all tests are true, then no errors have occurred, then the uniqueName of the ArgsInformation is passed back as a key for the Command Object to find which method to use.
#### ArgsValidator.java:
The ArgsValidator takes the previous idea but runs the list of Strings against all possible ArgsInformations specified by the Command Object.
Specifically, the ArgsValidator takes in the name of the Command Object (String) and a Hashmap.
The Hashmap maps the **size** of the arguments to a list of possible ArgsInformations for arguments of that **size** (ex. NaiveRadius has a Hashmap with keys 2 and 4, for the size of arguments, and each size corresponds to a list of one ArgsInformation)

The reason why Hashmaps don't just map to ArgsInformation but instead to a list of ArgsInformation is because the same command in the future may have the same number of arguments but for two different functionalities, so a list of ArgsInformation is considered instead.

Each Command Object would build their own ArgsValidator Object internally, and for whatever list of strings for arguments they receive, they will pass it to the ArgsValidator they built to verify if its valid. If the uniqueName is passed back, then a switch case of the uniqueNames would tell the Command Object what to do. If nothing is passed back, then the Command exits the execution.

### CSVParse -----
The CSV Parser is called by Commands that needs to read a CSV file. The idea of the CSV Parser is that it would take in the filepath specified, a list of expected Headers for the file, an initial list that would be empty, and a custom Anonmynous (Lambda) Method that converts a given line to a generic type T.
If no errors occur, then the Parser would first check if the headers match each other, then for every line, they would convert the line to an Object of generic type T, and add that to the empty list.

There are 2 files under CSVParse:
#### LineConverter.java:
This file defines an anonmynous method that converts a String to a generic type T.
#### CSVParser.java:
This is the parser of the CSV Files, it functions as explained above.

### Stars -----
#### Main.java:
The main method of the project, calls the REPLRunner and runs the REPL.
#### Star.java:
The Object representation of a Star, stores its id, name, x, y, and z coordinate. The Star also has a distanceTo method that returns the distance of the star to a coordinate.
Validations of String Arguments passed to Star.java are also handled by the ArgumentValidator.

Each of the three Command Objects share an ArrayList of Stars together and a StringBuilder of what the currentFile is. Whenever the "stars" command make any changes to the
list of stars, it would change the other two object as well.
#### UpdateStarFile.java:
This is the "stars filepath" command. It constructs a CSVParser that reads the file and converts them to a list of Stars Object/
#### NaiveRadius.java:
NaiveRadius takes in all stars whose distance to the location specified is less than the radius. The arguments are first passed through the validator, then each correspondent method would handle the logic internally. Specifically, performing NaiveRadius requires a filtering of the list to remove all stars with a distance greater than the radius, and then sorting of the list to return.
#### NaiveNeighbors.java:
NaiveNeighbors takes in the n closest stars to it given a nonnegative integer n. The arguments are first passed through the validator, then each correspondent method would handle the logic internally. The object would first sort the list and find the first n stars. Specific implementations of tiebreakers are explained in the code. 
#### Radius.java:
The mature implementation of naive_radius using KD Trees, more details in the code.
#### Neighbors.java:
The mature implementation of naive_neighbors using KD Trees, more details in the code.
#### StarsGenerator.java:
A utilities class that generates a list of random stars for Model Based Testing, more explained below.

### KDTree -----
The KDTree stores an object of generic type T that extends that interface hasCoordinate:
#### hasCoordinate.java:
An interface for any objects that rely on coordinates to implement, it requires the object that implement the interface to declare how to access their coordinates and the metric of distance between coordinates.
#### KDNode.java:
The building block of KDTree, the Node is the same as a node for a Binary Tree. The leaf node is just left with null indiciating that whatever below is empty because this suited the best during recursive detections.
#### KDTree.java:
An implementation of generic KDTree, offers both radius search and neighbors search in its class. more explained in the stencil code.

### Person -----
#### MockPerson.java:
The Object representation of a MockPerson, stores their first name, last name, email, gender, street address, and birth date. Validations of String Arguments passed to MockPerson.java are also handled by the ArgumentValidator inside the Object rather than the Command because other commands might also use the MockPerson object later.

#### MockCSV.java:
This is the "mock csv" command, similar to UpdateStarFile.java

### GUI ------
#### CommandHandler.java:
The Handler of GUI GET Request for the Command Datatype. This is responsible for handling all front end forms request to the backend. More to GUI will be explained below.

* ArrayLists are chosen over Linked Lists for stars because the specific tie breakers of NaiveNeighbors required indexing of the ArrayList, this is best done with ArrayList because accessing each component has a time complexity of Constant Time whereas a linked list would require traveling from either the beginning or the end of the list to that point. In addition, ArrayLists were also better suited, personally, for Functional Methods such as .forEach and .removeIf, they also offer general compatibility towards Arrays (which ArrayLists are just extendible Arrays) which is another data structure commonly used in the implementation.

### Front End and Back End Interaction:
For Front End and Back End interaction, ftl templates are used so that they call the specific addresses whenever a GET request is submitted in the front end forms.
When the request is passed to Main.java under spark servers, the specific Handler created in CommandHandler is used to handle the specific request.

The REPL and the CommandHandler share the same identicial command objects because the Hashmap of Commands are in Main, therefore, loading in files in the back end is synchronized with the front end.

## Front End Design Details:
Front End Design can be split into the 4 Following Categories. They can found under src\main\resources:
1. FTL Templates
2. CSS
3. Javascript
4. Custom Assets

### FTL Templates:
main.ftl is the main page of the website, query.ftl is chaned to this level.
In query.ftl there are 3 sections - one for which form is displayed, one for the background of the GUI, and one of the selector between Light and Dark Mode.
In modal.ftl, the output of the command for the GUI is displayed here as a Modal.

### CSS and Javascript:
main.css and js -
#### Light and Dark Mode Transition:
All css files (except for the ones from lab) extend from main.css and use a set of variables defined in main.css. Light and Dark Mode is enabled by changing the class name of the body. Under body when class name is "light", they have one set of variable values. Under body when class name is "dark", they have another set of variable values.

form.css and js -
#### Switching Between Name and Coordinate:
By default there're 2*4 = 8 forms in the ftl, however, they are default as display none. When the user selects which form they want, their display is set to block./

modal.css and js -
By default modal is also hidden in none, when the user submit, it is invoked to appear.

#### Custom Assets:
The images used for this project.
Disclaimer: All Pixel Arts here are custom assets made by me for the Stars Project, you can find them under
https://www.pixilart.com/maroon-scorch/gallery on Pixilart with the same username as my github username,
the account on Pixilart is also anonymous.

More details are explained in the code. 
## Answers to design questions:
### Stars2 Questions:
1. What are some problems you would run into if you wanted to use your k-d tree to find points that are close to each other on the earth's surface? You do not need to explain how to solve these problems.

There are three main problems that the k-d tree would run into for considering points on the Earth's surface:
a. The existence of a cycle in a spherical/angular representation of coordinates
b. Determining the right metric to use for "distance" between two points
c. How the space partitioning would adjust accordingly to b

a: One of the fundamental assumptions that a KD Tree takes that helps reduce its runtime when compared to a list is the notion of direction,
ex. If the axis distance of the selected coordinate to the origin point is greater than the desired radius/distance and the selected point
is between the left child's coordinate and the selected coordinate, then we can automatically eliminate the right subtree because we assume
that any points on the right child's side will be on that side with a distance greater than the desired distance we are searching for, and vice versa.

However, the above condition assumes that the point on the right subtree's side will never be on the left subtree's side, and this is
indeed true for the Cartesian Coordinates. However, when dealing with spheres such as the question here, because now there is a notion of a cycle,
the points on the right subtree, if extended long enough, can actually wrap back to the space for the left subtree because it is traversing on a sphere.
Thus, the KD Tree Model here cannot safely eliminate a subtree with our current assumptions when dealing with coordinates such as Latitudes and Longitudes.
It would instead be more advisable to convert all points to the Cartesian Coordinate with respect to the center of Earth as origin.

b: However, even if all the points are in Cartesian Coordinate, we still run into the problem of defining what the measure of the distance between two
points on Earth's surface should be. The KD Tree Model so far uses the Euclidean distance between two points; however, the Euclidean Distance of
two points on a sphere does not convey a notion of "nearness" as much as they would just cut through the sphere entirely (ex. If I have two points on
the opposite side of the globe, their euclidean distance is just a straight line axis through the two points and the center, which is not very helpful
when I am trying to consider geographical distances).

c: It would be more advisable instead to choose distances on a surface, but then one might run into
the problem of how to divide the KD Tree based on the specific metric of distance they have chosen
because the original implementation relied solely on considering Euclidean distances. For each different kind of metric in distance,
the space partitioning method would wary as well.

2. Your k-d tree supports most of the methods required by the Collection interface. What would you have to add/change to make code like "Collection<Star> db = new KDTree<Star>()" compile and work properly?

In order for an object to become part of the Collections' interface, it must have to ability to insert and delete elements from the collection, have the ability to know if a given object is in its collection, the ability to signal information about itself such as size, emptiness, etc, the ability to be converted to Arrays, and finally equals and hascodes. First and foremost, addition is relatively easy. Whenever adding an element in, my KD Tree could just compare with the element's corresponding coordinate at each level and determine where to send the element down until eventually it reaches a leaf node and settles. AddAll would then be iterating through a iterable of these objects and adding them one at a time. 

Deletion would be quite tricky, on the other hand because the KD Tree has to maintain its directions and space partitioning while being able to remove said element. A simple, albeit costly way, to implement this is to store the original ArrayList passed into my KD Tree, so whenever a request is asked to delete a Node, the corresponding point on the ArrayList is removed, then the KD Tree would just build a new Tree based on the edited ArrayList. For RemoveAll, we also work with the ArrayList first, after all desired elements has been removed from the ArrayList, then we reconstruct the list.

Then, for the ability to know if an Object is in its collection, the KD Tree would need a search through all nodes of the tree and to compare each Node with the target. ContainsAll would work the same in principle by iteratively searching through the tree again.

For the ability to signal information about itself such as size and emptiness. These are all relatively straight forward, and one only needs to check the private variables already stored to return them.

For turning the Collection into an Array, albeit turning a tree into an Array is quite unintuitive, we could follow either an inorder, postorder, or preorder traversal of all the nodes to concatenate them into an Array.

Finally, for Equals and Hashcodes,for Equality a KD Tree is the same to another if and only if they contain the same nodes in the same exact order, and the Hashcode can be made from the combination of the individual nodes' hashcodes accordingly.

### Stars1 Questions:
1. Suppose that in addition to the commands specified in Command Line/REPL Specification, you wanted to support 10+ more commands. How would you change your code - particularly your REPL parsing - to do this? Don't worry about specific algorithmic details; we're interested in the higher-level design.

Because of the implementation of the HashMap in my REPL Runner. All I really have to do is to add 10+ more entries of the command into the HashMap, with the key being what string should invoke them. Then I would create a Command Object for each of the Commands and handle the functionalities internally to them. For the possible arguments of each command, they would be handled by the Argument Validator via an internal Hashmap defined by the Command Object. If one Command has ties to another, they just have to share the same mutable constant defined separately in the REPL Runner. Overall, it doesn't change any main code in the run method of REPLRunner.

## Model Based Testing to Property Based Testing:
### Model Based Testing:
There were some challenges to Model Based Testing such as how to compare the two lists when the tiebreaking of lists ensure a nondeterminsitic list, or how to generate sufificently random lists of stars for testing. Finally, I decided to settle on using a custom object class called StarGenerator to generate the lists for me. You can find the Model Based Testing under the stars package of the JUnit Tests. In it, by initializing a starsGenderator, I ran NaiveRadius vs Radius and NaiveNeighbors vs Neighbors in two separate files.

For all instances, a random list of stars is generated during setup that's shared by both commands. For the radius, in coordinate searches, a random coordinate is generated each time in a for loop, and the result of the naive_radius vs radius are compared to each other directly. For names, I have prepared a keyset of all the keys of names for stars and iterated both commands through that and compared their results to one another (which should be the same).

For neighbors, instead of comparing the lists of stars, I mapped the stars to distance because the result could be nondeterminsitic for stars but not for distances. Otherwise, they followed the same steps as above.

### Property Based Testing:
In Property Based Testing, I decided to improve upon the idea I had previously in MBT and focused on testing more properties.

I believe the following properties best summarize the main points to check for:

For Neighbors Command, I check if the output lists satisfy:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The size of both lists are the count specified
   * 3. Both lists are in ascending order of distances
   * 4. All the stars selected in the lists are all in the original list.

For Radius Command, I check if the output lists satisfy:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The distances are in Ascending Order and below the radius
   * 3. All the stars selected in the lists are all in the original list.
  
Additionally, for names, I also check that the name specified is not in the output list.

Relevant files for Property Based Testing can be found under JUNIT Testing for PropertyBasedNeighborsTest and PropertyBasedRadiusTest
and under the star package for StarsGenerator.java

## How to build/run your program:
To build the program and start, run the following command in the root directory of the Project:
```
mvn package
chmod +x run
chmod +x cs32-test
./run
```
## How to run GUI:
```
./run --gui --port 4567
```
The localholst can be found under http://localhost:4567/stars
## How to run any tests:
All following commands should be run in the root directory of the Project:<br/>
To run system tests for stars2 (student):
```
./cs32-test tests/mji13/stars/stars2/*.test
```
To run system tests for stars1 (student):
```
./cs32-test tests/mji13/stars/stars1/*.test
```
To run system tests for stars (TA):
```
./cs32-test tests/ta/stars/stars1/*.test
./cs32-test tests/ta/stars/stars2/*.test
```

To run system tests for data-modeling:
```
./cs32-test tests/mji13/person/*.test
```

To run Unit Tests:
```
mvn test
```

## Notes
data_modeling.txt can be found under the people package
<br/>accessibility_testing.txt can be found under src/main/resources/accessibility_testing.txt
### Proof of Passing Lighthouse Test:
![img.png](img.png)
