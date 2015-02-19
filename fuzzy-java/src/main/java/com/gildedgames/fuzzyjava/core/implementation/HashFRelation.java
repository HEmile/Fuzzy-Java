package com.gildedgames.fuzzyjava.core.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.core.interfaces.FRelation;
import com.gildedgames.fuzzyjava.core.interfaces.FRelationEntry;
import com.gildedgames.fuzzyjava.util.Pair;

public class HashFRelation<T1, T2> implements FRelation<T1, T2>
{

	private final Map<Pair<T1, T2>, Float> map;

	public HashFRelation()
	{
		this.map = new HashMap<Pair<T1, T2>, Float>();
	}

	public HashFRelation(int capacity)
	{
		this.map = new HashMap<Pair<T1, T2>, Float>(capacity);
	}

	public HashFRelation(FRelation<T1, T2> copyRelation)
	{
		this(copyRelation.elementSize());
		for (final FRelationEntry<T1, T2> entry : copyRelation.entrySet())
		{
			this.map.put(new Pair<T1, T2>(entry.element1(), entry.element2()), entry.strength());
		}
	}

	@Override
	public int elementSize()
	{
		return this.map.size();
	}

	@Override
	public void add(T1 element1, T2 element2, float strength)
	{
		final Pair<T1, T2> pair = new Pair<T1, T2>(element1, element2);
		this.map.put(pair, strength);
	}

	@Override
	public float remove(T1 element1, T2 element2)
	{
		final Pair<T1, T2> pair = new Pair<T1, T2>(element1, element2);
		return this.map.remove(pair);
	}

	@Override
	public float strengthOfRelation(Object element1, Object element2)
	{
		final Pair<?, ?> pair = new Pair<Object, Object>(element1, element2);

		if (this.map.containsKey(pair))
		{
			return this.map.get(pair);
		}
		return 0.0f;
	}

	@Override
	public Set<FRelationEntry<T1, T2>> entrySet()
	{
		final Set<FRelationEntry<T1, T2>> set = new HashSet<FRelationEntry<T1, T2>>(this.elementSize());
		for (final Entry<Pair<T1, T2>, Float> entry : this.map.entrySet())
		{
			final Pair<T1, T2> pair = entry.getKey();
			set.add(new FRelationEntry<T1, T2>(pair.getFirst(), pair.getSecond(), entry.getValue()));
		}

		return set;
	}

	@Override
	public void clear()
	{
		this.map.clear();
	}

}
