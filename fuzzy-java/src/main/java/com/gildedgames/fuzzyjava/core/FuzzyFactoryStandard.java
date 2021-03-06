package com.gildedgames.fuzzyjava.core;

import java.util.Collection;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
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
import com.gildedgames.fuzzyjava.core.evaluation.RelationProperty;
import com.gildedgames.fuzzyjava.core.evaluation.RuleBuilder;
import com.gildedgames.fuzzyjava.core.evaluation.RuleSet;
import com.gildedgames.fuzzyjava.core.evaluation.SetProperty;
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
		return new HashFSetMut<>();
	}

	@Override
	public <E> FSetMut<E> createMutableSet(int capacity)
	{
		return new HashFSetMut<>(capacity);
	}

	@Override
	public <E> FSetMut<E> createMutableSet(FSetMut<E> set)
	{
		return new HashFSetMut<>(set);
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation()
	{
		return new HashFRelation<>();
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation(int capacity)
	{
		return new HashFRelation<>(capacity);
	}

	@Override
	public <T1, T2> FRelationMut<T1, T2> createMutableRelation(FRelationSet<? extends T1, ? extends T2> relation)
	{
		return new HashFRelation<>(relation);
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
		return new HashFVector<>(set);
	}

	@Override
	public FFunctionOps createFunctionOperations()
	{
		return new StandardFunctionOps();
	}

	@Override
	public <E> IRuleSet<E> createRuleSet(Collection<E> universe)
	{
		return new RuleSet<>(universe);
	}

	@Override
	public IRuleBuilder createPropFunctionBuilder()
	{
		return new RuleBuilder();
	}

	@Override
	public <E> ISetProperty<E> createSetProp(String name)
	{
		return new SetProperty<E>(name);
	}

	@Override
	public <E> IRelationProperty<E> createRelationProp(String name)
	{
		return new RelationProperty(name);
	}

}
