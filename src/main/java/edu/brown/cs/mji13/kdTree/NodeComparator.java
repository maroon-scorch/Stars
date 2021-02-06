package edu.brown.cs.mji13.kdTree;

public interface NodeComparator<T> {
  Boolean comp(T node1, T node2);
}
