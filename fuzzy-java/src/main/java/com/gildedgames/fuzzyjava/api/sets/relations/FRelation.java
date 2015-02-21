package com.gildedgames.fuzzyjava.api.sets.relations;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

/**
 * Denotes a relation between two universes, which contain
 * elements of type T1 and T2 respectively.
 * @author Emile
 */
public interface FRelation<T1, T2> extends FFunction<Entry<T1, T2>>
{

	/**
	 * Returns the membership value of the given relationship.
	 * If the object is not in the set, this will return
	 * 0.0f
	 */
	float strengthOfRelation(T1 element1, T2 element2);

}
