package edu.brown.cs.student.validations;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * ArgsValidator Object that verifies if a list of Strings are the valid arguments
 * based on the Map of Argument Informations it is constructed with.
 */
public class ArgsValidator {

  /**
   * the name of the datatype/command using the ArgsValidator.
   */
  private final String typeName;

  /**
   * The map of integers (the size of the arguments) to its correspond list of
   * ArgsInformation. The reason why each Integer doesn't have just 1 ArgsInformation
   * is because the same command can have the same number of parameters but do
   * two different actions based on what the parameters are.
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps;

  /**
   * Constructs the ArgsValidator.
   *
   * @param typeName    the typeName of the ArgsValidator
   * @param reqInfoMaps the map of Integers to ArgsValidator of the ArgsValidator
   */
  public ArgsValidator(String typeName, Map<Integer, ArgsInformation[]> reqInfoMaps) {
    this.typeName = typeName;
    this.reqInfoMaps = reqInfoMaps;
  }

  /**
   * Tests the list of arguments passed in against the list of ArgsInformation
   * given by the size of the arguments.
   *
   * @param args the list of arguments
   * @return the unique name of the ArgsInformation if the list manages to pass
   * one of the ArgsInformation, otherwise an option of empty is given back.
   * If no key for the args' size is found, raise an error on incorrect number of types.
   */
  public Optional<String> testArgs(ArrayList<String> args) {
    int argSize = args.size();

    if (reqInfoMaps.containsKey(argSize)) {
      ArgsInformation[] reqInfos = reqInfoMaps.get(argSize);
      ArrayList<String> errorList = new ArrayList<>();
      for (ArgsInformation reqInfo : reqInfos) {
        String error = testArgsWithRegex(args, reqInfo);
        // If the test passes, meaning the error was empty
        if (error.isEmpty()) {
          return Optional.of(reqInfo.getUniqueName());
        }
        errorList.add(error);
      }

      System.out.print(String.join("", errorList));
    } else {
      System.out.println("ERROR: Incorrect number of arguments for command " + typeName);
    }
    // If all tests failed, option of empty is returned
    return Optional.empty();
  }


  /**
   * Tests the list of arguments passed in against the ArgsInformation given.
   *
   * @param args    the list of arguments
   * @param reqInfo the current ArgsInformation to be tested against
   * @return the String of all the errors that occurred when failing the tests of
   * the ArgsInformation. If all tests were passed, an empty String would be returned.
   */
  public String testArgsWithRegex(ArrayList<String> args, ArgsInformation reqInfo) {
    int argSize = args.size();
    String[] argsFormat = reqInfo.getArgsFormat();
    StringValidation[] requirements = reqInfo.getRequirements();
    String[] errorMessages = reqInfo.getErrorMessages();

    String errorMsgToRaise = "";

    // For each argument and the StringValidation test is corresponds
    // test the argument against the StringValidation
    for (int i = 0; i < argSize; i++) {
      String currentArg = args.get(i);
      StringValidation currentReq = requirements[i];
      if (!currentReq.validate(currentArg)) {
        errorMsgToRaise += "ERROR: The value " + currentArg
            + " is malformed\n" + errorMessages[i] + "\n";
      }
    }

    // The Error Message is not empty, add some general layers of error messages
    // around the Error Message
    if (!errorMsgToRaise.isEmpty()) {
      errorMsgToRaise = "ERROR:----------------------------------------------\n"
          + "ERROR: If you are trying to use the following format: " + typeName
          + " <" + String.join("> <", argsFormat)
          + ">,\n" + "ERROR: then the following errors occurred:\n"
          + errorMsgToRaise
          + "ERROR:----------------------------------------------\n";
    }

    return errorMsgToRaise;
  }

}
