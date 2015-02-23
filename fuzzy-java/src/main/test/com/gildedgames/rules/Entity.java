package com.gildedgames.rules;

public class Entity
{
	public float length;

	public float strength;

	public float weight;

	@Override
	public String toString()
	{
		return "length: " + this.length + " strength: " + this.strength + " weight " + this.weight;
	}
}
