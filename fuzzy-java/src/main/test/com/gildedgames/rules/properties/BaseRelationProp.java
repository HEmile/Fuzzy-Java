package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.rules.Entity;

public abstract class BaseRelationProp implements IProperty<Entity>
{

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

	protected abstract float convert(Entity entity1, Entity entity2);

	@Override
	public float convert(Object... params)
	{
		return this.convert((Entity) params[0], (Entity) params[1]);
	}

	protected abstract boolean propertySet(Entity entity1, Entity entity2);

	@Override
	public boolean propertySet(Object... params)
	{
		return this.propertySet((Entity) params[0], (Entity) params[1]);
	}

	protected abstract void setProperty(Entity entity1, Entity entity2, float value);

	@Override
	public void setProperty(float value, Object... params)
	{
		this.setProperty((Entity) params[0], (Entity) params[1], value);
	}

	protected abstract Object[] tryInferMissing(Entity entity1, Entity entity2);

	@Override
	public Object[] tryInferMissing(Object[] params)
	{
		return this.tryInferMissing((Entity) params[0], (Entity) params[1]);
	}

	@Override
	public int arity()
	{
		return 2;
	}

}
