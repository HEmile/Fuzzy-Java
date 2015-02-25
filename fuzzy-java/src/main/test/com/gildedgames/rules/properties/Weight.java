package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Weight extends BaseProp
{
	public static Weight inst = new Weight();

	public static FFuncProp<Entity> isFat = Rules.b.prop(new PositiveSlope(110, 25), inst);

	public static FFuncProp<Entity> isAverage = Rules.b.prop(new FuzzyNumber(70, 20, 20), inst);

	public static FFuncProp<Entity> isSkinny = Rules.b.prop(new NegativeSlope(37, 23), inst);

	@Override
	public float getMinBound()
	{
		return 30;
	}

	@Override
	public float getMaxBound()
	{
		return 150;
	}

	@Override
	public float convert(Entity element)
	{
		return element.weight;
	}

	@Override
	public String getName()
	{
		return "test:weight";
	}

	@Override
	public void setProperty(Entity element, float value)
	{
		element.weight = value;
	}

}
