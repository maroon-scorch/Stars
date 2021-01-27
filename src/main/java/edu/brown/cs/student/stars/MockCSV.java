package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Arrays;

public class MockCSV implements Command{
    private Number[] acceptArgs = {1};
    public MockCSV() {

    }

    public void execute(ArrayList<String> args) {
        if (areArgsValid(args)) {
            System.out.println("Success!");
        }
    }

    public boolean areArgsValid(ArrayList<String> args) {
        int argSize = args.size();
        if (!Arrays.asList(acceptArgs).contains(argSize)) {
            System.out.println("ERROR: Incorrect Number of Arguments for \"mock <csv>\"");
            return false;
        }

        return true;
    }
}
