package edu.brown.cs.student.stars;
import java.util.ArrayList;
public interface Command {
    public void execute(ArrayList<String> args);
    public boolean areArgsValid(ArrayList<String> args);
}
