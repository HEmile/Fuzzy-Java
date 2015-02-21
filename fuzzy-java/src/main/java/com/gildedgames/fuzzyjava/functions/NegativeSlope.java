package com.gildedgames.fuzzyjava.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public class NegativeSlope implements FFunction<Float>
{
	private final float rightPoint, leftpoint, interp;

	public NegativeSlope(float leftPoint, float tail)
	{
		this.rightPoint = leftPoint + tail;
		this.leftpoint = leftPoint;
		this.interp = 1 / tail;
	}

	@Override
	public float membershipOf(Float element)
	{
		if (element >= this.rightPoint)
		{
			return 0.0f;
		}
		if (element > this.leftpoint)
		{
			return 1 - (element - this.leftpoint) * this.interp;
		}
		return 1.0f;
	}
}
