package com.gildedgames.fuzzyjava.core.sets;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.FSetDiscr;
import com.gildedgames.fuzzyjava.api.sets.FSetOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;

public class StandardSetOperations implements FSetOperations
{

	@Override
	public <E> FSetDiscr<E> complement(FSetDiscr<E> set)
	{
		final FSetDiscr<E> complement = new HashFSetDiscrete<E>(set.size());
		for (final Entry<E, Float> entry : set)
		{
			complement.add(entry.getKey(), 1.0f - entry.getValue());
		}
		return complement;
	}

	@Override
	public <E> FSetDiscr<E> union(FSetDiscr<E> set1, FSetDiscr<? extends E> set2)
	{
		final FSetDiscr<E> union = new HashFSetDiscrete<E>(set1.size());
		for (final Entry<E, Float> entry : set1)
		{
			union.add(entry.getKey(), entry.getValue());
		}

		for (final Entry<? extends E, Float> entry : set2)
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
	public <E> FSetDiscr<E> intersection(FSetDiscr<E> set1, FSetDiscr<? extends E> set2)
	{
		final FSetDiscr<E> intersection = new HashFSetDiscrete<E>(set1.size());
		for (final Entry<? extends E, Float> entry : set2)
		{
			final E element = entry.getKey();
			final float entryMembership = entry.getValue();
			final float unionMembership = Math.min(entryMembership, set1.membershipOf(element));
			intersection.add(element, unionMembership);
		}
		return intersection;
	}

	@Override
	public <E> boolean contains(FSetDiscr<E> set1, FSetDiscr<? extends E> set2)
	{
		for (final Entry<? extends E, Float> entry : set2)
		{
			final E element = entry.getKey();
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
	public <T1, T2> FRelationSet<T1, T2> cartesian(FSetDiscr<T1> set1, FSetDiscr<T2> set2)
	{
		final FRelationDiscr<T1, T2> cartesian = new HashFRelation<T1, T2>(set1.size() * set2.size());
		for (final Entry<T1, Float> entry1 : set1)
		{
			final T1 element1 = entry1.getKey();
			final float membership1 = entry1.getValue();

			for (final Entry<T2, Float> entry2 : set2)
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
