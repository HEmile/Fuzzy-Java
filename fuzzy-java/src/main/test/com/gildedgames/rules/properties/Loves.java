package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IRelationProperty;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Loves
{
	public static IRelationProperty<Entity> inst = Rules.factory.createRelationProp("test:loves");

	public static FFuncProp<Entity> inLove = Rules.b.prop(new PositiveSlope(1, 0.6f), inst);

	public static FFuncProp<Entity> not = Rules.b.prop(new NegativeSlope(0, 0.5f), inst);
}
