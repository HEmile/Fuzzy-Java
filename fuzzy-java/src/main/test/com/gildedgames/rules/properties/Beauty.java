package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.ISetProperty;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Beauty
{
	public static ISetProperty<Entity> inst = Rules.factory.createSetProp("test:beauty");

	public static FFuncProp<Entity> beautiful = Rules.b.prop(new PositiveSlope(1, 0.3f), inst);

	public static FFuncProp<Entity> ugly = Rules.b.prop(new NegativeSlope(0, 0.3f), inst);

	public static FFuncProp<Entity> average = Rules.b.prop(new FuzzyNumber(0.5f, 0.3f, 0.3f), inst);
}
