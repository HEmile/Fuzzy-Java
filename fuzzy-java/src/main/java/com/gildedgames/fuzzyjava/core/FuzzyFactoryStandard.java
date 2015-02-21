package com.gildedgames.fuzzyjava.core;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
import com.gildedgames.fuzzyjava.api.matrices.FMatrixOperations;
import com.gildedgames.fuzzyjava.api.matrices.FVector;
import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.matrices.ArrayFVector;
import com.gildedgames.fuzzyjava.core.matrices.StandardMatrixOperations;
import com.gildedgames.fuzzyjava.core.sets.HashFSetDiscrete;
import com.gildedgames.fuzzyjava.core.sets.StandardSetOperations;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;
import com.gildedgames.fuzzyjava.core.sets.relations.StandardRelationOperations;

public class FuzzyFactoryStandard implements FuzzyFactory
{

	@Override
	public <E> FSetDiscr<E> createDiscreteSet()
	{
		return new HashFSetDiscrete<E>();
	}

	@Override
	public <E> FSetDiscr<E> createDiscreteSet(int capacity)
	{
		return new HashFSetDiscrete<E>(capacity);
	}

	@Override
	public <E> FSetDiscr<E> createDiscreteSet(FSetDiscr<E> set)
	{
		return new HashFSetDiscrete<E>(set);
	}

	@Override
	public <T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation()
	{
		return new HashFRelation<T1, T2>();
	}

	@Override
	public <T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation(int capacity)
	{
		return new HashFRelation<T1, T2>(capacity);
	}

	@Override
	public <T1, T2> FRelationDiscr<T1, T2> createDiscreteRelation(FRelationSet<? extends T1, ? extends T2> relation)
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
	public FVector createVector(int length)
	{
		return new ArrayFVector(length);
	}

	@Override
	public FVector createVector(float[] array)
	{
		return new ArrayFVector(array);
	}

	@Override
	public FMatrixOperations createVectorOperations()
	{
		return new StandardMatrixOperations();
	}

}
