package com.gildedgames.rules;

import com.gildedgames.rules.properties.Beauty;

public class Entity
{
	public float length = -1;

	public float strength = -1;

	public float weight = -1;

	public boolean male;

	@Override
	public String toString()
	{
		return "Male: " + this.male + " length: " + this.length + " strength: " + this.strength + " weight " + this.weight + " beauty " + Beauty.inst.membershipOf(this);
	}
}
