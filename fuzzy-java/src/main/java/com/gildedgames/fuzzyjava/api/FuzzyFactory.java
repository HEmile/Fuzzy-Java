package com.gildedgames.fuzzyjava.api;

import com.gildedgames.fuzzyjava.api.functions.FFunctionOperations;
import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
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

	<E> FSetMut<E> createMutableSet();

	<E> FSetMut<E> createMutableSet(int capacity);

	<E> FSetMut<E> createMutableSet(FSetMut<E> set);

	<T1, T2> FRelationMut<T1, T2> createMutableRelation();

	<T1, T2> FRelationMut<T1, T2> createMutableRelation(int capacity);

	<T1, T2> FRelationMut<T1, T2> createMutableRelation(FRelationSet<? extends T1, ? extends T2> relation);

	FSet<Integer> createVector(float[] array);

	<E> FSet<E> createVector(FSet<E> set);

	FSetOperations createSetOperations();

	FRelationOperations createRelationOperations();

	FFunctionOperations createFunctionOperations();

}
