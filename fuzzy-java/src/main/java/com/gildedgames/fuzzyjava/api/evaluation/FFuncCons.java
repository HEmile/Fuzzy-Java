package com.gildedgames.fuzzyjava.api.evaluation;

public interface FFuncCons<E> extends FContFunc<E>
{
	IProperty<E> getProperty();

	Variable[] variables();
}
