package edu.brown.cs.student.csvParse;

/**
 * Interface for anonymnous methods that takes in a String and returns a generic type T.
 *
 * @param <T> generic Type for the return type
 */
public interface LineConverter<T> {
  T accept(String line);
}
