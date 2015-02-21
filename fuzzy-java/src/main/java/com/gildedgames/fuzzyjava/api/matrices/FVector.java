package com.gildedgames.fuzzyjava.api.matrices;

import com.gildedgames.fuzzyjava.api.sets.FSet;

/**
 * A fuzzy set that is an vector from 0 to length().
 * elementsize() returns the amount of members with
 * membership value > 0.0f
 * @author Emile
 *
 */
public interface FVector extends FSet<Integer>
{
	int length();

	float set(int index, float value);

	void clear();
}
