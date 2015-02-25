package com.gildedgames.fuzzyjava.core;

public class Ops
{
	public static float and(float f1, float f2)
	{
		return Math.min(f1, f2);
	}

	public static float or(float f1, float f2)
	{
		return Math.max(f1, f2);
	}

	public static float not(float f)
	{
		return 1.0f - f;
	}

}
