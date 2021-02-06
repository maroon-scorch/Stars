package edu.brown.cs.mji13.kdTree;

import java.util.Comparator;

public class KDNode<T> {
  private T item;
  private Comparator<T> cmp;
  // private NodeComparator<T> cmp;
  // private KDNode<T> parent;
  private KDNode<T> left, right;

  public KDNode(T item, Comparator<T> cmp) {
    this.item = item;
    this.cmp = cmp;
    // this.parent = parent;
    this.left = new KDNode<T>();
    this.right = new KDNode<T>();
  }

  public KDNode() {
    this.item = null;
    this.cmp = null;
    this.left = null;
    this.right = null;
  }

  public T getItem() {
    return item;
  }

  public Comparator<T> getCmp() {
    return cmp;
  }

  public KDNode<T> getLeft() {
    return left;
  }

  public KDNode<T> getRight() {
    return right;
  }

  public void setItem(T item) {
    this.item = item;
  }

  public void setCmp(Comparator<T> cmp) {
    this.cmp = cmp;
  }

  public void setLeft(KDNode<T> node) {
    this.left = node;
  }

  public void setRight(KDNode<T> node) {
    this.right = node;
  }

  public String toString() {
    try {
      return ((item == null) ? "" : item.toString()) + " "
          + ((left == null) ? "" : left.toString()) + " "
          + ((right == null) ? "" : right.toString());
    } catch(Exception e) {
      System.out.println("ERROR: To String for Tree of this type hasn't been implemented yet");
      return "";
    }
  }

}
