package edu.brown.cs.student.validations;

/**
 * Interface that stores some general StringValidations to be used by other Objects.
 */
public interface StringValFunctions {

  /**
   * Checks if the string passed in is numeric.
   *
   * @param input the input String
   * @return True if the input is numeric
   */
  default Boolean isNumeric(String input) {
    try {
      Double.parseDouble(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Checks if the string passed in is an integer.
   *
   * @param input the input String
   * @return True if the input is a String integer
   */
  default Boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Checks if the string passed in is a valid "name".
   *
   * @param input the input String
   * @return True if the input is surrounded by double quotes
   */
  default Boolean isName(String input) {
    return input.matches("\"|[\\w\\W]+\"");
  }

  /**
   * Always returns true for any input.
   *
   * @param input the input String
   * @return True
   */
  default Boolean pass(String input) {
    return true;
  }

  /**
   * Checks if the string is a non-negative number.
   *
   * @param input the input String
   * @return True if the string is numeric and nonneaative
   */
  default Boolean isNonNegative(String input) {
    return (isNumeric(input) && (Double.parseDouble(input) >= 0));
  }
}
