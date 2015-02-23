package com.gildedgames.fuzzyjava.core.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.sets.FSet;
import com.gildedgames.fuzzyjava.api.sets.FSetMut;
import com.gildedgames.fuzzyjava.api.sets.FSetOps;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelation;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationMut;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;

public class StandardSetOperations implements FSetOps
{

	@Override
	public <E> FSet<E> complement(FSet<E> set)
	{
		final FSetMut<E> complement = new HashFSetMut<E>(set.size());
		for (final Entry<E, Float> entry : set)
		{
			complement.add(entry.getKey(), 1.0f - entry.getValue());
		}
		return complement;
	}

	@Override
	public <E> FSet<E> union(FSet<? extends E> set1, FSet<? extends E> set2)
	{
		final FSetMut<E> union = new HashFSetMut<E>(set1.size());
		for (final Entry<? extends E, Float> entry : set1)
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
	public <E> FSet<E> intersection(FSet<E> set1, FSet<? extends E> set2)
	{
		final FSetMut<E> intersection = new HashFSetMut<E>(set1.size());
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
	public <E> boolean contains(FSet<E> set1, FSet<? extends E> set2)
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
	public <T1, T2> FRelationSet<T1, T2> cartesian(FSet<T1> set1, FSet<T2> set2)
	{
		final FRelationMut<T1, T2> cartesian = new HashFRelation<T1, T2>(set1.size() * set2.size());
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

	@Override
	public <E> float getHeight(FSet<E> set)
	{
		float height = 0.0f;
		for (final Entry<E, Float> entry : set)
		{
			height = Math.max(height, entry.getValue());
		}
		return height;
	}

	@Override
	public <E> Collection<E> alphaCut(FSet<E> set, float alpha)
	{
		final Set<E> crispSet = new HashSet<E>((int) (set.size() * alpha));
		for (final Entry<E, Float> entry : set)
		{
			if (entry.getValue() >= alpha)
			{
				crispSet.add(entry.getKey());
			}
		}
		return crispSet;
	}

	@Override
	public <E> Collection<E> cutAllZero(FSet<E> set)
	{
		final Set<E> crispSet = new HashSet<E>(set.size() / 2);
		for (final Entry<E, Float> entry : set)
		{
			if (entry.getValue() > 0.0f)
			{
				crispSet.add(entry.getKey());
			}
		}
		return crispSet;
	}

	@Override
	public <T1, T2> FRelation<T1, T2> ifThenStrength(FSet<T1> theIf, FSet<T2> theThen)
	{
		final FRelationMut<T1, T2> relation = new HashFRelation<T1, T2>(theIf.size() * theThen.size());
		for (final T1 x : theIf.universe())
		{
			for (final T2 y : theThen.universe())
			{
				final float membershipX = theIf.membershipOf(x);
				final float membershipY = theThen.membershipOf(y);
				relation.add(x, y, Math.max(Math.min(membershipX, membershipY), 1.0f - membershipX));
			}
		}
		return relation;
	}

	@Override
	public <T1, T2> FRelation<T1, T2> ifThenElseStrength(FSet<T1> theIf, FSet<T2> theThen, FSet<T2> theElse)
	{
		final FRelationMut<T1, T2> relation = new HashFRelation<T1, T2>(theIf.size() * theThen.size());
		for (final T1 x : theIf.universe())
		{
			for (final T2 y : theThen.universe())
			{
				final float membershipX = theIf.membershipOf(x);
				final float membershipThenY = theThen.membershipOf(y);
				final float membershipElseY = theElse.membershipOf(y);
				final float ante = Math.min(membershipX, membershipThenY);
				final float conse = Math.min(1 - membershipX, membershipElseY);
				relation.add(x, y, Math.max(ante, conse));
			}
		}
		return relation;
	}

	@Override
	public <T1, T2> FSet<T2> composition(FSet<T1> set, FRelationSet<T1, T2> relation)
	{
		final FSetMut<T2> toReturn = new HashFSetMut<T2>(relation.length());
		for (final T2 j : relation.universe2())
		{
			float maxvalue = 0.0f;
			for (final T1 i : set.universe())
			{
				final float minvalue = Math.min(set.membershipOf(i), relation.strengthOfRelation(i, j));
				maxvalue = Math.max(maxvalue, minvalue);
			}
		}
		return toReturn;
	}
}
