package com.gildedgames.fuzzyjava.api.evaluation;

public interface IProperty<E>
{
	float getMinBound();

	float getMaxBound();

	float convert(E element);

	String getName();
}
