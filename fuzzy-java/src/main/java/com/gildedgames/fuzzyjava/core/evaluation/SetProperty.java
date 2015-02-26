package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.ISetProperty;
import com.gildedgames.fuzzyjava.core.sets.HashFSetMut;

public class SetProperty<E> extends HashFSetMut<E> implements ISetProperty<E>
{
	private final String name;

	public SetProperty(String name)
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
		return this.membershipOf(params[0]);
	}

	@Override
	public boolean propertySet(Object... params)
	{
		return this.contains((E) params[0]);
	}

	@Override
	public void setProperty(float value, Object... params)
	{
		this.add((E) params[0], value);
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
		return 1;
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
