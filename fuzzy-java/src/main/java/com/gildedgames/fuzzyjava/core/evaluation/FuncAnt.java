package com.gildedgames.fuzzyjava.core.evaluation;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.util.Pair;

public class FuncAnt<E> implements FFuncAnt<E>
{

	private final Parameter[] parameters;

	private final FFuncProp<E> propFunc;

	public FuncAnt(FFuncProp<E> propFunc, Parameter... parameters)
	{
		this.parameters = parameters;
		this.propFunc = propFunc;
	}

	@Override
	public float membershipOf(Object[] element)
	{
		return this.propFunc.membershipOf(element);
	}

	@Override
	public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
	{
		final Set<Entry<IProperty<E>, Parameter[]>> set = new HashSet<Entry<IProperty<E>, Parameter[]>>(1);
		set.add(new Pair<IProperty<E>, Parameter[]>(this.propFunc.getProperty(), this.parameters));
		return set;
	}

	@Override
	public float evaluate(Map<Variable, ?> env)
	{
		final Object[] els = new Object[this.parameters.length];
		for (int i = 0; i < this.parameters.length; i++)
		{
			els[i] = this.parameters[i].getValue(env);
		}
		return this.membershipOf(els);
	}

}
