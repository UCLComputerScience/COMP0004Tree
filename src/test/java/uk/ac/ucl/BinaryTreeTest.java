/*
 * TestNG test class for uk.ac.ucl.datastructures.BinaryTree class
 * Copyright (c) 2007
 * Dept. of Computer Science, University College London
 * @author Graham Roberts
 */
package uk.ac.ucl;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.datastructures.BinaryTree;
import uk.ac.ucl.datastructures.Tree;

import java.util.Iterator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class BinaryTreeTest
{
  private Tree<Integer> empty ;
  private Tree<Integer> one ;
  private Tree<Integer> several ;

  @Before
  public void setUp()
  {
    empty = new BinaryTree<>() ;
    one = new BinaryTree<>() ;
    one.add(0) ;
    several = new BinaryTree<>() ;
    several.add(5) ;
    several.add(2) ;
    several.add(1) ;
    several.add(9) ;
    several.add(8) ;
    several.add(10) ;
  }

  @Test
  public void testEmptyContainsZeroItems()
  {
    assertTreeEmpty(empty);
  }

  @Test
  public void testOneContainsOneItem()
  {
    assertTrue(one.contains(0)) ;
    assertIterationValid(one,new int[]{0});
  }

  @Test
  public void testSeveralContainsSixItems()
  {
    assertContains(several,new int[]{1,2,5,8,9,10}) ;
    assertIterationValid(several,new int[]{1,2,5,8,9,10});
  }

  @Test
  public void testSeveralDoesNotContain()
  {
    assertDoesNotContain(several,new int[]{-1,0,3,4,6,7,11}) ;
  }

  @Test
  public void testRemoveFromEmpty()
  {
    empty.remove(0);
    assertTreeEmpty(empty);
  }

  @Test
  public void testRemoveFromOne()
  {
    one.remove(0) ;
    assertFalse(one.contains(0)) ;
    assertTreeEmpty(one);
  }

  @Test
  public void testRemoveByLeaf()
  {
    assertRemoveAll(several,new int[]{5,2,1,8,10,9,5});
  }

  @Test
  public void testRemoveByRoot()
  {
    assertRemoveAll(several,new int[]{5,8,9,10,2,1});
  }

  @Test
  public void testRemoveEmptyNull()
  {
    empty.remove(null);
    assertTreeEmpty(empty);
  }

  @Test
  public void testRemoveEmptyOne()
  {
    one.remove(null);
    assertTrue(one.contains(0)) ;
    assertIterationValid(one,new int[]{0});
  }

  @Test
  public void testDuplicates()
  {
    empty.add(1) ;
    empty.add(1) ;
    empty.add(1) ;
    assertIterationValid(empty,new int[] {1,1,1});
    assertTrue(empty.contains(1)) ;
    empty.remove(1) ;
    assertTrue(empty.contains(1)) ;
    assertIterationValid(empty,new int[] {1,1});
    empty.remove(1) ;
    assertTrue(empty.contains(1)) ;
    assertIterationValid(empty,new int[] {1});
    empty.remove(1) ;
    assertTrue(!empty.contains(1)) ;
    assertTreeEmpty(empty);
  }

  @Test
  public void testInvalidRemove()
  {
    several.remove(-10);
    assertContains(several,new int[]{1,2,5,8,9,10}) ;
    assertIterationValid(several,new int[]{1,2,5,8,9,10});
  }

  @Test
  public void testIteratorReturnsNullAtEnd()
  {
    assertNull(empty.iterator().next());
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testIteratorRemoveThrowsException()
  {
    empty.iterator().remove();
  }

  private void assertTreeEmpty(Tree<Integer> tree)
  {
    Iterator<Integer> iterator = tree.iterator() ;
    assertTrue(!iterator.hasNext()) ;
  }

  private void assertRemoveAll(Tree<Integer> tree, int[] elements)
  {
    for (int i = 0 ; i < elements.length ; i++)
    {
      tree.remove(elements[i]);
      assertFalse(tree.contains(elements[i])) ;
    }
    assertTreeEmpty(tree);
  }

  private void assertContains(Tree<Integer> tree, int[] elements)
  {
    for (int i = 0 ; i < elements.length ; i++)
    {
      assertTrue(tree.contains(elements[i])) ;
    }
  }

  private void assertDoesNotContain(Tree<Integer> tree, int[] elements)
  {
    for (int i = 0 ; i < elements.length ; i++)
    {
      assertFalse(tree.contains(elements[i])) ;
    }
  }

  private void assertIterationValid(Tree<Integer> tree, int[] elements)
  {
    Iterator<Integer> iterator = tree.iterator() ;
    for (int i = 0 ; i < elements.length ; i++)
    {
      assertEquals(Integer.valueOf(elements[i]),iterator.next()) ;
    }
    assertFalse(iterator.hasNext());
  }
}


