package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.kdTree.KDTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StarStorage {
  private String id;
  public StarStorage(String id) {
    this.id = id;
  }

  /**
   * The name of the current file the Command is operating on.
   */
  private final StringBuilder currentFile = new StringBuilder();

  /**
   * The list of Stars to store the converted lines to stars in.
   */
  private final ArrayList<Star> starsList = new ArrayList<>();

  private ArrayList<Comparator<Star>> starCmpList = new ArrayList<>() {{
    add(Comparator.comparingDouble(Star::getX));
    add(Comparator.comparingDouble(Star::getY));
    add(Comparator.comparingDouble(Star::getZ));
  }};

  /**
   * The KDTree of Stars to store the convert the list of stars to tree.
   */
  private final KDTree<Star> starsTree = new KDTree<>(starCmpList);

  /**
   * The Hashmap of the Star's Name to the Star Object
   */
  private Map<String, Star> starsMap = new HashMap<>();

  public String getFileName() {
    return currentFile.toString();
  }

  public void setName(String name) {
    currentFile.replace(0, currentFile.length(), name);
  }

  public ArrayList<Star> getStarsList() {
    return new ArrayList<>(starsList);
  }

  public void setList(ArrayList<Star> slist) {
    starsList.clear();
    starsList.addAll(slist);
  }

  public KDTree<Star> getStarsTree() {
    return starsTree;
  }

  public void setListToTree(ArrayList<Star> slist) {
    starsTree.clear();
    starsTree.constructTree(slist);
  }

  public Optional<Star> getStarFromMap(String name) {
    if (starsMap.containsKey(name)) {
      return Optional.of(starsMap.get(name));
    }
    return Optional.empty();
  }

  public void setListToStarsMap(ArrayList<Star> slist) {
    starsMap.clear();
    for (Star item : slist) {
      String name = item.getName();
      if (!name.isEmpty()) {
        starsMap.put(name, item);
      }
    }
  }



}
