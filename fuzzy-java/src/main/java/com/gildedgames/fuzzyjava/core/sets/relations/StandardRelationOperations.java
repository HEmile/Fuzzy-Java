package com.gildedgames.fuzzyjava.core.sets.relations;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.relations.FRelation;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOps;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;

public class StandardRelationOperations implements FRelationOps
{

	@Override
	public <T1, T2, T3> FRelationSet<T1, T3> maxMinComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2)
	{
		final FRelationMut<T1, T3> newRelation = new HashFRelation<>();

		for (final Entry<Entry<T1, T2>, Float> entrya : relation1)
		{
			final T1 elementa1 = entrya.getKey().getKey();
			final T2 elementa2 = entrya.getKey().getValue();
			final float strengtha = entrya.getValue();
			for (final Entry<Entry<T2, T3>, Float> entryb : relation2)
			{
				final T2 elementb1 = entryb.getKey().getKey();
				if (elementa2.equals(elementb1))
				{
					final T3 elementb2 = entryb.getKey().getValue();
					final float strengthb = entryb.getValue();

					final float iterStrength = Math.min(strengtha, strengthb);

					final float currentStrength = newRelation.strengthOfRelation(elementa1, elementb2);

					if (currentStrength < iterStrength)
					{
						newRelation.add(elementa1, elementb2, iterStrength);
					}
				}
			}
		}

		return newRelation;
	}

	@Override
	public <T1, T2, T3> FRelationSet<T1, T3> maxProductComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2)
	{
		final FRelationMut<T1, T3> newRelation = new HashFRelation<>();

		for (final Entry<Entry<T1, T2>, Float> entrya : relation1)
		{
			final T1 elementa1 = entrya.getKey().getKey();
			final T2 elementa2 = entrya.getKey().getValue();
			final float strengtha = entrya.getValue();
			for (final Entry<Entry<T2, T3>, Float> entryb : relation2)
			{
				final T2 elementb1 = entryb.getKey().getKey();
				if (elementa2.equals(elementb1))
				{
					final T3 elementb2 = entryb.getKey().getValue();
					final float strengthb = entryb.getValue();

					final float iterStrength = strengtha * strengthb;

					final float currentStrength = newRelation.strengthOfRelation(elementa1, elementb2);

					if (currentStrength < iterStrength)
					{
						newRelation.add(elementa1, elementb2, iterStrength);
					}
				}
			}
		}

		return newRelation;
	}

	@Override
	public <E> boolean isReflexive(FRelationSet<E, E> relation)
	{
		if (relation.width() != relation.length())
		{
			return false;
		}
		for (final E element : relation.universe1())
		{
			if (relation.strengthOfRelation(element, element) != 1)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public <E> boolean isSymmetric(FRelationSet<E, E> relation)
	{
		if (relation.width() != relation.length())
		{
			return false;
		}
		for (final E element1 : relation.universe1())
		{
			for (final E element2 : relation.universe2())
			{
				if (relation.strengthOfRelation(element1, element2) != relation.strengthOfRelation(element2, element1))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public <E> boolean isTransitive(FRelationSet<E, E> relation)
	{
		if (relation.width() != relation.length())
		{
			return false;
		}
		for (final E a : relation.universe1())
		{
			for (final E b : relation.universe1())
			{
				for (final E c : relation.universe1())
				{
					final float aToB = relation.strengthOfRelation(a, b);
					final float bToC = relation.strengthOfRelation(b, c);
					final float aToC = relation.strengthOfRelation(a, c);

					if (aToC < Math.min(aToB, bToC))
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public <E> boolean isTolerance(FRelationSet<E, E> relation)
	{
		return this.isReflexive(relation) && this.isSymmetric(relation);
	}

	@Override
	public <E> boolean isEquivalence(FRelationSet<E, E> relation)
	{
		return this.isTolerance(relation) && this.isTransitive(relation);
	}

	@Override
	public <E> FRelationSet<E, E> toEquivalence(FRelationSet<E, E> toleranceRelation)
	{
		if (!this.isTolerance(toleranceRelation))
		{
			throw new IllegalArgumentException("Expected a tolerance relation, but it wasn't");
		}
		FRelationSet<E, E> toEquivalence = toleranceRelation;
		while (!this.isEquivalence(toEquivalence))
		{
			toEquivalence = this.maxMinComposition(toEquivalence, toleranceRelation);
		}
		return toleranceRelation;
	}

	@Override
	public <T1, T2> FRelation<T1, T1> cosineAmplitude(FRelationSet<T1, T2> matrix)
	{
		if (matrix == null)
		{
			return new HashFRelation<>(0);
		}
		final int n = matrix.width();
		final FRelationMut<T1, T1> relation = new HashFRelation<>(n * n);
		for (final T1 i : matrix.universe1())
		{
			for (final T1 j : matrix.universe1())
			{
				float dotProduct = 0.0f;
				float sum1 = 0.0f;
				float sum2 = 0.0f;
				for (final T2 k : matrix.universe2())
				{
					final float value1 = matrix.strengthOfRelation(i, k);
					final float value2 = matrix.strengthOfRelation(j, k);

					dotProduct += value1 * value2;
					sum1 += value1 * value1;
					sum2 += value2 * value2;
				}
				dotProduct = Math.abs(dotProduct);
				final float nom = (float) Math.sqrt(sum1 * sum2);//TODO: invalid cast?
				relation.add(i, j, dotProduct / nom);
			}
		}

		return relation;
	}

	@Override
	public <T1, T2> FRelation<T1, T1> maxMin(FRelationSet<T1, T2> matrix)
	{
		if (matrix == null)
		{
			return new HashFRelation<>(0);
		}
		final int n = matrix.width();
		final FRelationMut<T1, T1> relation = new HashFRelation<>(n * n);
		for (final T1 i : matrix.universe1())
		{
			for (final T1 j : matrix.universe1())
			{
				float sum1 = 0.0f;
				float sum2 = 0.0f;
				for (final T2 k : matrix.universe2())
				{
					final float value1 = matrix.strengthOfRelation(i, k);
					final float value2 = matrix.strengthOfRelation(j, k);

					sum1 += Math.min(value1, value2);
					sum2 += Math.max(value1, value2);
				}
				relation.add(i, j, sum1 / sum2);
			}
		}

		return relation;
	}
}
