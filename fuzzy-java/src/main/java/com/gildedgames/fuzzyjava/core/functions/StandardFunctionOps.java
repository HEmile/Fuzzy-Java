package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOps;

public class StandardFunctionOps implements FFunctionOps
{

	/**
	 * Denotes how many samples the defuzzify
	 * algorithm will take. Higher means better
	 * results but worse performance.
	 * Experiment with this!
	 */
	public static int defuzzifySamples = 1000;

	@Override
	public <E> FFunction<E> and(FFunction<E> function1, FFunction<E> function2)
	{
		return new And<>(function1, function2);
	}

	@Override
	public <E> FFunction<E> or(FFunction<E> function1, FFunction<E> function2)
	{
		return new Or<>(function1, function2);
	}

	@Override
	public <E> FFunction<E> implies(FFunction<E> function1, FFunction<E> function2)
	{
		return new Implies<>(function1, function2);
	}

	@Override
	public <E> FFunction<E> not(FFunction<E> function)
	{
		return new Not<>(function);
	}

	@Override
	public <E> FFunction<E> cut(FFunction<E> function, float cutHeight)
	{
		return new Cut<>(function, cutHeight);
	}

	/**
	 * Computes the center of gravity of the
	 * function.
	 */
	@Override
	public float defuzzify(FFunction<Float> function, float leftBound, float rightBound)
	{
		final float dx = (rightBound - leftBound) / defuzzifySamples;
		float x = leftBound;
		float sum1 = 0, sum2 = 0;
		for (int i = 0; i < defuzzifySamples; i++)
		{
			final float membership = function.membershipOf(x) * dx;
			sum1 += membership * x;
			sum2 += membership;
			x += dx;
		}
		return sum1 / sum2;
	}

}
