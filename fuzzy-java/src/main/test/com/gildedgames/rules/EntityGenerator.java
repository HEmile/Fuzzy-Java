package com.gildedgames.rules;

import java.util.Random;

import com.gildedgames.rules.properties.Beauty;
import com.gildedgames.rules.properties.Length;
import com.gildedgames.rules.properties.Strength;
import com.gildedgames.rules.properties.Weight;

public class EntityGenerator
{

	private static Random r = new Random();

	public Entity generate()
	{
		final Entity e = new Entity();
		e.length = Length.inst.randomValue(r);
		e.weight = Weight.inst.randomValue(r);
		Beauty.inst.add(e, r.nextFloat());
		e.strength = Strength.inst.randomValue(r);
		e.male = r.nextBoolean();

		return e;
	}
}
