package com.gildedgames.fuzzyjava.util;

/**
 * Mutable implementation of a Pair object.
 * Recommended only when performance matters,
 * for example in iterators.
 * @author Emile
 */
public class MutablePair<T1, T2> extends Pair<T1, T2>
{

	public T1 firstM;

	public T2 secondM;

	public MutablePair(T1 f, T2 s)
	{
		super(f, s);
		this.firstM = f;
		this.secondM = s;
	}

	@Override
	public int hashCode()
	{
		return (this.getFirst() == null ? 0 : this.getFirst().hashCode() * 31)
				+ (this.getSecond() == null ? 0 : this.getSecond().hashCode());
	}

	@Override
	public T1 getFirst()
	{
		return this.firstM;
	}

	@Override
	public T2 getSecond()
	{
		return this.secondM;
	}

	@Override
	public T2 setValue(T2 arg0)
	{
		final T2 oldValue = this.secondM;
		this.secondM = arg0;
		return oldValue;
	}

}
