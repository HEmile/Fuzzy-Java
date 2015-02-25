package com.gildedgames.fuzzyjava.api.evaluation;

import java.util.HashMap;
import java.util.Map;

public class Variable implements Parameter
{

	private final Variable[] variables = new Variable[] { this };

	@Override
	public Variable[] variables()
	{
		return this.variables;
	}

	@Override
	public final Object getValue(Map<Variable, ?> env)
	{
		return env.get(this);
	}

	@Override
	public Map<Variable, ?> tryInferVars(Object paramValue)
	{
		final Map<Variable, Object> set = new HashMap<Variable, Object>(1);
		set.put(this, paramValue);
		return set;
	}
}
