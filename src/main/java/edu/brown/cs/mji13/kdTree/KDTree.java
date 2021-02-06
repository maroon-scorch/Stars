package edu.brown.cs.mji13.kdTree;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Comparator;

public class KDTree<T> {
  private int dimension;
  // private ArrayList<NodeComparator<T>> cmpList;
  private ArrayList<Comparator<T>> cmpList;
  private KDNode<T> root;

  public KDTree(ArrayList<Comparator<T>> cmpList) {
    if (cmpList.size() < 1) {
      throw new IllegalArgumentException("KD Trees cannot have a dimension less than 1");
    }

    this.dimension = cmpList.size();
    this.cmpList = new ArrayList<>(cmpList);
    this.root = new KDNode<T>();
  }

  public KDTree() {
  }

  public void constructTree(ArrayList<T> itemList) {
    ArrayList<T> template = new ArrayList<>(itemList);
    this.root = constructTreeHelper(template, 0);
  }

  public KDNode<T> constructTreeHelper(ArrayList<T> itemList, int level) {
    if (itemList.isEmpty()) {
      return new KDNode<T>();
    }

    ArrayList<T> currentList = new ArrayList<>(itemList);
    Comparator<T> currentCmp = cmpList.get(level % dimension);
    currentList.sort(currentCmp);

    // System.out.println(currentList);

    int median = (currentList.size() - 1) / 2;
    T currentItem = currentList.get(median);
    KDNode<T> currentNode = new KDNode<>(currentItem, currentCmp);

    ArrayList<T> leftList = new ArrayList<>();
    ArrayList<T> rightList = new ArrayList<>();

    for (int i = 0; i < median; i++) {
      leftList.add(currentList.get(i));
    }

    for (int j = median + 1; j < itemList.size(); j++) {
      rightList.add(currentList.get(j));
    }

    currentNode.setLeft(constructTreeHelper(leftList, level + 1));
    currentNode.setRight(constructTreeHelper(rightList, level + 1));

    return currentNode;
  }

//  public void insert(T item, int level, KDNode<T> currentNode) {
//    if (currentNode.getItem() == null) {
//      NodeComparator<T> cmpToInsert = cmpList.get(level % dimension);
//
//      currentNode.setItem(item);
//      currentNode.setCmp(cmpToInsert);
//      currentNode.setLeft(new KDNode<T>());
//      currentNode.setRight(new KDNode<T>());
//    }
//
//    T currentItem = currentNode.getItem();
//    NodeComparator<T> currentCmp = currentNode.getCmp();
//
//    if (currentCmp.comp(currentItem, item)) {
//      insert(item, level + 1, currentNode.getLeft());
//    } else {
//      insert(item, level + 1, currentNode.getRight());
//    }
//  }

  public void clear() {
    root = new KDNode<T>();
  }

  public Integer rootSize() {
    return treeSize(root);
  }

  public Integer treeSize(KDNode<T> currentNode) {
    if (currentNode.getItem() == null) {
      return 0;
    }

    KDNode<T> leftNode = currentNode.getLeft();
    KDNode<T> rightNode = currentNode.getRight();
    return 1 + treeSize(leftNode) + treeSize(rightNode);
  }

  public KDNode<T> getTree() {
    return root;
  }

}
