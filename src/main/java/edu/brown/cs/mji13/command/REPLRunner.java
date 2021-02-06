package edu.brown.cs.mji13.command;

import edu.brown.cs.mji13.people.MockCSV;
import edu.brown.cs.mji13.stars.NaiveNeighbors;
import edu.brown.cs.mji13.stars.NaiveRadius;
import edu.brown.cs.mji13.stars.Neighbors;
import edu.brown.cs.mji13.stars.Radius;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.UpdateStarFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.entry;

/**
 * REPLRunner that initiates the REPL Loop until being terminated.
 */
public class REPLRunner {
  /**
   * The common datatypes shared by all the stars-related commands.
   */
  private final StarStorage starStorage = new StarStorage("storage");

  /**
   * The Hashmap of String Keys to which Command Object they correspond to.
   */
  private final Map<String, Command> commandMap = Map.ofEntries(
      entry("stars", new UpdateStarFile(starStorage)),
      entry("naive_radius", new NaiveRadius(starStorage)),
      entry("naive_neighbors", new NaiveNeighbors(starStorage)),
      entry("radius", new Radius(starStorage)),
      entry("neighbors", new Neighbors(starStorage)),
      entry("mock", new MockCSV())
  );

  /**
   * Constructs an empty REPLRunner.
   */
  public REPLRunner() {
  }

  /**
   * Runs the REPLRunner that takes in each line and executes them.
   */
  public void run() {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      String input;
      while ((input = reader.readLine()) != null) {
        try {
          ArrayList<String> separatedCommand = new ArrayList<>();

          // Referencef from IRon's Regex Pattern under post:
          // https://stackoverflow.com/questions/366202/
          // regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
          // Separates the Command into different Strings without splitting between ""
          Pattern pattern = Pattern.compile("(\"[^\"]*\"|[\\S]+)+");
          Matcher matcher = pattern.matcher(input);

          while (matcher.find()) {
            separatedCommand.add(matcher.group());
          }

          separatedCommand.removeIf(String::isEmpty);
          String commandTitle = separatedCommand.remove(0);

          if (commandMap.containsKey(commandTitle)) {
            Command currentCommand = commandMap.get(commandTitle);
            currentCommand.execute(separatedCommand);
          } else {
            System.out.printf("ERROR: The Command \"%s\" does not exist%n", commandTitle);
          }
        } catch (RuntimeException e) {
          System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
          System.out.println("ERROR: Invalid input for entry");
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Exception has occurred, please re-run the program");
    }
  }
}
