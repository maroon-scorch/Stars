package edu.brown.cs.student.csvParse;

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
 * Parser responsisble for reading the CSV File and ensuring the file is properly
 * formatted according to standards set.
 */
public class CSVParser {

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

    // Clears the Template first and sets up the Path
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

    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: File %s does not exist.%n", filepath);
      return false;
    } catch (IOException e) {
      System.out.println("ERROR: File Name/Path/Content is not valid");
      template.clear();
      return false;
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
      template.clear();
      return false;
    }

    return true;
  }
}
