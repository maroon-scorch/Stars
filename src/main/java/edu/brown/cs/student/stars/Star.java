package edu.brown.cs.student.stars;

import edu.brown.cs.student.validations.ArgsInformation;
import edu.brown.cs.student.validations.ArgsValidator;
import edu.brown.cs.student.validations.StringValFunctions;
import edu.brown.cs.student.validations.StringValidation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * The Stars Object that stores the information from the CSV File.
 */
public class Star implements StringValFunctions {
  /**
   * The list of fields MockPerson stores:
   * starID, star name, x coordinate, y coordinate, z coordinate.
   */
  private final String starID, starName;
  private final double x, y, z;

  /**
   * Specifications on the requirements on the argument passed to the command.
   * starID - any String
   * starName - any String
   * x - numeric
   * y - numeric
   * z - numeric.
   */
  private final Map<Integer, ArgsInformation[]> stringEntryValidation
      = Map.ofEntries(
      entry(5, new ArgsInformation[] {new ArgsInformation(
          "star_x_y_z",
          new String[] {"starID: any string", "starName: any string", "x: number",
              "y: number", "z: number"},
          new StringValidation[] {this::pass, this::pass, this::isNumeric, this::isNumeric,
              this::isNumeric},
          new String[] {"ERROR: Star ID of row is malformed",
              "ERROR: Proper Name of row is malformed",
              "ERROR: Coordinate X of row must be numeric.",
              "ERROR: Coordinate Y of row must be numeric.",
              "ERROR: Coordinate Z of row must be numeric."
          }
      )}));

  /**
   * Creates a Star Object with the String Parameters Given.
   *
   * @param starID   id of Star
   * @param starName name of Star
   * @param strX     x coodinate of Star
   * @param strY     y coordiante of Star
   * @param strZ     z coordiante of Star
   */
  public Star(String starID, String starName, String strX, String strY, String strZ) {
    ArrayList<String> stringArgs = new ArrayList<>();
    stringArgs.add(starID);
    stringArgs.add(starName);
    stringArgs.add(strX);
    stringArgs.add(strY);
    stringArgs.add(strZ);

    ArgsValidator argsValidator = new ArgsValidator("star:", stringEntryValidation);
    Optional<String> opMethodName = argsValidator.testArgs(stringArgs);
    if (opMethodName.isEmpty()) {
      throw new IllegalArgumentException("ERROR: The fields of the row does not"
          + "match the specifications of stars");
    }

    this.starID = starID;
    this.starName = starName;
    this.x = Double.parseDouble(strX);
    this.y = Double.parseDouble(strY);
    this.z = Double.parseDouble(strZ);
  }

  /**
   * Creates a Star Object with the Parameters in the Proper Types Given.
   *
   * @param starID   id of Star
   * @param starName name of Star
   * @param x        x coodinate of Star
   * @param y        y coordiante of Star
   * @param z        z coordiante of Star
   */
  public Star(String starID, String starName, double x, double y, double z) {
    this.starID = starID;
    this.starName = starName;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Finds the distance of the star to the coordinate given.
   *
   * @param xPos x coodinate of destination
   * @param yPos y coordiante of destination
   * @param zPos z coordiante of destination
   * @return the distance
   */
  public double distanceTo(double xPos, double yPos, double zPos) {
    double xDiff = x - xPos;
    double yDiff = y - yPos;
    double zDiff = z - zPos;

    return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2));
  }

  /**
   * Gets the name of the star.
   *
   * @return name
   */
  public String getName() {
    return starName;
  }

  /**
   * Gets the x coordinate of the star.
   *
   * @return x
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate of the star.
   *
   * @return y
   */
  public double getY() {
    return y;
  }

  /**
   * Gets the z coordinate of the star.
   *
   * @return z
   */
  public double getZ() {
    return z;
  }

  /**
   * Enables the star object to be printable.
   *
   * @return the String Literal of the star (which is its id)
   */
  public String toString() {
    return starID;
  }

}
