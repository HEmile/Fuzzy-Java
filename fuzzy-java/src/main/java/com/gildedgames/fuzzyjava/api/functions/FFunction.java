package com.gildedgames.fuzzyjava.api.functions;

public interface FFunction<E>
{
	/**
	 * Returns the membership value of the given object.
	 * If the object is not in the working set, this will return
	 * 0.0f
	 */
	float membershipOf(E element);
}
