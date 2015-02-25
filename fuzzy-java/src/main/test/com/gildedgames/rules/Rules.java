package com.gildedgames.rules;

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
				Weight.isFat.ant(y)),
				Length.isAverage.cons(x));

	}

	/**
	 * I just realised how difficult it is 
	 * to unit test fuzzy stuff, lol
	 */
	@Test
	public void testEvaluateRules()
	{
		final IRuleSet<Entity> ruleSet = null;//factory.createRuleSet();
		this.addRules(ruleSet);

		final Entity ent1 = new Entity();
		ent1.length = 180;
		ent1.weight = 70;
		ent1.strength = ruleSet.valueOf(Strength.inst, ent1);
		System.out.println(ent1);

		final Entity ent2 = new Entity();
		ent2.length = 140;
		ent2.weight = 110;
		ent2.strength = ruleSet.valueOf(Strength.inst, ent2);
		System.out.println(ent2);

		final Entity ent3 = new Entity();
		ent3.length = 190;
		ent3.weight = 50;
		ent3.strength = ruleSet.valueOf(Strength.inst, ent3);
		System.out.println(ent3);

		final Entity ent4 = new Entity();
		ent4.length = 190;
		ent4.weight = 110;
		ent4.strength = ruleSet.valueOf(Strength.inst, ent4);
		System.out.println(ent4);

		final Entity ent5 = new Entity();
		ent5.length = 110;
		ent5.weight = 30;
		ent5.strength = ruleSet.valueOf(Strength.inst, ent5);
		System.out.println(ent5);

		final Entity ent6 = new Entity();
		ent6.length = 210;
		ent6.weight = 70;
		ent6.strength = ruleSet.valueOf(Strength.inst, ent6);
		System.out.println(ent6);

	}
}
