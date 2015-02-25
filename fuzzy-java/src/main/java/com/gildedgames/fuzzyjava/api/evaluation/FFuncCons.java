package com.gildedgames.fuzzyjava.api.evaluation;

public interface FFuncCons<E> extends FContFunc<E>
{
	FFuncProp<E> getPropFunc();

	Variable[] variables();
}
