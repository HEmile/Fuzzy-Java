package com.gildedgames.fuzzyjava.util;

public class Pair<T, U>
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
		if (oth == null || !this.getClass().isInstance(oth))
		{
			return false;
		}
		final Pair<T, U> other = this.getClass().cast(oth);
		return (this.first == null ? other.first == null : this.first.equals(other.first))
				&& (this.second == null ? other.second == null : this.second.equals(other.second));
	}

}
