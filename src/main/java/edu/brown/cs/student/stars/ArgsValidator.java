package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

interface StringValidator {
    Boolean validate(String input);
}

public class ArgsValidator {

//    StringValidator isNonNegative = (str) -> (isArgNumeric(str) && (Double.parseDouble(str) >= 0));
//    private Map<String, StringValidator> validateMap = Map.ofEntries(
//            entry("non-negative", isNonNegative),
//            entry("double", ),
//            entry("integer", ),
//            entry("name", )
//    );
//
//    private String commandName;
//    private Integer[] possibleArgNumbers;
//    private Map<Integer, String[][]> requirementMaps;
//
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
//            errorMessages += "";// evaluateArgs(args, requirementMaps);
//        } else {
//            errorMessages += "ERROR: Incorrect Number of Arguments\n";
//        }
//
//        System.out.println(errorMessages);
//        return errorMessages.isEmpty();
//    }
//
//    public boolean isArgNonNegative(String input) {
//        if (isArgNumeric(input)) {
//
//        }
//    }
//
//    public boolean isArgNumeric(String input) {
//        try {
//            Double.parseDouble(input);
//            return true;
//        } catch(NumberFormatException e){
//            return false;
//        }
//    }
//
//    public boolean isArgInteger(String input) {
//        try {
//            Integer.parseInt(input);
//            return true;
//        } catch(NumberFormatException e){
//            return false;
//        }
//    }
//
//    public boolean isArgString(String input) {
//        char[] charInputs = input.toCharArray();
//        return ((charInputs[0] == '"') && (charInputs[input.length() - 1] == '"'));
//    }
}
