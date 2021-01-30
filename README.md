# README

## Stars
**TODO: Fill out this section!**
## Known bugs:
None
## Design details:
### REPL:
Rrather than connecting the logic of the comands directly to the REPLRunner,
I instead made all commands extend from an Interface called "Command" with only two default methods
- execute and matchArgsToMethod. "execute" is the actual execution of the command while "matchArgsToMethod" is
a validation of the arguments being passed to the command to determine if the command should be executed and
which form of the command to choose.

For the runner, a Hashmap is set up to key the first string of the split (after being split
by space) to whatever command object the input matches too.

### Argument Validation:
For each command object in the 

## How to run any tests:
To run system tests for stars:
```
./cs32-test tests/student/stars1/*.test --timeout 20
```

To run system tests for data-modeling:
```
./cs32-test tests/student/person/*.test --timeout 20
```

To run Unit Tests:
```
mvn test
```

## How to build/run your program:
To build the program and start, run the following command:
```
mvn package
./run
```

## Answers to design questions:
