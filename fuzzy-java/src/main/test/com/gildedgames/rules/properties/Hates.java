package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IRelationProperty;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Hates
{
	public static IRelationProperty<Entity> inst = Rules.factory.createRelationProp("test:hates");

	public static FFuncProp<Entity> fiercly = Rules.b.prop(new PositiveSlope(1, 0.3f), inst);

	public static FFuncProp<Entity> aBit = Rules.b.prop(new FuzzyNumber(0.5f, 0.3f, 0.3f), inst);

	public static FFuncProp<Entity> not = Rules.b.prop(new NegativeSlope(0, 0.5f), inst);
}
