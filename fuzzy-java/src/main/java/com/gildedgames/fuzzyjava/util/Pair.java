package com.gildedgames.fuzzyjava.util;

import java.util.Map;
import java.util.Map.Entry;

public class Pair<T, U> implements Map.Entry<T, U>
{
	private final T first;

	private final U second;

	private final int hash;

	public Pair(T f, U s)
	{
		this.first = f;
		this.second = s;
		this.hash = (this.first == null ? 0 : this.first.hashCode() * 31)
				+ (this.second == null ? 0 : this.second.hashCode());
	}

	public T getFirst()
	{
		return this.first;
	}

	public U getSecond()
	{
		return this.second;
	}

	@Override
	public int hashCode()
	{
		return this.hash;
	}

	@Override
	public boolean equals(Object oth)
	{
		if (this == oth)
		{
			return true;
		}
		if (oth == null || !(oth instanceof Entry))
		{
			return false;
		}
		final Entry other = (Entry) oth;
		return (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
				&& (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
	}

	@Override
	public T getKey()
	{
		return this.getFirst();
	}

	@Override
	public U getValue()
	{
		return this.getSecond();
	}

	@Override
	public U setValue(U arg0)
	{
		throw new RuntimeException("Cannot set value of an immutable pair");
	}

}
