package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;

public class FuncCons<E> implements FFuncCons<E>
{
	private final FFuncProp<E> func;

	private final Variable[] variables;

	public FuncCons(FFuncProp<E> func, Variable[] variables)
	{
		this.func = func;
		this.variables = variables;
	}

	@Override
	public float membershipOfFloat(float element)
	{
		return this.func.membershipOfFloat(element);
	}

	@Override
	public float membershipOf(Object[] element)
	{
		return this.func.membershipOf(element);
	}

	@Override
	public IProperty<E> getProperty()
	{
		return this.func.getProperty();
	}

	@Override
	public Variable[] variables()
	{
		return this.variables;
	}

}
