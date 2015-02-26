package com.gildedgames.rules.properties;

import java.util.Random;

import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.rules.Entity;

public abstract class BaseProp implements IProperty<Entity>
{
	protected abstract float convert(Entity el);

	@Override
	public float convert(Object... params)
	{
		return this.convert((Entity) params[0]);
	}

	@Override
	public int arity()
	{
		return 1;
	}

	@Override
	public boolean propertySet(Object... params)
	{
		return this.convert((Entity) params[0]) != -1;
	}

	protected abstract void setProperty(Entity el, float value);

	@Override
	public void setProperty(float value, Object... params)
	{
		this.setProperty((Entity) params[0], value);
	}

	@Override
	public Object[] tryInferMissing(Object[] params)
	{
		return params;
	}

	public float randomValue(Random r)
	{
		final float newFloat = r.nextFloat();
		return this.getMinBound() + (this.getMaxBound() - this.getMinBound()) * newFloat;
	}

}
