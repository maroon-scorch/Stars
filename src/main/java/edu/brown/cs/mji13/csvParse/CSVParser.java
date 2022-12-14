package edu.brown.cs.mji13.csvParse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Parser responsible for reading the CSV File and ensuring the file is properly
 * formatted according to standards set.
 */
public class CSVParser {

  /**
   * The errorMessages that may occur during each run of the Parser.
   */
  private final ArrayList<String> errorMessages = new ArrayList<>();

  /**
   * Creates an Empty CSVParser object.
   */
  public CSVParser() {
  }

  /**
   * Parses through the file given and converts each line
   * according to what lineConverter specfied.
   *
   * @param <T>             the generic type T for what the ArrayList
   *                        stores and the converter returns
   * @param filepath        path to the file to be read
   * @param template        the ArrayList to store each converted line
   * @param expectedHeaders the expected headers of the CSV File
   * @param lineConverter   the Anonmynous Method where line would be passed in and converted
   * @return True if the parsing was successful, False otherwise
   */
  public <T> boolean parse(
      String filepath,
      ArrayList<T> template,
      String[] expectedHeaders,
      LineConverter<T> lineConverter) {

    // Clears the Template and error messages first and sets up the Path
    errorMessages.clear();
    template.clear();
    Path path = Paths.get(filepath);

    try (BufferedReader csvReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String header = csvReader.readLine();
      // Checks if File is Empty
      if (header == null) {
        throw new NullPointerException("The file is empty.");
      }

      String[] headers = header.split(",", -1);
      // Fails if the Headers do not Match
      if (!Arrays.equals(headers, expectedHeaders)) {
        throw new IllegalArgumentException("The "
            + "headers of the File are not the expected headers.");
      }

      // For each line, adds the converted line to the ArrayList
      String line;
      while ((line = csvReader.readLine()) != null) {
        template.add(lineConverter.accept(line));
      }
      // Add the Error to the errorMessages if any occurs.
    } catch (FileNotFoundException e) {
      errorMessages.add(String.format("ERROR: File %s does not exist", filepath));
      return false;
    } catch (IOException e) {
      errorMessages.add("ERROR: File Name/Path/Content is not valid");
      template.clear();
      return false;
    } catch (Exception e) {
      errorMessages.add("ERROR: " + e.getMessage());
      template.clear();
      return false;
    }

    return true;
  }

  /**
   * Returns the errorMessage generated after the current run of the Parser.
   *
   * @return the errorMessage.
   */
  public ArrayList<String> getMessages() {
    return new ArrayList<>(errorMessages);
  }
}
