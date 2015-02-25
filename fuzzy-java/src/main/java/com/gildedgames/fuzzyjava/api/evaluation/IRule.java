package com.gildedgames.fuzzyjava.api.evaluation;

public interface IRule<I, O>
{

	FFuncAnt<I> getAntecedent();

	FFuncCons<O> getConsequent();
}
