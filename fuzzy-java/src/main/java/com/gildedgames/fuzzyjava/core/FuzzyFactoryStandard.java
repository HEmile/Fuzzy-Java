package com.gildedgames.fuzzyjava.core;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOperations;
import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.functions.StandardFunctionOperations;
import com.gildedgames.fuzzyjava.core.matrices.ArrayFVector;
import com.gildedgames.fuzzyjava.core.matrices.HashFVector;
import com.gildedgames.fuzzyjava.core.sets.HashFSetDiscrete;
import com.gildedgames.fuzzyjava.core.sets.StandardSetOperations;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;
import com.gildedgames.fuzzyjava.core.sets.relations.StandardRelationOperations;

public class FuzzyFactoryStandard implements FuzzyFactory
{

	@Override
	public <E> FSetMut<E> createMutableSet()
	{
		return new HashFSetDiscrete<E>();
	}

	@Override
	public <E> FSetMut<E> createMutableSet(int capacity)
	{
		return new HashFSetDiscrete<E>(capacity);
	}

	@Override
	public <E> FSetMut<E> createMutableSet(FSetMut<E> set)
	{
		return new HashFSetDiscrete<E>(set);
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation()
	{
		return new HashFRelation<T1, T2>();
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation(int capacity)
	{
		return new HashFRelation<T1, T2>(capacity);
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation(FRelationSet<? extends T1, ? extends T2> relation)
	{
		return new HashFRelation<T1, T2>(relation);
	}

	@Override
	public FSetOperations createSetOperations()
	{
		return new StandardSetOperations();
	}

	@Override
	public FRelationOperations createRelationOperations()
	{
		return new StandardRelationOperations();
	}

	@Override
	public FSet<Integer> createVector(float[] array)
	{
		return new ArrayFVector(array);
	}

	@Override
	public <E> FSet<E> createVector(FSet<E> set)
	{
		return new HashFVector<E>(set);
	}

	@Override
	public FFunctionOperations createFunctionOperations()
	{
		return new StandardFunctionOperations();
	}

}
