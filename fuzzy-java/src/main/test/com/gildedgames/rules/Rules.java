package com.gildedgames.rules;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gildedgames.fuzzyjava.api.FuzzyFactory;
import com.gildedgames.fuzzyjava.api.evaluation.IPropBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.core.FuzzyFactoryStandard;
import com.gildedgames.rules.properties.Length;
import com.gildedgames.rules.properties.Strength;
import com.gildedgames.rules.properties.Weight;

public class Rules
{
	public static FuzzyFactory factory = new FuzzyFactoryStandard();

	public static IPropBuilder b = factory.createPropFunctionBuilder();

	public void addRules(IRuleSet<Entity> ruleSet)
	{
		final Variable x = b.createVar();
		final Variable y = b.createVar();

		ruleSet.addRule(b.and(
				b.very(Strength.isStrong.ant(x)),
				Weight.isFat.ant(x)),
				Length.isAverage.cons(x));

	}

	/**
	 * I just realised how difficult it is 
	 * to unit test fuzzy stuff, lol
	 */
	@Test
	public void testEvaluateRules()
	{
		final Entity ent1 = new Entity();
		final Entity ent2 = new Entity();
		final Entity ent3 = new Entity();
		final Entity ent4 = new Entity();
		final Entity ent5 = new Entity();
		final Entity ent6 = new Entity();
		final List<Entity> ents = new ArrayList<Entity>();
		ents.add(ent1);
		ents.add(ent2);
		ents.add(ent3);
		ents.add(ent4);
		ents.add(ent5);
		ents.add(ent6);
		final IRuleSet<Entity> ruleSet = factory.createRuleSet(ents);
		this.addRules(ruleSet);

		ent1.strength = 80;
		ent1.weight = 70;
		ent1.length = ruleSet.valueOf(Length.inst, ent1);
		System.out.println(ent1);

		ent2.strength = 50;
		ent2.weight = 110;
		ent2.length = ruleSet.valueOf(Length.inst, ent2);
		System.out.println(ent2);

		ent3.strength = 100;
		ent3.weight = 50;
		ent3.length = ruleSet.valueOf(Length.inst, ent3);
		System.out.println(ent3);

		ent4.strength = 80f;
		ent4.weight = 110;
		ent4.length = ruleSet.valueOf(Length.inst, ent4);
		System.out.println(ent4);

		ent5.strength = 40;
		ent5.weight = 30;
		ent5.length = ruleSet.valueOf(Length.inst, ent5);
		System.out.println(ent5);

		ent6.strength = 100;
		ent6.weight = 70;
		ent6.length = ruleSet.valueOf(Length.inst, ent6);
		System.out.println(ent6);

	}
}
