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
### REPL:
Rrather than connecting the logic of the comands directly to the REPLRunner,
all commands extend from an Interface called "Command" with only two default methods
- execute: the actual execution of the command
- matchArgsToMethod: which uses the arguments passed to determine which method (if any) should be used to execute the command. If no methods are found, the arguments are invalid, and their corresponding errors are printed.

For the runner, a Hashmap is set up to key the first string of the split (after being split
by space) to whatever command object the input matches too.

### Argument Validation:
For each command object in the 

## Answers to design questions:

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
