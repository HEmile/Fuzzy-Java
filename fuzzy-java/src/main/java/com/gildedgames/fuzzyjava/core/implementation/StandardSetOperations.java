package com.gildedgames.fuzzyjava.core.implementation;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.core.interfaces.FRelation;
import com.gildedgames.fuzzyjava.core.interfaces.FSetDiscrete;
import com.gildedgames.fuzzyjava.core.interfaces.FSetOperations;

public class StandardSetOperations implements FSetOperations
{

	@Override
	public <E> FSetDiscrete<E> complement(FSetDiscrete<E> set)
	{
		final FSetDiscrete<E> complement = new HashFSetDiscrete<E>(set.elementSize());
		for (final Entry<E, Float> entry : set.entrySet())
		{
			complement.add(entry.getKey(), 1.0f - entry.getValue());
		}
		return complement;
	}

	@Override
	public <E> FSetDiscrete<E> union(FSetDiscrete<E> set1, FSetDiscrete<? extends E> set2)
	{
		final FSetDiscrete<E> union = new HashFSetDiscrete<E>(set1.elementSize());
		for (final Entry<E, Float> entry : set1.entrySet())
		{
			union.add(entry.getKey(), entry.getValue());
		}

		for (final Entry<? extends E, Float> entry : set2.entrySet())
		{
			final E object = entry.getKey();
			final float setMembership = entry.getValue();
			final float currentMembership = union.membershipOf(object);
			if (currentMembership <= setMembership)
			{
				union.add(object, setMembership);
			}
		}
		return union;
	}

	@Override
	public <E> FSetDiscrete<E> intersection(FSetDiscrete<E> set1, FSetDiscrete<? extends E> set2)
	{
		final FSetDiscrete<E> intersection = new HashFSetDiscrete<E>(set1.elementSize());
		for (final Entry<? extends E, Float> entry : set2.entrySet())
		{
			final E element = entry.getKey();
			final float entryMembership = entry.getValue();
			final float unionMembership = Math.min(entryMembership, set1.membershipOf(element));
			intersection.add(element, unionMembership);
		}
		return intersection;
	}

	@Override
	public boolean contains(FSetDiscrete<?> set1, FSetDiscrete<?> set2)
	{
		for (final Entry<?, Float> entry : set2.entrySet())
		{
			final Object element = entry.getKey();
			final float entryMembership = entry.getValue();
			final float set1Membership = set1.membershipOf(element);
			if (entryMembership > set1Membership)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public <T1, T2> FRelation<T1, T2> cartesian(FSetDiscrete<T1> set1, FSetDiscrete<T2> set2)
	{
		final FRelation<T1, T2> cartesian = new HashFRelation<T1, T2>(set1.elementSize() * set2.elementSize());
		for (final Entry<T1, Float> entry1 : set1.entrySet())
		{
			final T1 element1 = entry1.getKey();
			final float membership1 = entry1.getValue();

			for (final Entry<T2, Float> entry2 : set2.entrySet())
			{
				final T2 element2 = entry2.getKey();
				final float membership2 = entry2.getValue();

				final float strength = Math.min(membership1, membership2);

				cartesian.add(element1, element2, strength);
			}
		}
		return cartesian;
	}

}
