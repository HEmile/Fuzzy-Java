package com.gildedgames.fuzzyjava.core.matrices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.util.CollectionHelper;
import com.gildedgames.fuzzyjava.util.MutablePair;
import com.gildedgames.fuzzyjava.util.Pair;

public class ArrayFVector implements FSet<Integer>
{
	private final float[] array;

	public ArrayFVector(int length)
	{
		this.array = new float[length];
		Arrays.fill(this.array, 0);
	}

	public ArrayFVector(float[] array)
	{
		this.array = array;
	}

	@Override
	public float membershipOf(Integer object)
	{
		final int i = object;
		if (i >= 0 && i < this.size())
		{
			return this.array[object];
		}
		return 0.0f;
	}

	@Override
	public Iterator<Entry<Integer, Float>> iterator()
	{
		return new Iterator<Entry<Integer, Float>>()
		{
			MutablePair<Integer, Float> pair;

			@Override
			public boolean hasNext()
			{
				return this.pair == null || this.pair.firstM < ArrayFVector.this.size();
			}

			@Override
			public Pair<Integer, Float> next()
			{
				if (this.pair == null)
				{
					this.pair = new MutablePair<Integer, Float>(0, ArrayFVector.this.membershipOf(0));
					return this.pair;
				}
				this.pair.firstM++;
				this.pair.secondM = ArrayFVector.this.membershipOf(this.pair.firstM);
				return this.pair;
			}

		};
	}

	@Override
	public int size()
	{
		return this.array.length;
	}

	@Override
	public Set<Integer> universe()
	{
		return new HashSet<Integer>(CollectionHelper.rangeTo(this.size()));
	}

}
