package com.gildedgames.fuzzyjava.api.sets;

import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

/**
 *  A set is an iterator over a set of pairs from 
 *  object to float, where the float is the 
 *  membership of the object.
 *  It should return all elements in the universe
 *  with their membership in the set.
 *  If the set is not aware of all elements in the 
 *  universe, this will return all the elements it
 *  is aware of instead.
 *  
 *  Note that this interface by itself is immutable
 */
public interface FSet<E> extends FFunction<E>, Iterable<Entry<E, Float>>
{
	/**
	 * Returns the amount of elements in the set
	 */
	int size();

	/**
	 * Returns all objects in the set even if their membership
	 * is 0.0
	 */
	Set<E> universe();
}
