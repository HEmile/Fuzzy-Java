package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRelationProperty;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;
import com.gildedgames.fuzzyjava.util.Pair;

public class RelationProperty<E> extends HashFRelation<E, E> implements IRelationProperty<E>
{
	private final String name;

	public RelationProperty(String name)
	{
		this.name = name;
	}

	@Override
	public float getMinBound()
	{
		return 0;
	}

	@Override
	public float getMaxBound()
	{
		return 1;
	}

	@Override
	public float convert(Object... params)
	{
		return this.strengthOfRelation(params[0], params[1]);
	}

	@Override
	public boolean propertySet(Object... params)
	{
		return this.contains(new Pair<>((E) params[0], (E) params[1]));
	}

	@Override
	public void setProperty(float value, Object... params)
	{
		this.add((E) params[0], (E) params[1], value);
	}

	@Override
	public Object[] tryInferMissing(Object[] params)
	{
		return params;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public int arity()
	{
		return 2;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (obj instanceof IProperty)
		{
			return ((IProperty) obj).getName().equals(this.getName());
		}
		return false;
	}

}
