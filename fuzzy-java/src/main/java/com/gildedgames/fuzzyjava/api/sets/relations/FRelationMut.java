package com.gildedgames.fuzzyjava.api.sets.relations;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.FSetMut;

/**
 * A relationship that is mutable.
 * Can be used to setup new 
 * relationships.
 * @author Emile
 */
public interface FRelationMut<T1, T2> extends FRelationSet<T1, T2>, FSetMut<Entry<T1, T2>>
{
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
}
