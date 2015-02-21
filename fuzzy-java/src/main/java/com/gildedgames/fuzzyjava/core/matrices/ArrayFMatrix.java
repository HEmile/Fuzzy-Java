package com.gildedgames.fuzzyjava.core.matrices;

import java.util.Iterator;
import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.matrices.FMatrix;
import com.gildedgames.fuzzyjava.api.matrices.FVector;
import com.gildedgames.fuzzyjava.util.MutablePair;

public class ArrayFMatrix implements FMatrix
{
	private final FVector[] vectors;

	private final int width, length;

	public ArrayFMatrix(int width, int length)
	{
		this.vectors = new FVector[width];
		for (int i = 0; i < width; i++)
		{
			this.vectors[i] = new ArrayFVector(length);
		}
		this.width = width;
		this.length = length;
	}

	@Override
	public void setMembership(int i, int j, float membership)
	{
		this.vectors[i].set(j, membership);
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
					this.pair = new MutablePair<Integer, Integer>(0, 0);
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
	public void clear()
	{
		for (int i = 0; i < this.width; i++)
		{
			this.vectors[i] = new ArrayFVector(this.length);
		}
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
	public FVector getColumn(int i)
	{
		return this.vectors[i];
	}

	@Override
	public FVector getRow(int j)
	{
		final FVector vector = new ArrayFVector(this.width);
		for (int i = 0; i < this.width; i++)
		{
			vector.set(i, this.strengthOfRelation(i, j));
		}
		return vector;
	}

	@Override
	public int size()
	{
		return this.width * this.length;
	}

}
