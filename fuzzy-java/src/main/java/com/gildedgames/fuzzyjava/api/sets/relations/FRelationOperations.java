package com.gildedgames.fuzzyjava.api.sets.relations;

public interface FRelationOperations
{

	<T1, T2, T3> FRelationSet<T1, T3> maxMinComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2);

	<T1, T2, T3> FRelationSet<T1, T3> maxProductComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2);

	<E> boolean isReflexive(FRelationSet<E, E> relation);

	<E> boolean isSymmetric(FRelationSet<E, E> relation);

	<E> boolean isTransitive(FRelationSet<E, E> relation);

	<E> boolean isTolerance(FRelationSet<E, E> relation);

	<E> boolean isEquivalence(FRelationSet<E, E> relation);

	/**
	 * Transforms a tolerance relation into
	 * an equivalence relation by repeatedly
	 * applying composition.
	 */
	<E> FRelationSet<E, E> toEquivalence(FRelationSet<E, E> toleranceRelation);

	<T1, T2> FRelation<T1, T1> cosineAmplitude(FRelationSet<T1, T2> matrix);

	<T1, T2> FRelation<T1, T1> maxMin(FRelationSet<T1, T2> matrix);

}
