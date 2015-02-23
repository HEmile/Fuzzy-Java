package com.gildedgames.fuzzyjava.api.evaluation;

public interface IRule<E>
{
	/**
	 * Evaluate the proposition in the 
	 * antecedent with the given environment.
	 * Be careful to set your envs up right!
	 * 
	 * Returns a float that represents
	 * the Truth value of the proposition
	 * given the environment.
	 */
	float evaluateAntecedent(E parameter);

	FFuncAntecedent<E> getAntecedent();

	FFuncConsequent<E> getConsequent();
}
