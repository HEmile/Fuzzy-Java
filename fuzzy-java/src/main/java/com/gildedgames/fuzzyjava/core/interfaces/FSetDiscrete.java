package com.gildedgames.fuzzyjava.core.interfaces;

import java.util.Map.Entry;
import java.util.Set;

public interface FSetDiscrete<E>
{
	/**
	 * Returns the amount of elements in the set
	 */
	int elementSize();

	/**
	 * Adds the object with the given membership
	 * value. The membership is clamped so that:
	 * 0.0f <= membership <= 1.0f
	 * 
	 * If the object is already in the set it will set
	 * its membership value to the given. 
	 */
	void add(E object, float membership);

	/**
	 * Removes the object from the set.
	 * @returns The membership value associated  
	 */
	float remove(E object);

	/**
	 * Returns the membership value of the given object.
	 * If the object is not in the set, this will return
	 * 0.0f
	 */
	float membershipOf(Object object);

	/**
	 *  Returns a set of mapentries from object to float,
	 *  where the float is the membership of the object
	 */
	Set<Entry<E, Float>> entrySet();

	/**
	 * Returns all objects in the set even if their membership
	 * is 0.0
	 */
	Set<E> objects();

	/**
	 * Completely empties the set.
	 */
	void clear();

}
