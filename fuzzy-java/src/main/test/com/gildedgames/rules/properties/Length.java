package com.gildedgames.rules.properties;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.functions.FuzzyNumber;
import com.gildedgames.fuzzyjava.functions.NegativeSlope;
import com.gildedgames.fuzzyjava.functions.PositiveSlope;
import com.gildedgames.rules.Entity;
import com.gildedgames.rules.Rules;

public class Length extends BaseProp
{
	public static Length inst = new Length();

	public static FFuncProp<Entity> isTall = Rules.b.prop(new PositiveSlope(210, 45), inst);

	public static FFuncProp<Entity> isAverage = Rules.b.prop(new FuzzyNumber(170, 20, 20), inst);

	public static FFuncProp<Entity> isShort = Rules.b.prop(new NegativeSlope(110, 50), inst);

	@Override
	public float getMinBound()
	{
		return 100;
	}

	@Override
	public float getMaxBound()
	{
		return 230;
	}

	@Override
	public String getName()
	{
		return "test:length";
	}

	@Override
	protected float convert(Entity el)
	{
		return el.length;
	}

	@Override
	protected void setProperty(Entity el, float value)
	{
		el.length = value;
	}

}
