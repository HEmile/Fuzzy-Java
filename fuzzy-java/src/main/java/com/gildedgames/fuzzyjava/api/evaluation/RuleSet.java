package com.gildedgames.fuzzyjava.api.evaluation;

import java.util.Collection;
import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.evaluation.proposition.IVariable;

public interface RuleSet
{
	void addRule(IRule rule);

	/**
	 * Find the value of the 
	 * parameter given an 
	 * environment of
	 * variables that are applied
	 * to the rules. This will
	 * then find truth values
	 * which are applied to the
	 * consequents of the rules
	 * which contain the paramater
	 * of the name searching.
	 */
	float evaluate(String searching, Collection<IVariable<?>> environment);

	/**
	 * Find the value of
	 * as many parameters
	 * as possible by using
	 * the rules in the set
	 * to find what can be
	 * inferred from the 
	 * given environment.
	 */
	Collection<Entry<String, Float>> tryEvaluateEverything(Collection<IVariable<?>> environment);
}
