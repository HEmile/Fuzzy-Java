package com.gildedgames.fuzzyjava.api.evaluation;

public interface IProperty<E>
{
	float getMinBound();

	float getMaxBound();

	float convert(Object... params);

	boolean propertySet(Object... params);

	void setProperty(float value, Object... params);

	Object[] tryInferMissing(Object[] params);

	String getName();

	int arity();
}
