package com.gildedgames.fuzzyjava.api.functions;


public interface FFunctionOps
{
	<E> FFunction<E> and(FFunction<E> function1, FFunction<E> function2);

	<E> FFunction<E> or(FFunction<E> function1, FFunction<E> function2);

	<E> FFunction<E> implies(FFunction<E> function1, FFunction<E> function2);

	<E> FFunction<E> not(FFunction<E> function);

	<E> FFunction<E> cut(FFunction<E> function, float cutHeight);

	float defuzzify(FFunction<Float> function, float leftBound, float rightBound);
}
