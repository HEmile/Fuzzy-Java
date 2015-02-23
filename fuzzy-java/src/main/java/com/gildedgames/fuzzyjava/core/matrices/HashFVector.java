package com.gildedgames.fuzzyjava.core.matrices;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSet;

public class HashFVector<E> implements FSet<E>
{

	private final Map<E, Float> map;

	public HashFVector(FSet<E> set)
	{
		this.map = new HashMap<E, Float>(set.size());
		for (final Entry<E, Float> entry : set)
		{
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	public HashFVector(Collection<E> set)
	{
		this.map = new HashMap<E, Float>(set.size());
		for (final E entry : set)
		{
			this.map.put(entry, 1.0f);
		}
	}

	@Override
	public int size()
	{
		return this.map.size();
	}

	@Override
	public float membershipOf(E element)
	{
		return this.map.get(element);
	}

	@Override
	public Iterator<Entry<E, Float>> iterator()
	{
		return this.map.entrySet().iterator();
	}

	@Override
	public Set<E> universe()
	{
		return this.map.keySet();
	}

}
