package edu.brown.cs.mji13.csvParse;

/**
 * Interface for anonymnous methods that takes in a String and returns a generic type T.
 *
 * @param <T> generic Type for the return type
 */
public interface LineConverter<T> {
  /**
   * Method that accepts the line of the file and converts it to generic type T.
   *
   * @param line - the line of the file passed in
   * @return what the line would be converted to
   */
  T accept(String line);
}
