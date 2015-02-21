package com.gildedgames.fuzzyjava.core.sets.relations;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.sets.HashFSetDiscrete;
import com.gildedgames.fuzzyjava.util.Pair;

public class HashFRelation<T1, T2> implements FRelationDiscr<T1, T2>
{

	private final Map<Entry<T1, T2>, Float> map;

	private final Set<T1> universe1;

	private final Set<T2> universe2;

	public HashFRelation()
	{
		this.map = new HashMap<Entry<T1, T2>, Float>();
		this.universe1 = new HashSet<T1>();
		this.universe2 = new HashSet<T2>();
	}

	public HashFRelation(int capacity)
	{
		this.map = new HashMap<Entry<T1, T2>, Float>(capacity);
		final int root = (int) Math.sqrt(capacity);
		this.universe1 = new HashSet<T1>(root);
		this.universe2 = new HashSet<T2>(root);
	}

	public <T1E extends T1, T2E extends T2> HashFRelation(FRelationSet<T1E, T2E> copyRelation)
	{
		this(copyRelation.size());
		for (final Entry<Entry<T1E, T2E>, Float> entry : copyRelation)
		{
			final Entry<T1E, T2E> elements = entry.getKey();
			this.map.put(new Pair<T1, T2>(elements.getKey(), elements.getValue()), entry.getValue());
			this.universe1.add(elements.getKey());
			this.universe2.add(elements.getValue());
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
		this.universe1.add(element1);
		this.universe2.add(element2);
	}

	@Override
	public float remove(T1 element1, T2 element2)
	{
		final Pair<T1, T2> pair = new Pair<T1, T2>(element1, element2);
		this.universe1.remove(element1);
		this.universe1.remove(element2);
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

	@Override
	public int length()
	{
		return this.universe2.size();
	}

	@Override
	public int width()
	{
		return this.universe1.size();
	}

	@Override
	public FSet<T2> getColumn(T1 i)
	{
		final FSetDiscr<T2> set = new HashFSetDiscrete<T2>(this.width());
		for (final T2 j : this.universe2)
		{
			set.add(j, this.strengthOfRelation(j, i));
		}
		return set;
	}

	@Override
	public FSet<T1> getRow(T2 j)
	{
		final FSetDiscr<T1> set = new HashFSetDiscrete<T1>(this.width());
		for (final T1 i : this.universe1)
		{
			set.add(i, this.strengthOfRelation(i, j));
		}
		return set;
	}

	@Override
	public Collection<T1> universe1()
	{
		return this.universe1;
	}

	@Override
	public Collection<T2> universe2()
	{
		return this.universe2;
	}

}
