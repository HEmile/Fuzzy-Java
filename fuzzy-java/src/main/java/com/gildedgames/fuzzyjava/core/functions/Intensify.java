package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

/**
 * Intensification algorithm as
 * proposed by Zadeh (1972).
 * @author Emile
 */
public class Intensify<E> implements FFunction<E>
{
	FFunction<E> function;

	float exponent, scalar;

	/**
	 * Intensifies the given function to 
	 * the given degree. An intensification
	 * is an algorithm for having values
	 * with bigger contrast, at least for
	 * degree > 1. 
	 * This means that a value of for
	 * example 0.3 will be much closer
	 * to 0 for higher degrees, and a 
	 * value of 0.8 will be closer to 1.0
	 * @param function
	 * @param degree
	 */
	public Intensify(FFunction<E> function, float degree)
	{
		this.function = function;
		this.exponent = degree;
		this.scalar = (float) Math.pow(2, degree - 1);
	}

	@Override
	public float membershipOf(E element)
	{
		final float membership = this.function.membershipOf(element);
		if (membership > 0.5f)
		{
			return (float) (1 - this.scalar * Math.pow(1 - membership, this.exponent));
		}
		return (float) (this.scalar * Math.pow(membership, this.exponent));
	}

}
