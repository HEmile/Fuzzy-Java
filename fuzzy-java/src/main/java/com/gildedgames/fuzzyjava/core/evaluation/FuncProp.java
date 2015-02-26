package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.core.evaluation.exceptions.InvalidNumberOfArgumentsException;

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
	public float membershipOfFloat(float element)
	{
		return this.func.membershipOf(element);
	}

	@Override
	public IProperty<E> getProperty()
	{
		return this.property;
	}

	@Override
	public float membershipOf(Object[] element)
	{
		return this.func.membershipOf(this.getProperty().convert(element));
	}

	@Override
	public FFuncAnt<E> a(Parameter... parameters)
	{
		if (parameters.length != this.getProperty().arity())
		{
			throw new InvalidNumberOfArgumentsException();
		}
		return new FuncAnt<>(this, parameters);
	}

	@Override
	public FFuncCons<E> c(Variable... parameters)
	{
		if (parameters.length != this.getProperty().arity())
		{
			throw new InvalidNumberOfArgumentsException();
		}
		return new FuncCons<>(this, parameters);
	}

}
