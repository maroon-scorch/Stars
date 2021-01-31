# README

## Stars
**TODO: Fill out this section!**
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
All subsequent runs of the program would execute normally as expected.

## Design details:
The packages in the source code are subdivided into 5 packages:
- Command
- Validations
- CSVParse
- Stars
- People

### Command -----
#### Command.java
Rrather than connecting the logic of the comands directly to the REPLRunner, all commands extend from an Interface called "Command" with only two default methods:
- execute: the actual execution of the command
- matchArgsToMethod: which uses the arguments passed to determine which method (if any) should be used to execute the command. If no methods are found, the arguments are invalid, and their corresponding errors are printed.
A command object can execute multiple different types of command (ex. A NaiveRadius Command could handle 2 arguments and 4 arguments without needing to create Two Separate Command Objects for each case), and how they decide which method to execute is handled under Validations.

#### REPLRunner.java
For the runner, a Hashmap is set up to keys to whatever command object the input matches too (ex. "naive_radius" to the Naive Radius Command).
For each line passed to the BufferedReader, the string is split by space (excluding that inside of doulbe quotes) and the first string is passed to the Hashmap to see if a key for it exists. If there is such a key for the first string, then the rest of the strings are passed into its corresponding Command Object.

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
#### StarUtilities.java:
An interface that stores certain Stars specific methods shared by NaiveRadius and NaiveNeighbors
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

### Person -----
#### MockPerson.java:
The Object representation of a MockPerson, stores their first name, last name, email, gender, street address, and birth date. Validations of String Arguments passed to MockPerson.java are also handled by the ArgumentValidator inside the Object rather than the Command because other commands might also use the MockPerson object later.

#### MockCSV.java:
This is the "mock csv" command, similar to UpdateStarFile.java

* ArrayLists are chosen over Linked Lists for stars because the specific tie breakers of NaiveNeighbors required indexing of the ArrayList, this is best done with ArrayList because accessing each component has a time complexity of Constant Time whereas a linked list would require traveling from either the beginning or the end of the list to that point. In addition, ArrayLists were also better suited, personally, for Functional Methods such as .forEach and .removeIf, they also offer general compatibility towards Arrays (which ArrayLists are just extendible Arrays) which is another data structure commonly used in the implementation.

## Answers to design questions:
Suppose that in addition to the commands specified in Command Line/REPL Specification, you wanted to support 10+ more commands. How would you change your code - particularly your REPL parsing - to do this? Don't worry about specific algorithmic details; we're interested in the higher-level design.

Because of the implementation of the HashMap in my REPL Runner. All I really have to do is to add 10+ more entries of the command into the HashMap, with the key being what string should invoke them. Then I would create a Command Object for each of the Commands and handle the functionalities internally to them. For the possible arguments of each command, they would be handled by the Argument Validator via an internal Hashmap defined by the Command Object. If one Command has ties to another, they just have to share the same mutable constant defined separately in the REPL Runner. Overall, it doesn't change any main code in the run method of REPLRunner.

## How to build/run your program:
To build the program and start, run the following command in the root directory of the Project:
```
mvn package
chmod +x run
chmod +x cs32-test
./run
```

## How to run any tests:
All following commands should be run in the root directory of the Project:
To run system tests for stars (student):
```
./cs32-test tests/mji13/stars/stars1/*.test
```
To run system tests for stars (TA):
```
./cs32-test tests/ta/stars/stars1/*.test
```

To run system tests for data-modeling:
```
./cs32-test tests/mji13/person/*.test
```

To run Unit Tests:
```
mvn test
```
