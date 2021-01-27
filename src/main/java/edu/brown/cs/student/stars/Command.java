package edu.brown.cs.student.stars;

import java.util.ArrayList;

public interface Command {
  void execute(ArrayList<String> args);

  boolean areArgsValid(ArrayList<String> args);
}
