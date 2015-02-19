package com.gildedgames.fuzzyjava.core.interfaces;

public interface FSetOperations
{
	/**
	 * Returns the complement of the set. The implementation
	 * of the complement can vary, but is usually the same 
	 * objects with membership: 1 - membership
	 */
	<E> FSetDiscrete<E> complement(FSetDiscrete<E> set);

	/**
	 * Returns the union between this set and the given.
	 * The implementation of the union can differ, but is
	 * usually the maximum value of the membership value
	 * of each element.
	 */
	<E> FSetDiscrete<E> union(FSetDiscrete<E> set1, FSetDiscrete<? extends E> set2);

	/**
	 * Returns the intersection between this set and the given.
	 * The implementation of the intersection can differ, but is
	 * usually the minimum value of the membership value of
	 * each element.
	 */
	<E> FSetDiscrete<E> intersection(FSetDiscrete<E> set1, FSetDiscrete<? extends E> set2);

	/**
	 * Does the fuzzy cartesian product between two given sets
	 * and returns the relationship.
	 * The strength of the relation is usually defined as the minimum 
	 * of the membership of the two elements.
	 */
	<T1, T2> FRelation<T1, T2> cartesian(FSetDiscrete<T1> set1, FSetDiscrete<T2> set2);

	/**
	 * Returns true if set1 contains set2. Note that this uses set2 <= set1
	 */
	boolean contains(FSetDiscrete<?> set1, FSetDiscrete<?> set2);
}
