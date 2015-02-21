package com.gildedgames.fuzzyjava.api;

import com.gildedgames.fuzzyjava.api.matrices.FMatrixOperations;
import com.gildedgames.fuzzyjava.api.matrices.FVector;
import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;

/**
 * Has a set of creation methods that bundle together
 * implementations of fuzzy logic. 
 * @author Emile
 *
 */
public interface FuzzyFactory
{

	<E> FSetDiscr<E> createDiscreteSet();

	<E> FSetDiscr<E> createDiscreteSet(int capacity);

	<E> FSetDiscr<E> createDiscreteSet(FSetDiscr<E> set);

	<T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation();

	<T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation(int capacity);

	<T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation(FRelationSet<? extends T1, ? extends T2> relation);

	FVector createVector(int length);

	FVector createVector(float[] array);

	FSetOperations createSetOperations();

	FRelationOperations createRelationOperations();

	FMatrixOperations createVectorOperations();

}
