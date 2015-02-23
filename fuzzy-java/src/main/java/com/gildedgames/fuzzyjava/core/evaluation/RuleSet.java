package com.gildedgames.fuzzyjava.core.evaluation;

import java.util.HashSet;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAntecedent;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncConsequent;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRule;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOps;
import com.gildedgames.fuzzyjava.core.functions.StandardFunctionOps;

public class RuleSet implements IRuleSet
{
	private final Set<IRule<?>> rules = new HashSet<IRule<?>>();

	private static final FFunctionOps fBuilder = new StandardFunctionOps();

	@Override
	public <E> void addRule(FFuncAntecedent<E> antecedent, FFuncConsequent<E> consequent)
	{
		this.rules.add(new Rule<E>(antecedent, consequent));
	}

	@Override
	public <E, EI extends E> float valueOf(IProperty<E> searching, EI value)
	{
		//Collect rules with a consequent of the property we're searching for
		final Set<IRule<E>> applicableRules = new HashSet<IRule<E>>();
		for (final IRule<?> rule : this.rules)
		{
			final IProperty<?> property = rule.getConsequent().getProperty();
			if (property.equals(searching) || property.getName().equals(searching.getName()))
			{
				applicableRules.add((IRule<E>) rule);
			}
		}

		FFunction<Float> aggregate = null;
		boolean shouldDefuzzify = false;
		for (final IRule<E> rule : applicableRules)
		{
			//Evaluate the proposition in the antecedent, 
			//retrieve membership value of consequent
			final float membership = rule.evaluateAntecedent(value);
			shouldDefuzzify = shouldDefuzzify || membership > 0.0f;
			final FFunction<Float> cut = fBuilder.cut(rule.getConsequent(), membership);

			//Aggregate all the functions for the
			//proposition.
			if (aggregate == null)
			{
				aggregate = cut;
			}
			else
			{
				aggregate = fBuilder.or(aggregate, cut);
			}
		}
		if (aggregate == null || !shouldDefuzzify)
		{
			return 0.0f;
		}

		//Defuzzify 
		return fBuilder.defuzzify(aggregate, searching.getMinBound(), searching.getMaxBound());
	}

}
