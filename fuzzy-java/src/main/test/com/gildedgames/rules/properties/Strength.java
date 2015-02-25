package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Strength extends BaseProp
{
	public static Strength inst = new Strength();

	public static FFuncProp<Entity> isStrong = Rules.b.prop(new PositiveSlope(95, 30), inst);

	public static FFuncProp<Entity> isAverage = Rules.b.prop(new FuzzyNumber(50, 20, 20), inst);

	public static FFuncProp<Entity> isWeak = Rules.b.prop(new NegativeSlope(5, 30), inst);

	@Override
	public float getMinBound()
	{
		return 0;
	}

	@Override
	public float getMaxBound()
	{
		return 100;
	}

	@Override
	public float convert(Entity element)
	{
		return element.strength;
	}

	@Override
	public String getName()
	{
		return "test:strength";
	}

	@Override
	public void setProperty(Entity element, float value)
	{
		element.strength = value;
	}

}
