package edu.brown.cs.mji13.command;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface for Commands that would be executed by the REPL.
 */
public interface Command {
  /**
   * Executes the command based on the arguments. This is intended for the REPL
   *
   * @param args the ArrayList of arguments passed into the command
   * @return an ArrayList of Messages to be printed out line by line
   */
  ArrayList<String> execute(ArrayList<String> args);

  /**
   * Executes the command based on the arguments. This is intended for the GUI.
   * If a command is not made for the GUI, by default it returns an empty string.
   *
   * @param args the ArrayList of arguments passed into the command
   * @return the String value to be passed down to the GUI
   */
  default String executeForGUI(ArrayList<String> args) {
    return "";
  }

  /**
   * Finds which Method inside the Command Object to execute the Command based on
   * the arguments passed in.
   *
   * @param args the ArrayList of arguments passed into the command
   * @return Optional of Empty if Arguments are Invalid, some String if method is found
   */
  Optional<String> matchArgsToMethod(ArrayList<String> args);
}
