package com.gildedgames.fuzzyjava.core.interfaces;

import java.util.Set;

/**
 * Denotes a relation between two universes, which contain
 * elements of type T1 and T2 respectively.
 * @author Emile
 */
public interface FRelation<T1, T2>
{
	/**
	 * Returns the amount of elements in the set
	 */
	int elementSize();

	/**
	 * Adds a relation with the given membership
	 * value. The membership is clamped so that:
	 * 0.0f <= membership <= 1.0f
	 * 
	 * If the object is already in the set it will set
	 * its membership value to the given. 
	 */
	void add(T1 element1, T2 element2, float membership);

	/**
	 * Removes a relation from the set.
	 * @returns The membership value associated  
	 */
	float remove(T1 element1, T2 element2);

	/**
	 * Returns the membership value of the given relationship.
	 * If the object is not in the set, this will return
	 * 0.0f
	 */
	float strengthOfRelation(Object element1, Object element2);

	/**
	 *  Returns a set of mapentries from object to float,
	 *  where the float is the membership of the object
	 */
	Set<FRelationEntry<T1, T2>> entrySet();

	/**
	 * Completely empties the set.
	 */
	void clear();
}
