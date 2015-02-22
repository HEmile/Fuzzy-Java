package com.gildedgames.fuzzyjava.api.evaluation;

import java.util.Collection;
import java.util.Map;

import com.gildedgames.fuzzyjava.api.evaluation.proposition.IVariable;
import com.gildedgames.fuzzyjava.api.evaluation.proposition.Proposition;
import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IRule
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
	float evaluateAntecedent(Collection<IVariable<?>> environment);

	/**
	 * Evaluate the consequent given the  
	 * truth value of the result. 
	 * 
	 * Returns a map from variable name
	 * to function float -> truth.
	 * 
	 * This function is the membership
	 * function for the different 
	 * consequents.
	 */
	Map<FFunction<Float>, String> evaluateConsequents(float truth);

	Collection<String> variablesInAntecedent();

	/**
	 * Returns the variable names used
	 * in the consequent of the rule.
	 * Used for backwards chaining
	 */
	Collection<String> variablesInConsequent();

	Proposition getAntecedent();

	Collection<FFunction<Float>> getConsequents();
}
