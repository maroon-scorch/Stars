package edu.brown.cs.student.stars;

public class ArgValidator {
    public ArgValidator() { }

    public boolean isArgNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isArgInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isArgString(String input) {
        char[] charInputs = input.toCharArray();
        return ((charInputs[0] == '"') && (charInputs[input.length() - 1] == '"'));
    }
}
