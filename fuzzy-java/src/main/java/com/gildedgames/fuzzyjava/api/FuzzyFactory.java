package com.gildedgames.fuzzyjava.api;

import java.util.Collection;

import com.gildedgames.fuzzyjava.api.evaluation.IRelationProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.ISetProperty;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOps;
import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOps;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOps;
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

	FSetOps createSetOperations();

	FRelationOps createRelationOperations();

	FFunctionOps createFunctionOperations();

	IRuleBuilder createPropFunctionBuilder();

	<E> IRuleSet<E> createRuleSet(Collection<E> universe);

	<E> ISetProperty<E> createSetProp(String name);

	<E> IRelationProperty<E> createRelationProp(String name);

}
