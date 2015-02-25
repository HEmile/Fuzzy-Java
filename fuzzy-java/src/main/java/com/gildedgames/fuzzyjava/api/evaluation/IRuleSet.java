package com.gildedgames.fuzzyjava.api.evaluation;

public interface IRuleSet<E>
{
	void addRule(FFuncAnt<E> antecedent, FFuncCons<E> consequent);

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
	float valueOf(IProperty<E> searching, Object... parameters);

	Object[] missing(FFuncProp<E> func, Object... parameters);

}
