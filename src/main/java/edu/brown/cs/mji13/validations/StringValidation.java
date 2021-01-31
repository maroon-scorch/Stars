package edu.brown.cs.mji13.validations;

/**
 * Interface for anonymnous methods that takes in a String and returns a Boolean.
 */
public interface StringValidation {
  /**
   * Takes in a String and determines if it's valid.
   *
   * @param str - the input string
   * @return True if String meets specification, False otherwise
   */
  Boolean validate(String str);
}
