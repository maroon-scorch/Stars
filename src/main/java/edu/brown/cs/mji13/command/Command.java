package edu.brown.cs.mji13.command;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface for Commands that would be executed by the REPL.
 */
public interface Command {
  /**
   * Executes the command based on the arguments.
   *
   * @param args the ArrayList of arguments passed into the command
   */
  void execute(ArrayList<String> args);

  /**
   * Finds which Method inside the Command Object to execute the Command.
   *
   * @param args the ArrayList of arguments passed into the command
   * @return Optional of Empty is Arguments are Invalid, some String if method is found
   */
  Optional<String> matchArgsToMethod(ArrayList<String> args);

  boolean hasErrorOccurred();

  ArrayList<String> getMessages();

  void clearMessage();
}
