package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


import static java.util.Map.entry;

interface StringValidator {
  void validate(String input);
}

public class ArgsValidator {

  private final String commandName;
  private final Map<Integer, ArgsInformation[]> reqInfoMaps;

  public ArgsValidator(String commandName, Map<Integer, ArgsInformation[]> regexMaps) {
    this.commandName = commandName;
    this.reqInfoMaps = regexMaps;
  }

  public Optional<String> testArgs(ArrayList<String> args) {
    int argSize = args.size();
    if (reqInfoMaps.containsKey(argSize)) {
      ArgsInformation[] reqInfos = reqInfoMaps.get(argSize);
      ArrayList<String> errorList = new ArrayList<>();
      for (ArgsInformation reqInfo : reqInfos) {
        String error = testArgsWithRegex(args, reqInfo);
        if (error.isEmpty()) {
          return Optional.of(reqInfo.getUniqueName());
        }
        errorList.add(error);
      }

      System.out.print(String.join("", errorList));

    } else {
      System.out.println("ERROR: Incorrect number of arguments for command " + commandName);
    }

    return Optional.empty();
  }

  public String testArgsWithRegex(ArrayList<String> args, ArgsInformation reqInfo) {
    int argSize = args.size();
    String[] argsFormat = reqInfo.getArgsFormat();
    String[] regexRequirements = reqInfo.getRegexRequirements();
    String[] errorMessages = reqInfo.getErrorMessages();

    String errorMsgToRaise = "";

    for (int i = 0; i < argSize; i++) {
      String currentArg = args.get(i);
      String currentReq = regexRequirements[i];

      if (!currentArg.matches(currentReq)) {
        errorMsgToRaise += errorMessages[i] + "\n";
      }
    }

    if (!errorMsgToRaise.isEmpty()) {
      errorMsgToRaise = "ERROR:----------------------------------------------\n"
          + "ERROR: If you are trying to enter the command: " + commandName
          + " <" + String.join("> <", argsFormat)
          + ">,\n" + "ERROR: then the following errors occurred:\n"
          + errorMsgToRaise
          + "ERROR:----------------------------------------------\n";
    }

    return errorMsgToRaise;
  }

}
