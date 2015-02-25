package com.gildedgames.fuzzyjava.api.evaluation;

public interface FFuncProp<E> extends FContFunc<E>
{
	IProperty<E> getProperty();

	@SuppressWarnings("unchecked")
	FFuncAnt<E> ant(Parameter... parameters);

	@SuppressWarnings("unchecked")
	FFuncCons<E> cons(Variable... parameters);
}
