package com.gildedgames.fuzzyjava.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

/**
 * 
 * @author Emile
 *
 */
public class PositiveSlope implements FFunction<Float>
{

	private final float rightPoint, leftpoint, interp;

	public PositiveSlope(float rightPoint, float tail)
	{
		this.rightPoint = rightPoint;
		this.leftpoint = rightPoint - tail;
		this.interp = 1 / tail;
	}

	@Override
	public float membershipOf(Float element)
	{
		if (element >= this.rightPoint)
		{
			return 1.0f;
		}
		if (element > this.leftpoint)
		{
			return (element - this.leftpoint) * this.interp;
		}
		return 0.0f;
	}

}
