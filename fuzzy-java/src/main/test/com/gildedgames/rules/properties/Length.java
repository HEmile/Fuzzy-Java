package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Length implements IProperty<Entity>
{
	public static IProperty<Entity> inst = new Length();

	public static FFuncProp<Entity> isTall = Rules.funcO.toPropFunc(new PositiveSlope(210, 45), inst);

	public static FFuncProp<Entity> isAverage = Rules.funcO.toPropFunc(new FuzzyNumber(170, 20, 20), inst);

	public static FFuncProp<Entity> isShort = Rules.funcO.toPropFunc(new NegativeSlope(110, 50), inst);

	@Override
	public float getMinBound()
	{
		return 100;
	}

	@Override
	public float getMaxBound()
	{
		return 300;
	}

	@Override
	public float convert(Entity element)
	{
		return element.length;
	}

	@Override
	public String getName()
	{
		return "test:length";
	}

}
