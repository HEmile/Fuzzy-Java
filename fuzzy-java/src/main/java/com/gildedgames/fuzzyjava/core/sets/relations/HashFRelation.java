package com.gildedgames.fuzzyjava.core.sets.relations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.util.Pair;

public class HashFRelation<T1, T2> implements FRelationDiscr<T1, T2>
{

	private final Map<Entry<T1, T2>, Float> map;

	public HashFRelation()
	{
		this.map = new HashMap<Entry<T1, T2>, Float>();
	}

	public HashFRelation(int capacity)
	{
		this.map = new HashMap<Entry<T1, T2>, Float>(capacity);
	}

	public <T1E extends T1, T2E extends T2> HashFRelation(FRelationSet<T1E, T2E> copyRelation)
	{
		this(copyRelation.size());
		for (final Entry<Entry<T1E, T2E>, Float> entry : copyRelation)
		{
			final Entry<T1E, T2E> elements = entry.getKey();
			this.map.put(new Pair<T1, T2>(elements.getKey(), elements.getValue()), entry.getValue());
		}
	}

	@Override
	public int size()
	{
		return this.map.size();
	}

	@Override
	public void add(T1 element1, T2 element2, float strength)
	{
		strength = Math.max(0.0f, Math.min(1.0f, strength));
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
	public float membershipOf(Entry<T1, T2> element)
	{
		return this.map.get(element);
	}

	@Override
	public Iterator<Entry<Entry<T1, T2>, Float>> iterator()
	{
		return this.map.entrySet().iterator();
	}

	@Override
	public void clear()
	{
		this.map.clear();
	}

	@Override
	public void add(Entry<T1, T2> object, float membership)
	{
		this.map.put(object, membership);
	}

	@Override
	public void addAll(FSetDiscr<? extends Entry<T1, T2>> fuzzySet)
	{
		for (final Entry<? extends Entry<T1, T2>, Float> entry : fuzzySet)
		{
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void addAll(Collection<? extends Entry<T1, T2>> set)
	{
		for (final Entry<T1, T2> entry : set)
		{
			this.map.put(entry, 1.0f);
		}
	}

	@Override
	public float remove(Entry<T1, T2> object)
	{
		return this.map.remove(object);
	}

	@Override
	public Set<Entry<T1, T2>> universe()
	{
		return this.map.keySet();
	}

}
