package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAntecedent;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncConsequent;
import com.gildedgames.fuzzyjava.api.evaluation.IRule;

public class Rule<E> implements IRule<E>
{
	private final FFuncAntecedent<E> antecedent;

	private final FFuncConsequent<E> consequent;

	public Rule(FFuncAntecedent<E> antecedent, FFuncConsequent<E> consequent)
	{
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	@Override
	public float evaluateAntecedent(E parameter)
	{
		return this.antecedent.membershipOfEl(parameter);
	}

	@Override
	public FFuncAntecedent<E> getAntecedent()
	{
		return this.antecedent;
	}

	@Override
	public FFuncConsequent<E> getConsequent()
	{
		return this.consequent;
	}

}
