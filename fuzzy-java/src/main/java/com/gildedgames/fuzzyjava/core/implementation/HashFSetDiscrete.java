package com.gildedgames.fuzzyjava.core.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.core.interfaces.FSetDiscrete;
import com.gildedgames.fuzzyjava.core.interfaces.FSetOperations;

/**
 * Implementation of a discrete fuzzy set using the most common mathematical
 * definitions. This is not an optimized implementation, but rather represents
 * the theory as closely as possible.
 * @author Emile
 */
public class HashFSetDiscrete<E> implements FSetDiscrete<E>
{

	private final Map<E, Float> map;

	public HashFSetDiscrete()
	{
		this.map = new HashMap<E, Float>();
	}

	public HashFSetDiscrete(int initialCapacity)
	{
		this.map = new HashMap<E, Float>(initialCapacity);
	}

	public HashFSetDiscrete(FSetDiscrete<? extends E> set)
	{
		this.map = new HashMap<E, Float>(set.elementSize());
		for (final Entry<? extends E, Float> entry : set.entrySet())
		{
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public int elementSize()
	{
		return this.map.size();
	}

	@Override
	public void add(E object, float membership)
	{
		this.map.put(object, membership);
	}

	@Override
	public float remove(E object)
	{
		return this.map.remove(object);
	}

	@Override
	public float membershipOf(Object object)
	{
		if (this.map.containsKey(object))
		{
			return this.map.get(object);
		}
		return 0.0f;
	}

	@Override
	public Set<Entry<E, Float>> entrySet()
	{
		return this.map.entrySet();
	}

	@Override
	public Set<E> objects()
	{
		return this.map.keySet();
	}

	@Override
	public void clear()
	{
		this.map.clear();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof FSetDiscrete)
		{
			final FSetOperations operations = new StandardSetOperations();
			final FSetDiscrete<?> set = (FSetDiscrete<?>) obj;
			return operations.contains(this, set) && operations.contains(set, this);
		}
		return false;
	}
}
