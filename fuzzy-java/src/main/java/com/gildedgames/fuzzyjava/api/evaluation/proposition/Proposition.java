package com.gildedgames.fuzzyjava.api.evaluation.proposition;

import java.util.Collection;
import java.util.Map;

public interface Proposition
{
	/**
	 * Evaluate the proposition with the 
	 * given environment.
	 * Be careful to set your envs up right!
	 * 
	 * @Return a float that represents
	 * the Truth value of the proposition
	 * given the environment.
	 */
	float evaluate(Map<String, Object> environment);

	Collection<String> parameters();

}
