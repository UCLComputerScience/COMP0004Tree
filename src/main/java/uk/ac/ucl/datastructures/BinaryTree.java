/**
 *  A simple generic binary tree class to demonstrate the basic principles
 *  of implementing a tree data structure. This should not be taken as a production
 *  quality class.
 *  Copyright (c) 2019
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
 */
package uk.ac.ucl.datastructures;

import java.util.Stack;
import java.util.Iterator;

/**
 * Objects stored in a tree must conform to Comparable so that their values can
 * be compared. The type parameter is constrained to conform to Comparable to
 * enforce this.
 */
public class BinaryTree<T extends Comparable<T>> implements Tree<T>
{
  /**
   * A tree is a hierarchical structure of TreeNode objects. root references
   * the top (root) node of the tree.
   */
  private TreeNode<T> root;

  /**
   *  Helper class used to implement tree nodes. As this is a private helper
   *  class it is acceptable to have public instance variables. Instances of
   *  this class can never be made available to client code of the tree. The methods
   *  in this class do most of the work of manipulating the tree structure.
   *  Note that it is a static class, so T is not in scope in the class, but it
   *  doesn't need to be.
   */
  private static class TreeNode<E extends Comparable<E>>
  {
    /**
     *  Data object reference.
     */
    public E value;

    /**
     *  Left and right child nodes.
     */
    public TreeNode<E> left,right;

    /**
     *  Constructor for TreeNode.
     *
     * @param  value    data object reference
     * @param  left   left child node reference or null
     * @param  right  right child node reference or null
     */
    public TreeNode(final E value, final TreeNode<E> left, final TreeNode<E> right)
    {
      this.value = value;
      this.left = left;
      this.right = right;
    }

    /**
     * Insert an object into the tree.
     *
     * @param object object to insert into tree.
     */
    public void insert(final E object)
    {
      if (value.compareTo(object) < 0)
      {
        if (right != null)
        {
          right.insert(object) ;
        }
        else
        {
          right = new TreeNode<E>(object,null,null) ;
        }
      }
      else
      {
        if (left != null)
        {
          left.insert(object) ;
        }
        else
        {
          left = new TreeNode<E>(object,null,null) ;
        }
      }
    }

    /**
     * Find an object in the tree. Objects are compared using the compareTo method, so
     * must conform to type Comparable.
     * Two objects are equal if they represent the same value according to the compareTo method.
     *
     * @param value Object representing value to find in tree.
     * @return  reference to matching node or null.
     */
    public TreeNode<E> find(final E value)
    {
      int temp = this.value.compareTo(value) ;
      if (temp == 0)
      {
        return this ;
      }
      if (temp < 0)
      {
        return (right == null) ? null : right.find(value) ;
      }
      return (left == null) ? null : left.find(value) ;
    }

    /**
     * Remove the node referencing an object representing the same value as the argument object.
     * This recursive method essentially restructures the tree as necessary and returns a
     * reference to the new root. The algorithm is straightforward apart from the case
     * where the node to be removed has two children. In that case the left-most leaf node
     * of the right child is moved up the tree to replace the removed node. Hand-evaluate some
     * examples to see how this method works.
     *
     * @param value Object representing value to remove from tree.
     * @param node Root node of the sub-tree currently being examined (possibly null).
     * @return reference to the (possibly new) root node of the sub-tree being examined or
     * null if no node.
     */
    private TreeNode<E> remove(final E value, TreeNode<E> node)
    {
      if (node == null)
      {
        return null;
      }
      if (value.compareTo(node.value) < 0)
      {
        node.left = remove(value,node.left);
      }
      else
      if (value.compareTo(node.value) > 0 )
      {
        node.right = remove(value, node.right);
      }
      else
      if (node.left != null && node.right != null)
      {
        node.value = findMin(node.right).value;
        node.right = remove(node.value,node.right);
      }
      else
      {
        node = (node.left != null) ? node.left : node.right;
      }
      return node;
    }

    /**
     * Helper method to find the left most leaf node in a sub-tree.
     *
     * @param node TreeNode to be examined.
     * @return reference to left most leaf node or null.
     */
    private TreeNode<E> findMin(final TreeNode<E> node)
    {
      if(node.left == null)

      {
        return node;
      }
      return findMin(node.left);
    }
  }

  /**
   * Construct an empty tree.
   */
  public BinaryTree()
  {
    root = null ;
  }

  /**
   * Store a value in the tree. The object representing the value must conform to type Comparable
   * in order to be inserted in the correct location. Multiple objects representing the
   * same value can be added.
   *
   * @param value reference to Comparable object representing value to add.
   */
  public void add(final T value)
  {
    if (root == null)
    {
      root = new TreeNode<T>(value,null,null) ;
    }
    else
    {
      root.insert(value) ;
    }
  }

  /**
   * Determine whether the tree contains an object with the same value as the
   * argument.
   *
   * @param value reference to Comparable object whose value will be searched for.
   * @return true if the value is found, false otherwise.
   */
  public boolean contains(final T value)
  {
    if (root == null)
    {
      return false ;
    }
    else
    {
      return (root.find(value) != null) ;
    }
  }

  /**
   * Remove an object with a matching value from the tree. If multiple
   * matches are possible, only the first matching object is removed.
   *
   * @param value Remove an object with a matching value from the tree.
   */
  public void remove(final T value)
  {
    if ((root != null) && (value != null))
    {
      root = root.remove(value,root) ;
    }
  }

  /**
   * Simple in-order iterator class. An iterator object will sequence through
   * the values stored in the tree in ascending order.
   * A stack is used to keep track of where the iteration has reached in the tree.
   * Note that if new items are added or removed during an iteration, there is no
   * guarantee that the iteration will continue correctly.
   */
  private class InOrderIterator implements Iterator<T>
  {
    private Stack<TreeNode<T>> nodes = new Stack<>() ;

    /**
     * Construct a new iterator for the current tree object.
     */
    public InOrderIterator()
    {
      pushLeft(root) ;
    }

    /**
     * Get next object in sequence.
     *
     * @return next object in sequence or null if the end of the sequence has
     * been reached.
     */
    public T next()
    {
      if (nodes.isEmpty())
      {
        return null ;
      }
      TreeNode<T> node = nodes.pop() ;
      pushLeft(node.right) ;
      return node.value;
    }

    /**
     * Determine if there is another object in the iteration sequence.
     *
     * @return true if another object is available in the sequence.
     */
    public boolean hasNext()
    {
      return !nodes.isEmpty() ;
    }

    /**
     * Helper method used to push node objects onto the stack to keep
     * track of the iteration.
     */
    private void pushLeft(TreeNode<T> node)
    {
      while (node != null)
      {
        nodes.push(node) ;
        node = node.left ;
      }
    }
  }

  /**
   * Return a new tree iterator object.
   *
   * @return new iterator object.
   */
  public Iterator<T> iterator()
  {
    return new InOrderIterator() ;
  }
}

