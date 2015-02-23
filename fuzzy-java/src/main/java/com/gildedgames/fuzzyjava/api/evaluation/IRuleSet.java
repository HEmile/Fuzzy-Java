package com.gildedgames.fuzzyjava.api.evaluation;

public interface IRuleSet
{
	<E> void addRule(FFuncAntecedent<E> antecedent, FFuncConsequent<E> consequent);

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
	<E, EI extends E> float valueOf(IProperty<E> searching, EI value);

}
