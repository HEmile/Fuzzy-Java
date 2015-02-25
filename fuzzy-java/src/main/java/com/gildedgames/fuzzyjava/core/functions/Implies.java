package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.core.Ops;

public class Implies<E> implements FFunction<E>
{
	private final FFunction<E> function1, function2;

	public Implies(FFunction<E> function1, FFunction<E> function2)
	{
		this.function1 = function1;
		this.function2 = function2;
	}

	@Override
	public float membershipOf(E element)
	{
		return Ops.or(Ops.not(this.function1.membershipOf(element)), this.function2.membershipOf(element));
	}
}
