package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.IRule;

public class Rule<I, O> implements IRule<I, O>
{
	private final FFuncAnt<I> antecedent;

	private final FFuncCons<O> consequent;

	public Rule(FFuncAnt<I> antecedent, FFuncCons<O> consequent)
	{
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	@Override
	public FFuncAnt<I> getAntecedent()
	{
		return this.antecedent;
	}

	@Override
	public FFuncCons<O> getConsequent()
	{
		return this.consequent;
	}

}
