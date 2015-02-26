package com.gildedgames.rules;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.core.FuzzyFactoryStandard;
import com.gildedgames.rules.properties.Arrogant;
import com.gildedgames.rules.properties.Beauty;
import com.gildedgames.rules.properties.Gender;
import com.gildedgames.rules.properties.Hates;
import com.gildedgames.rules.properties.Length;
import com.gildedgames.rules.properties.Loves;
import com.gildedgames.rules.properties.Strength;
import com.gildedgames.rules.properties.Weight;

public class Rules
{
	public static FuzzyFactory factory = new FuzzyFactoryStandard();

	public static IRuleBuilder b = factory.createPropFunctionBuilder();

	private final EntityGenerator entityGenerator = new EntityGenerator();

	@SuppressWarnings("unchecked")
	public void addRules(IRuleSet<Entity> ruleSet)
	{
		final Variable x = b.createVar();
		final Variable y = b.createVar();

		ruleSet.addRule(b.and(
				b.very(Strength.isStrong.a(x)),
				Weight.isFat.a(x)),

				Length.isAverage.c(x));

		ruleSet.addRule(b.and(
				Gender.male(x),
				Strength.isStrong.a(x),
				Weight.isAverage.a(x),
				Gender.female(y)),

				Loves.inLove.c(y, x));

		ruleSet.addRule(b.all(y,
				b.implies(b.and(
						Gender.male(x),
						Gender.female(y)),
						Loves.inLove.a(y, x))),

				Arrogant.arrogant.c(x));

		ruleSet.addRule(b.or(
				Strength.isWeak.a(x),
				Weight.isFat.a(x),
				Weight.isSkinny.a(x),
				Length.isShort.a(x),
				Beauty.ugly.a(x)),

				Arrogant.modest.c(x));

		ruleSet.addRule(b.and(
				Arrogant.modest.a(x),
				Arrogant.arrogant.a(y)),

				Hates.fiercly.c(x, y));

		ruleSet.addRule(b.and(
				Arrogant.arrogant.a(x),
				Arrogant.arrogant.a(y)),

				Hates.aBit.c(x, y));

		ruleSet.addRule(b.and(
				Arrogant.modest.a(x)),

				Loves.not.c(x, x));
	}

	/**
	 * I just realised how difficult it is 
	 * to unit test fuzzy stuff, lol
	 */
	@Test
	public void testEvaluateRules()
	{
		final List<Entity> ents = new ArrayList<Entity>();
		for (int i = 0; i < 100; i++)
		{
			ents.add(this.entityGenerator.generate());
		}
		final IRuleSet<Entity> ruleSet = factory.createRuleSet(ents);
		this.addRules(ruleSet);
		final Entity newEnt1 = new Entity();
		Beauty.inst.add(newEnt1, 0f);
		for (final Entity e : ents)
		{
			System.out.println(e);
			System.out.println("Loves himself: " + ruleSet.valueOf(Loves.inst, e, e));
		}

		System.out.println(ruleSet.missing(Arrogant.arrogant, new Object[] { null })[0]);
	}
}
