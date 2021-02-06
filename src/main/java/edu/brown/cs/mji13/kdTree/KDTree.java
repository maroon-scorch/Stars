package edu.brown.cs.mji13.kdTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The KD Tree Object implemented for efficient multi-dimensional searches.
 *
 * @param <T> - the generic type T of the items that the tree stores;
 *            must extend from HasCoordinates
 */
public class KDTree<T extends HasCoordinates> {
  /**
   * The two fields that a KD Tree stores:
   * 1. the dimension of the KDTree.
   * 2. the Tree itself.
   */
  private int dimension;
  private KDNode<T> root;

  /**
   * Constructs a KD Tree Object of the dimension specified, so sets up an empty tree.
   *
   * @param dimension - the dimension of the KD Tree, must be at least 1
   */
  public KDTree(int dimension) {
    if (dimension < 1) {
      throw new IllegalArgumentException("KD Trees cannot have a dimension less than 1");
    }

    this.dimension = dimension;
    this.root = new KDNode<T>();
  }

  /**
   * Constructs the actual KD Tree of Type T for a given arraylist of the same item types.
   *
   * @param itemList - the list of items to be turned into a KD Tree
   */
  public void constructTree(ArrayList<T> itemList) {
    ArrayList<T> template = new ArrayList<>(itemList);
    this.root = constructTreeHelper(template, 0);
  }

  /**
   * The helper method of constructTree that keeps track of the depth of the KD Tree
   * over recursion.
   *
   * @param itemList - the list of items to be turned into a KD Tree.
   * @param level    - increments by 1 following down each recursion, analogous
   *                 to the current depth of the tree.
   * @return - the fully constructed KD Tree
   */
  private KDNode<T> constructTreeHelper(ArrayList<T> itemList, int level) {
    // If the list is empty, then return the empty node.
    if (itemList.isEmpty()) {
      return new KDNode<T>();
    }

    /*
     Sorts the ArrayList by the comparator over one type of coordinate.
     Note: level % dimension keeps track of which type of coordinate to sort through.
      (ex. For x, y, z, at level 4, 4 % 3 = 1, so sort using x (1)).
     */
    ArrayList<T> currentList = new ArrayList<>(itemList);
    currentList.sort(Comparator.comparingDouble(cord -> cord.getCoordinate(level % dimension)));

    // Finds the median, set the current Node to be the item at median
    int median = (currentList.size() - 1) / 2;
    T currentItem = currentList.get(median);
    KDNode<T> currentNode = new KDNode<>(currentItem);

    // Construct lists left and right of the median, add them in accordingly.
    ArrayList<T> leftList = new ArrayList<>();
    ArrayList<T> rightList = new ArrayList<>();

    for (int i = 0; i < median; i++) {
      leftList.add(currentList.get(i));
    }

    for (int j = median + 1; j < itemList.size(); j++) {
      rightList.add(currentList.get(j));
    }

    // Pass the two lists down in recursion again
    currentNode.setLeft(constructTreeHelper(leftList, level + 1));
    currentNode.setRight(constructTreeHelper(rightList, level + 1));

    // After all recursions finished, the currentNode is the constructed tree.
    return currentNode;
  }


  /**
   * Given a coordinate and a radius, returns the list of all items within the radius from
   * closest to furthest.
   *
   * @param radius - the radius of the search
   * @param cords  - the coordinate of the search
   * @return the sorted list of all items within the radius of the cords
   */
  public ArrayList<T> findNodesInRadius(double radius, List<Double> cords) {
    ArrayList<T> template = new ArrayList<>();
    radiusHelper(radius, cords, 0, template, getTree());
    template.sort(Comparator.comparingDouble(star -> star.distanceTo(cords)));
    return template;
  }

  /**
   * The helper method of findNodesInRadius that recurs down the tree and performs
   * the search.
   *
   * @param radius      - the radius of the search
   * @param cords       - the coordinate of the search
   * @param level       - the depth of the currentNode in the whole tree
   * @param template    - the ArrayList of items to be updated on during Recursion
   * @param currentNode - the currentNode being recurred on
   */
  private void radiusHelper(double radius, List<Double> cords, int level,
                           ArrayList<T> template, KDNode<T> currentNode) {
    // If the current item is null (empty node) then break
    T currentItem = currentNode.getItem();
    if (currentItem == null) {
      return;
    }
    // If the current item's distance to the coordinate is less than the radius,
    // add the item to the ArrayList
    if (currentItem.distanceTo(cords) <= radius) {
      template.add(currentItem);
    }

    KDNode<T> leftNode = currentNode.getLeft();
    KDNode<T> rightNode = currentNode.getRight();

    // Determine which Axis Distance to Compare to
    int choice = level % dimension;

    // Finds the axis distance between the currentNode and the search position
    double currentAxisCord = currentItem.getCoordinate(choice);
    double fixedAxisCord = cords.get(choice);
    double axisDist = Math.abs(currentAxisCord - fixedAxisCord);

    // Search both if the distance is less than the radius, otherwise, check
    // direction to determine which subtree to traverse down
    if (axisDist <= radius) {
      radiusHelper(radius, cords, level + 1, template, leftNode);
      radiusHelper(radius, cords, level + 1, template, rightNode);
    } else {
      if (fixedAxisCord < currentAxisCord) {
        radiusHelper(radius, cords, level + 1, template, leftNode);
      } else if (fixedAxisCord >= currentAxisCord) {
        radiusHelper(radius, cords, level + 1, template, rightNode);
      }
    }
  }

  /**
   * Given a coordinate and a count, returns the list of count-th nearest neighbors to
   * the coordinate. If there's a tie between elements that exceed the size requested,
   * the result list plus the tied elements will be returned.
   *
   * @param count - the count of how many neighbors are wanted
   * @param cords - the coordinate of the search
   * @return the sorted list of the result list plus (if exists) all the tied elements
   * to the last element of the result list.
   */
  public ArrayList<T> findNearestNeighbors(int count, List<Double> cords) {
    // If asking for zero neighbors, return an empty list
    if (count == 0) {
      return new ArrayList<>();
    }

    // The PriorityQueue is a maxheap where the item whose distance is furthest to the
    // coordinate is the first element of the queue.
    PriorityQueue<T> maxItemQueue = new PriorityQueue<T>((T item1, T item2)
        -> Double.compare(item2.distanceTo(cords), item1.distanceTo(cords)));
    // The ArrayList storing all the tied stars.
    ArrayList<T> tiedList = new ArrayList<>();

    // Calls the helper function to recur down the tree.
    neighborsHelper(count, cords, 0, maxItemQueue, tiedList, getTree());

    // Converts the completed queue back to the list and sorts it
    ArrayList<T> resultList = new ArrayList<>(maxItemQueue);
    resultList.sort(Comparator.comparingDouble(T -> T.distanceTo(cords)));

    // Adds all the tiedLists elements to the end of the result list.
    resultList.addAll(tiedList);
    return resultList;
  }

  /**
   * The helper method of findNearestNeighbors that recurs down the tree and performs
   * the search.
   *
   * @param count       - the count of the search
   * @param cords       - the coordinate of the search
   * @param level       - the depth of the currentNode in the whole tree
   * @param itemQueue   - the Queue storing all the relevant items that are nearest
   * @param tiedList    - the list of tied items to the first element of the Queue
   * @param currentNode - the currentNode being recurred on
   */
  private void neighborsHelper(int count, List<Double> cords, int level,
                               PriorityQueue<T> itemQueue, ArrayList<T> tiedList,
                               KDNode<T> currentNode) {
    // If the node is an empty node, break out of the current function call.
    T currentItem = currentNode.getItem();
    if (currentItem == null) {
      return;
    }

    // If the Queue isn't full yet (has a size of count), add the item in.
    if (itemQueue.size() < count) {
      itemQueue.add(currentItem);
    } else {
      // If the Queue is full:
      T maxItem = itemQueue.element();
      // If the distance of the coordinate to the currentItem is less than that
      // to the first element, remove the first element, clear the tied list,
      // and add the current element down.
      if (currentItem.distanceTo(cords) < maxItem.distanceTo(cords)) {
        itemQueue.poll();
        tiedList.clear();
        itemQueue.add(currentItem);
        // If the two elements have the same distance, add the currentItem to the tiedlist.
      } else if (currentItem.distanceTo(cords) == maxItem.distanceTo(cords)) {
        tiedList.add(currentItem);
      }
    }

    KDNode<T> leftNode = currentNode.getLeft();
    KDNode<T> rightNode = currentNode.getRight();

    // Determine which Axis Distance to Compare to
    int choice = level % dimension;

    double maxDist = itemQueue.element().distanceTo(cords);
    double currentAxisCord = currentItem.getCoordinate(choice);
    double fixedAxisCord = cords.get(choice);
    double axisDist = Math.abs(currentAxisCord - fixedAxisCord);

    // Search both if the distance is less than the maximum distance to the furthest element,
    // otherwise, check direction to determine which subtree to traverse down
    if (axisDist <= maxDist) {
      neighborsHelper(count, cords, level + 1, itemQueue, tiedList, leftNode);
      neighborsHelper(count, cords, level + 1, itemQueue, tiedList, rightNode);
    } else {
      if (fixedAxisCord < currentAxisCord) {
        neighborsHelper(count, cords, level + 1, itemQueue, tiedList, leftNode);
      } else if (fixedAxisCord >= currentAxisCord) {
        neighborsHelper(count, cords, level + 1, itemQueue, tiedList, rightNode);
      }
    }
  }


  /**
   * Clears the tree to a new empty tree.
   */
  public void clear() {
    root = new KDNode<T>();
  }

  /**
   * Find the size of the KD Tree.
   *
   * @return the size of the KD Tree.
   */
  public Integer rootSize() {
    return treeSize(root);
  }

  /**
   * Helper method of rootSize that recurs down the tree to find its size.
   *
   * @param currentNode - the tree to find the size for.
   * @return the size of the Tree.
   */
  private Integer treeSize(KDNode<T> currentNode) {
    if (currentNode.getItem() == null) {
      return 0;
    }

    KDNode<T> leftNode = currentNode.getLeft();
    KDNode<T> rightNode = currentNode.getRight();
    return 1 + treeSize(leftNode) + treeSize(rightNode);
  }

  /**
   * Returns the tree stored in the object.
   *
   * @return the KD Tree.
   */
  public KDNode<T> getTree() {
    return root;
  }

}
