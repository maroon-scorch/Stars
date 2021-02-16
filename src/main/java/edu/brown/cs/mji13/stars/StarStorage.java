package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.kdTree.KDTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stars Storage Object for storing common datatypes, commands, and utilities
 * shared between all stars commands.
 */
public class StarStorage {
  /**
   * The argument for StarsStorage: a name assigned to the StorageUnit.
   */
  private String title;

  /**
   * Constructs a simple StarStorage Object.
   *
   * @param title - the name assigned to the object.
   */
  public StarStorage(String title) {
    this.title = title;
  }

  /**
   * The name of the current file the Command is operating on.
   */
  private final StringBuilder currentFile = new StringBuilder();

  /**
   * The list of Stars to store the converted lines to stars in.
   */
  private final ArrayList<Star> starsList = new ArrayList<>();

  /**
   * The KDTree of Stars to store the convert the list of stars to tree.
   */
  private final KDTree<Star> starsTree = new KDTree<>(3);

  /**
   * The Hashmap of the Star's Name to the Star Object.
   */
  private Map<String, Star> starsMap = new HashMap<>();

  /**
   * Returns the name of the current file the stars commands are operating on.
   *
   * @return the current file name
   */
  public String getFileName() {
    return currentFile.toString();
  }

  /**
   * Changes the name of the current file the stars commands are operating on.
   *
   * @param name - the name to replace the current name.
   */
  public void setName(String name) {
    currentFile.replace(0, currentFile.length(), name);
  }

  /**
   * Returns the current list of stars that the stars commands are operating on.
   *
   * @return the starsList.
   */
  public ArrayList<Star> getStarsList() {
    return new ArrayList<>(starsList);
  }

  /**
   * Replaces the current list of stars with another list of stars.
   *
   * @param slist - the list of stars to replace the current one.
   */
  public void setList(ArrayList<Star> slist) {
    starsList.clear();
    starsList.addAll(slist);
  }

  /**
   * Returns the current KD Tree of Stars.
   *
   * @return the current KD Tree of Stars.
   */
  public KDTree<Star> getStarsTree() {
    return starsTree;
  }

  /**
   * Takes in a list of stars and uses it to construct a KD Tree of stars.
   *
   * @param slist - the list of stars to be used to construct the KD Tree.
   */
  public void setListToTree(ArrayList<Star> slist) {
    starsTree.clear();
    starsTree.constructTree(slist);
  }

  /**
   * Given a name, searches if the Hashmap contains a star with said name.
   *
   * @param name - the key to find the corresponding star.
   * @return the star associated with the name, if found. In the command,
   * isNameInStorage is used to check before executing said command here.
   */
  public Star getStarFromMap(String name) {
    return starsMap.get(name);
  }

  /**
   * Given a name, searches if the Hashmap contains a star with said name.
   *
   * @param name - the key to find the corresponding star.
   * @return if the storage contains said name or not.
   */
  public Boolean isNameInStorage(String name) {
    return starsMap.containsKey(name) && !name.isEmpty();
  }

  /**
   * Returns the set of all names stored.
   *
   * @return the set of all names stored.
   */
  public Set<String> getStarNames() {
    return starsMap.keySet();
  }

  /**
   * Takes in a list of stars and uses it to construct a Hashmap of the star's name
   * to the stars. If the name is empty, do not add the star.
   *
   * @param slist - the list of stars to be used to construct the Hashmap.
   */
  public void setListToStarsMap(ArrayList<Star> slist) {
    starsMap.clear();
    for (Star item : slist) {
      String name = item.getName();
      if (!name.isEmpty()) {
        starsMap.put(name, item);
      }
    }
  }

  /**
   * Takes in a list of stars and uses it to construct a HTML Table with each Star
   * as a row. This is intended for the GUI.
   *
   * @param slist - the list of stars to be used to construct the Hashmap.
   * @return the HTML Table of the Stars
   */
  public String starListToHTML(ArrayList<Star> slist) {
    String header = "<tr>"
        + "<th>StarID</th>"
        + "<th>ProperName</th>"
        + "<th>Coordinates (X, Y, Z)</th>"
        + "</tr>";

    String body = "";

    for (Star str : slist) {
      String row = str.toHTML();
      body += row;
    }

    String table = "<table>" + header + body + "</table>";
    return table;
  }

}
