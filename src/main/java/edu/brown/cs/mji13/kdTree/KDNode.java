package edu.brown.cs.mji13.kdTree;

/**
 * The KD Node that stores the serves as the building block of the KD Tree
 * A KD Node can be one of two types:
 * 1. node(T item, node left, node right)
 * 2. empty node which is KDNode().
 *
 * @param <T> - Generic type of the item that each Node holds, must extend from HasCoordinates
 */
public class KDNode<T extends HasCoordinates> {
  /**
   * The fields a non-empty KD Node stores:
   * item, left child, right child.
   */
  private T item;
  private KDNode<T> left, right;

  /**
   * Constructs a new non-empty node, left and right children are initially empty nodes.
   *
   * @param item - the item to be stored at the head of this node
   */
  public KDNode(T item) {
    this.item = item;
    this.left = new KDNode<T>();
    this.right = new KDNode<T>();
  }

  /**
   * Constructs an empty node, all fields are currently null.
   */
  public KDNode() {
    this.item = null;
    this.left = null;
    this.right = null;
  }

  /**
   * Gets the current item at the node.
   *
   * @return the item at the node
   */
  public T getItem() {
    return item;
  }

  /**
   * Gets the left child of the node.
   *
   * @return the left child at the node
   */
  public KDNode<T> getLeft() {
    return left;
  }

  /**
   * Gets the right child of the node.
   *
   * @return the right child at the node
   */
  public KDNode<T> getRight() {
    return right;
  }

  /**
   * Sets the current item of the node to another item.
   *
   * @param item - the item to replace the current item
   */
  public void setItem(T item) {
    this.item = item;
  }

  /**
   * Sets the current left child of the node to another node.
   *
   * @param node - the node to replace the left child
   */
  public void setLeft(KDNode<T> node) {
    this.left = node;
  }

  /**
   * Sets the current right child of the node to another node.
   *
   * @param node - the node to replace the right child
   */
  public void setRight(KDNode<T> node) {
    this.right = node;
  }

  /**
   * Returns the string literal of the Tree (constructed in PostOrder).
   *
   * @return - the string version of the tree
   */
  public String toString() {
    try {
      return ((item == null) ? "" : item.toString()) + " "
          + ((left == null) ? "" : left.toString()) + " "
          + ((right == null) ? "" : right.toString());
    } catch (Exception e) {
      System.out.println("ERROR: To String for Tree of this type hasn't been implemented yet");
      return "";
    }
  }

}
