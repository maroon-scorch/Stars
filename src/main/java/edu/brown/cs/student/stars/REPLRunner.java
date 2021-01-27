package edu.brown.cs.student.stars;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.entry;

public class REPLRunner {

  private final ArrayList<Star> starsList = new ArrayList<>();
  private final StringBuilder currentFile = new StringBuilder();
  private final Map<String, Command> commandMap = Map.ofEntries(
      entry("stars", new UpdateStarFile(starsList, currentFile)),
      entry("naive_radius", new NaiveRadius(starsList, currentFile)),
      entry("naive_neighbors", new NaiveNeighbors(starsList, currentFile)),
      entry("mock", new MockCSV())
  );

  public REPLRunner() {
  }

  public void run() {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in))) {
      String input;
      while ((input = reader.readLine()) != null) {
        try {
          String regex = " (?=(([^'\"]*['\"]){2})*[^'\"]*$)";
          ArrayList<String> separatedCommand
              = new ArrayList<>(Arrays.asList(input.split(regex)));
          System.out.println(separatedCommand);
          // https://stackoverflow.com/questions/21627866/java-regex-to-split-a-string-using-spaces-but-not-considering-double-quotes-or
          //  (?<=") *(?=")
          // naive_neighbors 5 "So"l"
          //" (?=(([^'\"]*['\"]){2})*[^'\"]*$)"
          // Ask about this whether to raise an error for multiple space or handle it here
          separatedCommand.removeIf(String::isEmpty);
          String commandTitle = separatedCommand.remove(0);

          if (commandMap.containsKey(commandTitle)) {
            Command currentCommand = commandMap.get(commandTitle);
            currentCommand.execute(separatedCommand);
          } else {
            System.out.printf("ERROR: The Command \"%s\" does not exist\n", commandTitle);
          }
        } catch (Exception e) {
          System.out.println("ERROR: Invalid input for entry");
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Exception has occurred, please re-run the program");
    }
  }

}
