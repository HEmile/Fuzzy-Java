package com.gildedgames.fuzzyjava.core.matrices;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.util.CollectionHelper;
import com.gildedgames.fuzzyjava.util.MutablePair;
import com.gildedgames.fuzzyjava.util.Pair;

public class ArrayFMatrix implements FRelationSet<Integer, Integer>
{
	private final FSet<Integer>[] vectors;

	private final int width, length;

	public ArrayFMatrix(int width, int length)
	{
		this.vectors = new FSet[width];

		this.width = width;
		this.length = length;

	}

	@Override
	public float strengthOfRelation(Integer element1, Integer element2)
	{
		return this.vectors[element1].membershipOf(element2);
	}

	@Override
	public float membershipOf(Entry<Integer, Integer> element)
	{
		return this.vectors[element.getKey()].membershipOf(element.getValue());
	}

	@Override
	public Iterator<Entry<Entry<Integer, Integer>, Float>> iterator()
	{
		return new Iterator<Entry<Entry<Integer, Integer>, Float>>()
		{
			MutablePair<Integer, Integer> pair;

			MutablePair<Entry<Integer, Integer>, Float> element;

			@Override
			public boolean hasNext()
			{
				return this.pair != null && this.pair.firstM < ArrayFMatrix.this.width && this.pair.secondM < ArrayFMatrix.this.length;
			}

			@Override
			public Entry<Entry<Integer, Integer>, Float> next()
			{
				if (this.pair == null)
				{
					this.pair = new MutablePair<>(0, 0);
					this.element = new MutablePair<Entry<Integer, Integer>, Float>(this.pair, ArrayFMatrix.this.membershipOf(this.pair));
					return this.element;
				}
				this.pair.firstM++;
				if (this.pair.firstM > ArrayFMatrix.this.width)
				{
					this.pair.firstM = 0;
					this.pair.secondM++;
				}
				this.element.firstM = this.pair;
				this.element.secondM = ArrayFMatrix.this.membershipOf(this.pair);
				return this.element;
			}
		};
	}

	@Override
	public int length()
	{
		return this.length;
	}

	@Override
	public int width()
	{
		return this.width;
	}

	@Override
	public FSet<Integer> getColumn(Integer i)
	{
		return this.vectors[i];
	}

	@Override
	public FSet<Integer> getRow(Integer j)
	{
		final float[] buffer = new float[this.width];
		for (int i = 0; i < this.width; i++)
		{
			buffer[i] = this.strengthOfRelation(i, j);
		}
		final FSet<Integer> vector = new ArrayFVector(buffer);
		return vector;
	}

	@Override
	public int size()
	{
		return this.width * this.length;
	}

	@Override
	public Collection<Integer> universe1()
	{
		return CollectionHelper.rangeTo(this.width);
	}

	@Override
	public Collection<Integer> universe2()
	{
		return CollectionHelper.rangeTo(this.length);
	}

	@Override
	public Set<Entry<Integer, Integer>> universe()
	{
		final Set<Entry<Integer, Integer>> universe = new HashSet<>(this.width * this.length);
		for (int i = 0; i < this.width; i++)
		{
			this.vectors[i] = new ArrayFVector(this.length);
			for (int j = 0; j < this.length; j++)
			{
				universe.add(new Pair<Integer, Integer>(i, j));
			}
		}
		return universe;
	}

}
