package com.gildedgames.fuzzyjava.api.evaluation;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface FContFunc<E> extends FFunction<Object[]>
{
	float membershipOfFloat(float element);

}
