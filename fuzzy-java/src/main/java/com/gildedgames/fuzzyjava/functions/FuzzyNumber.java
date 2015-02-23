package com.gildedgames.fuzzyjava.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

/**
 * A fuzzy number function is a function
 * that has membership 1.0 on the value
 * called point, and has tails around it 
 * that linearly go from 0 to that point. 
 * 
 * Tip: Extend this function and implement
 * FCont with the type you want to use
 * it in consequents of rules.
 * @author Emile
 *
 */
public class FuzzyNumber implements FFunction<Float>
{
	private final float point, leftpoint, rightpoint;

	private final float leftInterp, rightInterp;

	/**
	 * Creates a Fuzzy number function with
	 * a symmetric tail.
	 */
	public FuzzyNumber(float point, float tail)
	{
		this(point, tail, tail);
	}

	/**
	 * Creates a Fuzzy number function with
	 * different tails left and right.
	 */
	public FuzzyNumber(float point, float leftTail, float rightTail)
	{
		this.point = point;
		this.rightpoint = point + rightTail;
		this.leftInterp = 1 / leftTail;
		this.rightInterp = 1 / rightTail;
		this.leftpoint = point - leftTail;
	}

	@Override
	public float membershipOf(Float element)
	{
		if (element == this.point)
		{
			return 1.0f;
		}
		if (element < this.point && element > this.leftpoint)
		{
			return (element - this.leftpoint) * this.leftInterp;
		}
		else if (element > this.point && element < this.rightpoint)
		{
			return 1 - (element - this.point) * this.rightInterp;
		}
		return 0;
	}
}
