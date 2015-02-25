package com.gildedgames.fuzzyjava.core.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOps;

/**
 * Implementation of a discrete fuzzy set using the most common mathematical
 * definitions. This is not an optimized implementation, but rather represents
 * the theory as closely as possible.
 * @author Emile
 */
public class HashFSetMut<E> implements FSetMut<E>
{

	private final Map<E, Float> map;

	public HashFSetMut()
	{
		this.map = new HashMap<E, Float>();
	}

	public HashFSetMut(int initialCapacity)
	{
		this.map = new HashMap<>(initialCapacity);
	}

	public HashFSetMut(FSetMut<? extends E> set)
	{
		this.map = new HashMap<>(set.size());
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

		if (obj instanceof FSetMut)
		{
			final FSetOps operations = new StandardSetOperations();
			try
			{
				@SuppressWarnings("unchecked")
				final FSetMut<E> set = (FSetMut<E>) obj;
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
	public void addAll(FSetMut<? extends E> fuzzySet)
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
