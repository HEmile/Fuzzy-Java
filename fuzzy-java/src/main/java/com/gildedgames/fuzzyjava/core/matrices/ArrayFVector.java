package com.gildedgames.fuzzyjava.core.matrices;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.matrices.FVector;
import com.gildedgames.fuzzyjava.util.MutablePair;
import com.gildedgames.fuzzyjava.util.Pair;

public class ArrayFVector implements FVector
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
	public float set(int object, float membership)
	{
		membership = Math.max(0, Math.min(1, membership));
		final float old = this.array[object];
		this.array[object] = membership;
		return old;
	}

	@Override
	public float membershipOf(Integer object)
	{
		final int i = object;
		if (i >= 0 && i < this.length())
		{
			return this.array[object];
		}
		return 0.0f;
	}

	@Override
	public void clear()
	{
		Arrays.fill(this.array, 0);
	}

	@Override
	public int length()
	{
		return this.array.length;
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
				return this.pair == null || this.pair.firstM < ArrayFVector.this.length();
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
		return this.length();
	}

}
