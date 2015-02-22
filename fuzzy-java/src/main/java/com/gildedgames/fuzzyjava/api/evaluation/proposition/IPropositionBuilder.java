package com.gildedgames.fuzzyjava.api.evaluation.proposition;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IPropositionBuilder
{
	Proposition predicate(FFunction<?> function, String parameter);

	Proposition and(Proposition one, Proposition two);

	Proposition or(Proposition one, Proposition two);

	Proposition implies(Proposition one, Proposition two);

	Proposition not(Proposition prop);
}
