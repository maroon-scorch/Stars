package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Random;

import static java.util.Map.entry;

public class Neighbors implements Command, StringValFunctions {
  private StarStorage starStorage;

  /**
   * Specifications on the requirements on the argument passed to the command.
   * 2 Arguments:
   * - neighbors: non-negative integer
   * - name: surrounded by double quotes
   * 4 Arguments:
   * - neighbors: non-negative integer
   * - x: number
   * - y: number
   * - z: number
   * See custom StringValidation Method at the Bottom
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps
      = Map.ofEntries(
      entry(2, new ArgsInformation[] {new ArgsInformation(
          "neighbors_2",
          new String[] {"neighbors: int >= 0", "\"name\""},
          new StringValidation[] {this::isNonNegInt, this::isName},
          new String[] {"ERROR: Number of Neighbors must be a Positive Integer.",
              "ERROR: Name must be surrounded by double quotes."}
      )}),
      entry(4, new ArgsInformation[] {new ArgsInformation(
          "neighbors_4",
          new String[] {"neighbors: int >= 0", "x: number", "y: number", "z: number"},
          new StringValidation[] {this::isNonNegInt, this::isNumeric,
              this::isNumeric, this::isNumeric},
          new String[] {"ERROR: Number of Neighbors must be a Positive Integer.",
              "ERROR: Coordinate X must be numeric.",
              "ERROR: Coordinate Y must be numeric.",
              "ERROR: Coordinate Z must be numeric."
          }
      )})
  );

  /**
   * The argument validator for the arguments of the mock <csv> command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("neighbors", reqInfoMaps);

  /**
   * Creates a Neighbors object.
   *
   * @param starStorage  - the storage of relevant stars data shared by files
   */
  public Neighbors(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Creates a Neighbors object.
   */
  public Neighbors() {
  }

  /**
   * Executes the naive_neighbors Command.
   * If successful, prints out the closest n number of stars to the specified location.
   *
   * Note: TA Colton said that the randomization is meant for tiebreakers to include on the list
   * so if there are stars with same distance away but including them does not exceed that number
   * asked, they will be included just as normal.
   * @param args - the list of arguments to be operated on.
   */
  public void execute(ArrayList<String> args) {
    if (starStorage.getFileName().length() == 0) {
      System.out.println("ERROR: No file has been loaded yet");
      return;
    }

    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      return;
    }

    String methodName = opMethodName.get();
    KDTree<Star> sTree = starStorage.getStarsTree();
    switch (methodName) {
      case "neighbors_2":
        int neighbors = Integer.parseInt(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        ArrayList<Star> starsInRange2 = performNeighbors(neighbors, sStarNoQuotes, sTree);
        starsInRange2.forEach(System.out::println);
        break;
      case "neighbors_4":
        int intNeighbors = Integer.parseInt(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        ArrayList<Star> starsInRange4 =
            performNeighbors(intNeighbors, dPosX, dPosY, dPosZ, sTree, Optional.empty());
        starsInRange4.forEach(System.out::println);
        break;
      default:
        System.out.println("ERROR: Hashmap reqInfoMaps has unregistered names, "
            + "shouldn't have reached here");
        break;
    }
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute.
   *
   * @param args the list of arguments to be operated on
   * @return Option of String - empty if the arguments are invalid, a String if match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  public ArrayList<Star> performNeighbors(
      int count, double x, double y, double z, KDTree<Star> tree, Optional<String> name) {

    if (count == 0) {
      return new ArrayList<>();
    }

    boolean filterName = name.isPresent();

    PriorityQueue<Star> maxStarQueue = new PriorityQueue<Star>((Star star1, Star star2)
        -> Double.compare(star2.distanceTo(x, y, z), star1.distanceTo(x, y, z)));
    ArrayList<Star> tiedList = new ArrayList<>();

    int dimension = 3;

    performNeighborsHelper(count, x, y, z, 0, dimension, maxStarQueue, tiedList, tree.getTree());

    ArrayList<Star> resultList = new ArrayList<>(maxStarQueue);
    resultList.sort(Comparator.comparingDouble(star -> star.distanceTo(x, y, z)));;

    if (filterName) {
      String pName = name.get();
      resultList.removeIf(star -> star.getName().equals(pName));
      tiedList.removeIf(star -> star.getName().equals(pName));
      count = count - 1;
    }

    if (tiedList.isEmpty()) {
      return resultList;
    }

    resultList.addAll(tiedList);
    ArrayList<Star> fullTiedList = new ArrayList<>(resultList);
    double distAtCount = resultList.get(count - 1).distanceTo(x, y, z);
    resultList.removeIf(star -> star.distanceTo(x, y, z) == distAtCount);
    fullTiedList.removeIf(star -> star.distanceTo(x, y, z) != distAtCount);

    // System.out.println(fullTiedList);

    Random ran = new Random();
    for (int i = 0; i < (count - resultList.size() + 1); i++) {
      int selected = ran.nextInt(fullTiedList.size());
      resultList.add(fullTiedList.remove(selected));
    }

    return resultList;
  }

  public void performNeighborsHelper(
      int count, double x, double y, double z, int level, int dimension,
      PriorityQueue<Star> starsQueue, ArrayList<Star> tieList, KDNode<Star> currentNode) {

    Star currentStar = currentNode.getItem();
    if (currentStar == null) {
      return;
    }

    if (starsQueue.size() < count) {
      starsQueue.add(currentStar);
    } else {
      Star maxStar = starsQueue.element();
      if (currentStar.distanceTo(x, y, z) < maxStar.distanceTo(x, y, z)) {
        starsQueue.poll();
        tieList.clear();
        starsQueue.add(currentStar);
      } else if (currentStar.distanceTo(x, y, z) == maxStar.distanceTo(x, y, z)) {
        tieList.add(currentStar);
      }
    }


    KDNode<Star> leftNode = currentNode.getLeft();
    Star leftStar = leftNode.getItem();

    KDNode<Star> rightNode = currentNode.getRight();
    Star rightStar = rightNode.getItem();

    int choice = level % dimension;
    // zero
    double maxDist = starsQueue.element().distanceTo(x, y, z);

    switch (choice) {
      case 0:
        double xDist = Math.abs(currentStar.getX() - x);
        if (xDist <= maxDist) {
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
        } else {
          if (x < currentStar.getX()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          } else if (x >= currentStar.getX()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
          }
        }
        break;
      case 1:
        double yDist = Math.abs(currentStar.getY() - y);
        if (yDist <= maxDist) {
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
        } else {
          if (y < currentStar.getY()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          } else if (y >= currentStar.getY()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
          }
        }
        break;
      case 2:
        double zDist = Math.abs(currentStar.getZ() - z);
        if (zDist <= maxDist) {
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
        } else {
          if (z < currentStar.getZ()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, leftNode);
          } else if (z >= currentStar.getZ()) {
            performNeighborsHelper(count, x, y, z, level + 1, dimension, starsQueue, tieList, rightNode);
          }
        }
        break;
    }
  }
  public ArrayList<Star> performNeighbors(
      int count, String name, KDTree<Star> tree) {
    // If the name is empty, print an error
    if (name.isEmpty()) {
      System.out.println("ERROR: Empty String is not a valid name for stars");
      return new ArrayList<>();
    }

    // If the selected star is not found, print an error
    Optional<Star> selectedStar = starStorage.getStarFromMap(name);
    if (selectedStar.isEmpty()) {
      System.out.printf("ERROR: No Stars with name \"%s\" is found%n", name);
      return new ArrayList<>();
    }

    Star presentStar = selectedStar.get();

    double selectedX = presentStar.getX();
    double selectedY = presentStar.getY();
    double selectedZ = presentStar.getZ();

    ArrayList<Star> resultList
        = performNeighbors(count + 1, selectedX, selectedY, selectedZ, tree, Optional.of(name));

    return resultList;
  }
}
