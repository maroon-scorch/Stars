package edu.brown.cs.mji13.main.kdtree;

import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.stars.NaiveRadius;
import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

  private KDTree<Star> starKDTree;

  /**
   * Sets up the KD Tree of stars
   */
  @Before
  public void setUp() {
    starKDTree = new KDTree<>(3);
    ArrayList<Star> starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", Arrays.asList(0.0, 0.0, 0.0)),
        new Star("2", "2", Arrays.asList(10.0, 6.0, 5.0)),
        new Star("3", "3", Arrays.asList(3.1, 1.5, -6.7)),
        new Star("4", "4", Arrays.asList(-5.0, -2.0, -7.0)),
        new Star("5", "5", Arrays.asList(-10.6, 13.0, 12.0)),
        new Star("6", "6", Arrays.asList(0.5, 6.0, 0.0)),
        new Star("7", "7", Arrays.asList(56.0, -50.0, -5.0)),
        new Star("8", "8", Arrays.asList(5.0, 6.0, 7.0)),
        new Star("9", "9", Arrays.asList(3.0, 4.7, 6.0)),
        new Star("10", "10", Arrays.asList(10.0, 24.0, 3.0))};
    starsList.addAll(Arrays.asList(starArray));
    starKDTree.constructTree(starsList);
  }

  /**
   * Resets the KD Tree of stars.
   */
  @After
  public void tearDown() {
    starKDTree = null;
  }

  /**
   ** Tests for constructing the KDTree
   */
  @Test
  public void testConstructTree() {
    setUp();
    KDNode<Star> tree = starKDTree.getTree();
    assertEquals(tree.getItem().getStarID(), "9");
    assertEquals(tree.getLeft().getItem().getStarID(), "1");
    assertEquals(tree.getRight().getItem().getStarID(), "8");
    assertEquals(tree.getLeft().getLeft().getItem().getStarID(), "4");
    assertEquals(tree.getRight().getLeft().getItem().getStarID(), "3");
    tearDown();
  }

  /**
   ** Tests for findNodesInRadius of the KDTree
   */
  @Test
  public void testRadiusNormal() {
    setUp();
    ArrayList<Star> result = starKDTree.findNodesInRadius(100, Arrays.asList(0.0, 0.0, 0.0));
    assertEquals(result.size(), 10);
    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");
    assertEquals(result.get(2).getStarID(), "3");
    assertEquals(result.get(3).getStarID(), "9");
    assertEquals(result.get(4).getStarID(), "4");
    assertEquals(result.get(5).getStarID(), "8");
    assertEquals(result.get(6).getStarID(), "2");
    assertEquals(result.get(7).getStarID(), "5");
    assertEquals(result.get(8).getStarID(), "10");
    assertEquals(result.get(9).getStarID(), "7");
    tearDown();
  }

  /**
   ** Tests for findNodesInRadius of the KDTree - exactly on point
   */
  @Test
  public void testRadiusExact() {
    setUp();
    ArrayList<Star> result = starKDTree.findNodesInRadius(0, Arrays.asList(0.0, 0.0, 0.0));
    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getStarID(), "1");
    tearDown();
  }

  /**
   ** Tests for findNodesInRadius of the KDTree - nothing found
   */
  @Test
  public void testRadiusNone() {
    setUp();
    ArrayList<Star> result = starKDTree.findNodesInRadius(0.5, Arrays.asList(3.6, 0.0, 0.0));
    assertEquals(result.size(), 0);
    tearDown();
  }

  /**
   ** Tests for findNearestNeighbors of the KDTree
   */
  @Test
  public void testNeighborsNormal() {
    setUp();
    ArrayList<Star> result
        = starKDTree.findNearestNeighbors(5, Arrays.asList(3.6, 0.0, 0.0));
    assertEquals(result.size(), 5);
    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");
    assertEquals(result.get(2).getStarID(), "3");
    assertEquals(result.get(3).getStarID(), "9");
    assertEquals(result.get(4).getStarID(), "8");
    tearDown();
  }

  /**
   ** Tests for findNearestNeighbors of the KDTree - zero neighbors
   */
  @Test
  public void testNeighborsZero() {
    setUp();
    ArrayList<Star> result
        = starKDTree.findNearestNeighbors(0, Arrays.asList(3.6, 0.0, 0.0));
    assertEquals(result.size(), 0);
    tearDown();
  }

  /**
   ** Tests for findNearestNeighbors of the KDTree - Number of Neighbors Exceed List Size
   */
  @Test
  public void testNeighborsExceed() {
    setUp();
    ArrayList<Star> result
        = starKDTree.findNearestNeighbors(15, Arrays.asList(3.6, 0.0, 0.0));
    assertEquals(result.size(), 10);
    tearDown();
  }

  /**
   ** Tests for findNearestNeighbors of the KDTree - Tie
   */
  @Test
  public void testNeighborsTie() {
    setUp();
    starKDTree.clear();
    ArrayList<Star> dupList = new ArrayList<>();
    dupList.add(new Star("A", "A", Arrays.asList(0.0, 0.0, 1.0)));
    dupList.add(new Star("B", "A", Arrays.asList(0.0, 0.0, 0.0)));
    dupList.add(new Star("C", "A", Arrays.asList(0.0, 1.0, 0.0)));
    starKDTree.constructTree(dupList);

    ArrayList<Star> result
        = starKDTree.findNearestNeighbors(2, Arrays.asList(0.0, 0.0, 0.0));

    // Explained in the javadoc for the method ^
    assertEquals(result.size(), 3);
    assertEquals(result.get(0).getStarID(), "B");

    tearDown();
  }

  /**
   ** Tests the rootSize method of KDTree
   */
  @Test
  public void testTreeSize() {
    setUp();

    int size = starKDTree.rootSize();
    assertEquals(10, size);

    starKDTree.clear();
    size = starKDTree.rootSize();
    assertEquals(0, size);

    tearDown();
  }
}
