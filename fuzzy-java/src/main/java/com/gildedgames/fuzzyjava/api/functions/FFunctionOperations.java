package com.gildedgames.fuzzyjava.api.functions;

public interface FFunctionOperations
{
	<E> FFunction<E> and(FFunction<E> function1, FFunction<E> function2);

	<E> FFunction<E> or(FFunction<E> function1, FFunction<E> function2);

	<E> FFunction<E> not(FFunction<E> function);
}
