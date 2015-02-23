package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.functions.FFunction;

public class FuncProp<E> implements FFuncProp<E>
{
	private final IProperty<E> property;

	private final FFunction<Float> func;

	public FuncProp(FFunction<Float> func, IProperty<E> property)
	{
		this.property = property;

		this.func = func;
	}

	@Override
	public float membershipOf(Float element)
	{
		return this.func.membershipOf(element);
	}

	@Override
	public IProperty<E> getProperty()
	{
		return this.property;
	}

	@Override
	public float membershipOfEl(E element)
	{
		return this.func.membershipOf(this.property.convert(element));
	}

}
