package com.gildedgames.rules.properties;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.rules.Entity;

public class Gender
{
	private static Set<Entry<IProperty<Entity>, Parameter[]>> emptySet = new HashSet<>(0);

	public static FFuncAnt<Entity> male(final Variable var)
	{
		return new FFuncAnt<Entity>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return ((Entity) element[0]).male ? 1.0f : 0.0f;
			}

			@Override
			public Set<Entry<IProperty<Entity>, Parameter[]>> propertiesWithVars()
			{
				return emptySet;
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<Entity> ruleSet, Set<Entry<List<Object>, IProperty<Entity>>> inferred)
			{
				return ((Entity) interpretation.get(var)).male ? 1.0f : 0.0f;
			}
		};
	}

	public static FFuncAnt<Entity> female(final Variable var)
	{
		return new FFuncAnt<Entity>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return !((Entity) element[0]).male ? 1.0f : 0.0f;
			}

			@Override
			public Set<Entry<IProperty<Entity>, Parameter[]>> propertiesWithVars()
			{
				return emptySet;
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<Entity> ruleSet, Set<Entry<List<Object>, IProperty<Entity>>> inferred)
			{
				return !((Entity) interpretation.get(var)).male ? 1.0f : 0.0f;
			}
		};
	}
}
