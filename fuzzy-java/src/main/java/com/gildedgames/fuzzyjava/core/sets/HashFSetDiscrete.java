package com.gildedgames.fuzzyjava.core.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;

/**
 * Implementation of a discrete fuzzy set using the most common mathematical
 * definitions. This is not an optimized implementation, but rather represents
 * the theory as closely as possible.
 * @author Emile
 */
public class HashFSetDiscrete<E> implements FSetDiscr<E>
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

	public HashFSetDiscrete(FSetDiscr<? extends E> set)
	{
		this.map = new HashMap<E, Float>(set.size());
		this.addAll(set);
	}

	@Override
	public int size()
	{
		return this.map.size();
	}

	@Override
	public void add(E object, float membership)
	{
		membership = Math.max(0.0f, Math.min(1.0f, membership));
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
	public Set<E> universe()
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

		if (obj instanceof FSetDiscr)
		{
			final FSetOperations operations = new StandardSetOperations();
			try
			{
				@SuppressWarnings("unchecked")
				final FSetDiscr<E> set = (FSetDiscr<E>) obj;
				return operations.contains(this, set) && operations.contains(set, this);
			}
			catch (final ClassCastException c)
			{
				c.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void addAll(FSetDiscr<? extends E> fuzzySet)
	{
		for (final Entry<? extends E, Float> entry : fuzzySet)
		{
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void addAll(Collection<? extends E> set)
	{
		for (final E element : set)
		{
			this.map.put(element, 1.0f);
		}
	}

	@Override
	public Iterator<Entry<E, Float>> iterator()
	{
		return this.map.entrySet().iterator();
	}
}
