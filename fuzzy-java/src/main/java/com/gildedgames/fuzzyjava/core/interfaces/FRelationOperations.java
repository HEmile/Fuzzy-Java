package com.gildedgames.fuzzyjava.core.interfaces;

public interface FRelationOperations
{
	/**
	 * Returns the complement of the set. The implementation
	 * of the complement can vary, but is usually the same 
	 * objects with membership: 1 - membership
	 */
	<T1, T2> FRelation<T1, T2> complement(FRelation<T1, T2> relation);

	/**
	 * Returns the union between this set and the given.
	 * The implementation of the union can differ, but is
	 * usually the maximum value of the membership value
	 * of each element.
	 */
	<T1, T2> FRelation<T1, T2> union(FRelation<T1, T2> relation1, FRelation<? extends T1, ? extends T2> relation2);

	/**
	 * Returns the intersection between this set and the given.
	 * The implementation of the intersection can differ, but is
	 * usually the minimum value of the membership value of
	 * each element.
	 */
	<T1, T2> FRelation<T1, T2> intersection(FRelation<T1, T2> relation1, FRelation<? extends T1, ? extends T2> relation2);

	<T1, T2, T3> FRelation<T1, T3> maxMinComposition(FRelation<T1, T2> relation1, FRelation<T2, T3> relation2);

	<T1, T2, T3> FRelation<T1, T3> maxProductComposition(FRelation<T1, T2> relation1, FRelation<T2, T3> relation2);

	/**
	 * Returns true if set1 contains set2. Note that this uses set2 <= set1
	 */
	boolean contains(FRelation<?, ?> set1, FRelation<?, ?> set2);
}
