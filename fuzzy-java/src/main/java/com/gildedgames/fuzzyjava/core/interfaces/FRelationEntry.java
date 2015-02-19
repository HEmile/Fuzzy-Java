package com.gildedgames.fuzzyjava.core.interfaces;

public class FRelationEntry<T1, T2>
{
	private final float strength;

	private final T1 element1;

	private final T2 element2;

	public FRelationEntry(T1 element1, T2 element2, float strength)
	{
		this.strength = strength;

		this.element1 = element1;

		this.element2 = element2;
	}

	public float strength()
	{
		return this.strength;
	}

	public T1 element1()
	{
		return this.element1;
	}

	public T2 element2()
	{
		return this.element2;
	}
}
