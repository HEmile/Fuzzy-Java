package com.gildedgames.fuzzyjava.api.evaluation.proposition;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IPredicate<E> extends Proposition, FFunction<E>
{
	String getParameterName();
}
