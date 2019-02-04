/**
 * General interface for trees.
 * Copyright (c) 2019
 * Dept. of Computer Science, University College London
 * @author Graham Roberts
 */
package uk.ac.ucl.datastructures;

import java.util.*;

public interface Tree<T>
{
  /**
   * Store an object in the tree. The object must conform to type Comparable
   * in order to be inserted in the correct location. Multiple objects representing the
   * same value can be added.
   *
   * @param value reference to Comparable object to add.
   */
  void add(final T value);

  /**
   * Determine whether the tree contains an object with the same value as the
   * argument.
   *
   * @param value reference to Comparable object whose value will be searched for.
   * @return true if the value is found.
   */
  boolean contains(final T value);

  /**
   * Remove an object with a matching value from the tree. If multiple
   * matches are possible, only the first matching object is removed.
   *
   * @param value Remove an object with a matching value from the tree.
   */
  void remove(final T value);

  /**
   * Return a new in-order tree iterator object.
   *
   * @return new in-order iterator object.
   */
  Iterator<T> iterator();
}
