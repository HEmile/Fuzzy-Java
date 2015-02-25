package com.gildedgames.fuzzyjava.core;

import java.util.Collection;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
import com.gildedgames.fuzzyjava.api.evaluation.IPropBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOps;
import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOps;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOps;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.evaluation.FuncPropBuilder;
import com.gildedgames.fuzzyjava.core.evaluation.RuleSet;
import com.gildedgames.fuzzyjava.core.functions.StandardFunctionOps;
import com.gildedgames.fuzzyjava.core.matrices.ArrayFVector;
import com.gildedgames.fuzzyjava.core.matrices.HashFVector;
import com.gildedgames.fuzzyjava.core.sets.HashFSetMut;
import com.gildedgames.fuzzyjava.core.sets.StandardSetOperations;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;
import com.gildedgames.fuzzyjava.core.sets.relations.StandardRelationOperations;

public class FuzzyFactoryStandard implements FuzzyFactory
{

	@Override
	public <E> FSetMut<E> createMutableSet()
	{
		return new HashFSetMut<E>();
	}

	@Override
	public <E> FSetMut<E> createMutableSet(int capacity)
	{
		return new HashFSetMut<E>(capacity);
	}

	@Override
	public <E> FSetMut<E> createMutableSet(FSetMut<E> set)
	{
		return new HashFSetMut<E>(set);
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
	public FSetOps createSetOperations()
	{
		return new StandardSetOperations();
	}

	@Override
	public FRelationOps createRelationOperations()
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
	public FFunctionOps createFunctionOperations()
	{
		return new StandardFunctionOps();
	}

	@Override
	public <E> IRuleSet<E> createRuleSet(Collection<E> universe)
	{
		return new RuleSet<E>(universe);
	}

	@Override
	public IPropBuilder createPropFunctionBuilder()
	{
		return new FuncPropBuilder();
	}

}
