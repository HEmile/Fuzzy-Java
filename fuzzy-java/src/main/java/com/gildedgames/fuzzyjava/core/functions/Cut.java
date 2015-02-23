package com.gildedgames.fuzzyjava.core.functions;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public class Cut<E> implements FFunction<E>
{

	private final FFunction<E> function;

	private final float cutHeight;

	public Cut(FFunction<E> function, float cutHeight)
	{
		this.function = function;
		this.cutHeight = cutHeight;
	}

	@Override
	public float membershipOf(E element)
	{
		return Math.min(this.function.membershipOf(element), this.cutHeight);
	}

}
