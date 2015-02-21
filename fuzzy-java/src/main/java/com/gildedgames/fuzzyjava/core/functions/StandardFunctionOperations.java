package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOperations;

public class StandardFunctionOperations implements FFunctionOperations
{

	@Override
	public <E> FFunction<E> and(FFunction<E> function1, FFunction<E> function2)
	{
		return new And<E>(function1, function2);
	}

	@Override
	public <E> FFunction<E> or(FFunction<E> function1, FFunction<E> function2)
	{
		return new Or<E>(function1, function2);
	}

	@Override
	public <E> FFunction<E> not(FFunction<E> function)
	{
		return new Not<E>(function);
	}

}
