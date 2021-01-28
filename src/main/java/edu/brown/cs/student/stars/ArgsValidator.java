package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.entry;

interface StringValidator {
  void validate(String input);
}

public class ArgsValidator {

  private final Map<String, StringValidator> validateMap = Map.ofEntries(
      entry("non-negative", this::testArgNonNegative),
      entry("number", this::testArgNumeric),
      entry("integer", this::testArgInteger),
      entry("name", this::testArgString),
      entry("any", (input -> { }))
  );

  private String commandName;
  private Integer[] possibleArgNumbers;
  // private Map<Integer, String[][]> requirementMaps;

  public ArgsValidator(String commandName) {
    this.commandName = commandName;
  }

  public void testArgs(ArrayList<String> args, String[][] requirements) {
    int argSize = args.size();
    if (argSize != requirements.length) {
      // Shouldn't Reach Here
      throw new RuntimeException("The number of arguments do not match "
          + "the number indicated by requirements");
    }

    for (int i = 0; i < argSize; i++) {
      String currentArg = args.get(i);
      String[] currentReq = requirements[i];
      for (String eachReq : currentReq) {
        satisfyReq(currentArg, eachReq);
      }
    }
  }

  public void satisfyReq(String arg, String req) {
    if (validateMap.containsKey(req)) {
      validateMap.get(req).validate(arg);
    } else {
      throw new RuntimeException(String.format("The following "
          + "requirement %s is not defined in the validator yet", req));
    }
  }

  public void testArgNonNegative(String input) {
    testArgNumeric(input);
    double numIn = Double.parseDouble(input);
    if (numIn < 0) {
      throw new RuntimeException(
          String.format("The argument %s must be non-negative.", input));
    }
  }

  public void testArgNumeric(String input) {
    try {
      Double.parseDouble(input);
    } catch (NumberFormatException e) {
      throw new RuntimeException(
          String.format("The argument %s is not numeric.", input));
    }
  }

  public void testArgInteger(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new RuntimeException(
          String.format("The argument %s is not an integer.", input));
    }
  }

  public void testArgString(String input) {
    char[] charInputs = input.toCharArray();
    if ((charInputs[0] != '"') || (charInputs[input.length() - 1] != '"')) {
      throw new RuntimeException(
          String.format("The argument %s is not a proper name, "
              + "it needs to be put into double quotes.", input));
    }
  }


//    public ArgsValidator(
//            String commandName,
//            Map<Integer, String[][]> requirementMaps) {
//        this.requirementMaps = requirementMaps;
//    }
//
//    public boolean areArgsValid(ArrayList<String> args) {
//        String errorMessages = "";
//        int argSize = args.size();
//
//        if (requirementMaps.containsKey(argSize)) {
//            String[][] keyWordLists = requirementMaps.get(argSize);
//            errorMessages += testArgs(keyWordLists, args);
//        } else {
//            errorMessages += "ERROR: Incorrect Number of Arguments\n";
//        }
//
//        System.out.println(errorMessages);
//        return errorMessages.isEmpty();
//    }
//
//    public String testArgs(String[][] keyWordLists, ArrayList<String> args) {
//        for (String[] keyWordList : keyWordLists) {
//
//        }
//        // System.out.println("ERROR: ");
//    }
//
}
