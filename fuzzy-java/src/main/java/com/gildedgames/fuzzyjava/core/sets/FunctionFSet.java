package com.gildedgames.fuzzyjava.core.sets;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.util.MutablePair;

public class FunctionFSet<E> implements FSet<E>
{

	private final FFunction<E> function;

	private final Set<E> elements;

	public FunctionFSet(FFunction<E> function, Set<E> elements)
	{
		this.function = function;
		this.elements = elements;
	}

	@Override
	public float membershipOf(E element)
	{
		return this.function.membershipOf(element);
	}

	@Override
	public Iterator<Entry<E, Float>> iterator()
	{
		return new Iterator<Entry<E, Float>>()
		{

			MutablePair<E, Float> pair;

			Iterator<E> iterator = FunctionFSet.this.elements.iterator();

			@Override
			public boolean hasNext()
			{
				return this.pair != null && this.iterator.hasNext();
			}

			@Override
			public Entry<E, Float> next()
			{
				final E element = this.iterator.next();
				if (this.pair == null)
				{
					this.pair = new MutablePair<>(element, FunctionFSet.this.membershipOf(element));
				}
				else
				{
					this.pair.firstM = element;
					this.pair.secondM = FunctionFSet.this.membershipOf(element);
				}
				return this.pair;
			}

		};
	}

	@Override
	public int size()
	{
		return this.elements.size();
	}

	@Override
	public Set<E> universe()
	{
		return this.elements;
	}

}
