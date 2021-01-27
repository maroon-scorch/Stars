package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateStarFile implements Command {
  private final ArrayList<Star> starsList;
  private final StringBuilder currentFile;

  private final Number[] acceptArgs = {1};
  private final CSVParser parser = new CSVParser();
  private final String[] expectedHeaders = {"StarID", "ProperName", "X", "Y", "Z"};

  public UpdateStarFile(ArrayList<Star> starsList, StringBuilder currentFile) {
    this.starsList = starsList;
    this.currentFile = currentFile;
  }

  public void execute(ArrayList<String> args) {
    if (areArgsValid(args)) {
      String filepath = args.get(0);
      boolean isSuccessful = parser.parse(filepath, starsList, expectedHeaders, this::lineToStar);
      if (isSuccessful) {
        System.out.printf("Read %d stars from %s\n", starsList.size(), filepath);
        currentFile.replace(0, currentFile.length(), filepath);
      }
    }
  }

  public boolean areArgsValid(ArrayList<String> args) {
    int argSize = args.size();
    if (!Arrays.asList(acceptArgs).contains(argSize)) {
      System.out.println("ERROR: Incorrect Number of Arguments for \"star\"");
      return false;
    }
    return true;
  }

  private Star lineToStar(String line) {
    String[] starArgsArray = line.split(",");
    if (starArgsArray.length != 5) {
      throw new RuntimeException("The file's rows are malformed, "
          + "they have inconsistent number of entries per row");
    }

    return new Star(
        starArgsArray[0],
        starArgsArray[1],
        Double.parseDouble(starArgsArray[2]),
        Double.parseDouble(starArgsArray[3]),
        Double.parseDouble(starArgsArray[4]));
  }
}
