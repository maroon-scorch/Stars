package edu.brown.cs.mji13.main.kdtree;

import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.stars.Star;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class KDNodeTest {
  /**
   ** Tests the get and set Methods of KDNode
   */
  @Test
  public void testNodeGetSet() {
    KDNode<Star> sampleNode = new KDNode<Star>(
        new Star("Abc", "My life is a joke", Arrays.asList(1.6, 5.4, 3.2)));

    sampleNode.setLeft(new KDNode<Star>(
        new Star("1", "3", Arrays.asList(1.6, 0.4, 3.7))));
    sampleNode.setRight(new KDNode<Star>(
        new Star("3", "35", Arrays.asList(-8.9, 4.4, 3.6))));

    assertEquals(sampleNode.getItem().getStarID(), "Abc");
    assertEquals(sampleNode.getLeft().getItem().getStarID(), "1");
    assertEquals(sampleNode.getRight().getItem().getStarID(), "3");

    sampleNode.setItem(new Star("6", "1", Arrays.asList(1.6, 5.4, 3.2)));
    assertEquals(sampleNode.getItem().getStarID(), "6");
  }

  /**
   ** Tests the empty constructors of KDNode
   */
  @Test
  public void testEmptyConstructor() {
    KDNode<Star> mtNode = new KDNode<>();
    assertNull(mtNode.getItem());
    assertNull(mtNode.getLeft());
    assertNull(mtNode.getRight());
  }

}
