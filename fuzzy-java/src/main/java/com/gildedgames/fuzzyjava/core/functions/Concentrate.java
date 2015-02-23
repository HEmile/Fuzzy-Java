package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public class Concentrate<E> implements FFunction<E>
{

	private final FFunction<E> function;

	private final float concentration;

	/**
	 * Concentrates the given function.
	 * Concentration is basically taking
	 * the power of the function to the
	 * given concentration, so
	 * function^concentration, with
	 * 0 < concentration.
	 * 
	 * This is useful to model adverbs 
	 * such as "very" (with concentration
	 * is 2), or "slightly" (with 
	 * concentration as 0.5).
	 */
	public Concentrate(FFunction<E> function, float concentration)
	{
		this.function = function;
		this.concentration = concentration;
	}

	@Override
	public float membershipOf(E element)
	{
		return (float) Math.pow(this.function.membershipOf(element), this.concentration);
	}

}
