package com.gildedgames.fuzzyjava.api.sets;

import java.util.Collection;

/**
 * A mutable set where you can freely add and
 * remove elements from it after it is
 * created. 
 * @author Emile
 */
public interface FSetMut<E> extends FSet<E>
{

	/**
	 * Adds the object with the given membership
	 * value. The membership is clamped so that:
	 * 0.0f <= membership <= 1.0f
	 * 
	 * If the object is already in the set it will set
	 * its membership value to the given. 
	 */
	void add(E object, float membership);

	void addAll(FSetMut<? extends E> fuzzySet);

	/**
	 * Adds all elements from the crisp set to
	 * the fuzzy set with membership = 1.0f. Note
	 * that this will override previous membership
	 * mappings for elements already in the fuzzy
	 * set.
	 */
	void addAll(Collection<? extends E> set);

	/**
	 * Removes the object from the set.
	 * @returns The membership value associated  
	 */
	float remove(E object);

	boolean contains(E object);

	/**
	 * Completely empties the set.
	 */
	void clear();

}
