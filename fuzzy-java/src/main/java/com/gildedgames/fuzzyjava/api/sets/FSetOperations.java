package com.gildedgames.fuzzyjava.api.sets;

import java.util.Collection;

import com.gildedgames.fuzzyjava.api.sets.relations.FRelation;

public interface FSetOperations
{
	/**
	 * Returns the complement of the set. The implementation
	 * of the complement can vary, but is usually the same 
	 * objects with membership: 1 - membership
	 */
	<E> FSet<E> complement(FSet<E> set);

	/**
	 * Returns the union between this set and the given.
	 * The implementation of the union can differ, but is
	 * usually the maximum value of the membership value
	 * of each element.
	 */
	<E> FSet<E> union(FSet<E> set1, FSet<? extends E> set2);

	/**
	 * Returns the intersection between this set and the given.
	 * The implementation of the intersection can differ, but is
	 * usually the minimum value of the membership value of
	 * each element.
	 */
	<E> FSet<E> intersection(FSet<E> set1, FSet<? extends E> set2);

	/**
	 * Does the fuzzy cartesian product between two given sets
	 * and returns the relationship.
	 * The strength of the relation is usually defined as the minimum 
	 * of the membership of the two elements.
	 */
	<T1, T2> FRelation<T1, T2> cartesian(FSet<T1> set1, FSet<T2> set2);

	<E> Collection<E> alphaCut(FSet<E> set, float alpha);

	<E> Collection<E> cutAllZero(FSet<E> set);

	<E> float getHeight(FSet<E> set);

	/**
	 * Returns true if set1 contains set2. Note that this uses set2 <= set1
	 */
	<E> boolean contains(FSet<E> set1, FSet<? extends E> set2);
}
