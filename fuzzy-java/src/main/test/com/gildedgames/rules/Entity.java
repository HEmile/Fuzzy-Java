package com.gildedgames.rules;

import com.gildedgames.rules.properties.Beauty;

public class Entity
{
	public float length = -1;

	public float strength = -1;

	public float weight = -1;

	@Override
	public String toString()
	{
		return "length: " + this.length + " strength: " + this.strength + " weight " + this.weight + " beauty " + Beauty.inst.membershipOf(this);
	}
}
