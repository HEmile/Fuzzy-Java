package com.gildedgames.fuzzyjava.api.evaluation;

public interface FFuncProp<E> extends FContFunc<E>
{
	IProperty<E> getProperty();

	@SuppressWarnings("unchecked")
	FFuncAnt<E> a(Parameter... parameters);

	@SuppressWarnings("unchecked")
	FFuncCons<E> c(Variable... parameters);
}
