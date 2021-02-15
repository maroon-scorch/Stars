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
   * The map of keywords to the specific Command Object to execute.
   *
   */
  private final Map<String, Command> commandMap;

  /**
   * Constructs an empty REPLRunner.
   *
   * @param commandMap - a Hashmap of keywords (name of Command) to Commands, serves as
   *                   the dictionary of commands available to the REPL.
   */
  public REPLRunner(Map<String, Command> commandMap) {
    this.commandMap = commandMap;
  }

  /**
   * Runs the REPLRunner that takes in each line and executes them.
   *
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
            ArrayList<String> messages = currentCommand.getMessages();
            messages.forEach(System.out::println);
            currentCommand.clearMessage();
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
