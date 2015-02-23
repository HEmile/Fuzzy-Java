package com.gildedgames.fuzzyjava.api.evaluation;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface FMultiContFunc<E> extends FFunction<Float>
{
	float membershipOfEl(E element);
}
