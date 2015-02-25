package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.core.Ops;

public class Not<E> implements FFunction<E>
{

	private final FFunction<E> function;

	public Not(FFunction<E> function)
	{
		this.function = function;
	}

	@Override
	public float membershipOf(E element)
	{
		return Ops.not(this.function.membershipOf(element));
	}

}
