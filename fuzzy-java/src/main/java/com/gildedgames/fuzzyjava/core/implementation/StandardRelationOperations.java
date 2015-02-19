package com.gildedgames.fuzzyjava.core.implementation;

import com.gildedgames.fuzzyjava.core.interfaces.FRelation;
import com.gildedgames.fuzzyjava.core.interfaces.FRelationEntry;
import com.gildedgames.fuzzyjava.core.interfaces.FRelationOperations;

public class StandardRelationOperations implements FRelationOperations
{

	@Override
	public <T1, T2> FRelation<T1, T2> complement(FRelation<T1, T2> set)
	{
		final FRelation<T1, T2> complement = new HashFRelation<T1, T2>(set.elementSize());
		for (final FRelationEntry<T1, T2> entry : set.entrySet())
		{
			complement.add(entry.element1(), entry.element2(), 1.0f - entry.strength());
		}
		return complement;
	}

	@Override
	public <T1, T2> FRelation<T1, T2> union(FRelation<T1, T2> set1, FRelation<? extends T1, ? extends T2> set2)
	{
		final FRelation<T1, T2> union = new HashFRelation<T1, T2>(set1.elementSize());
		for (final FRelationEntry<T1, T2> entry : set1.entrySet())
		{
			union.add(entry.element1(), entry.element2(), 1.0f - entry.strength());
		}

		for (final FRelationEntry<? extends T1, ? extends T2> entry : set2.entrySet())
		{
			final T1 element1 = entry.element1();
			final T2 element2 = entry.element2();
			final float setMembership = entry.strength();
			final float currentMembership = union.strengthOfRelation(element1, element2);
			if (currentMembership <= setMembership)
			{
				union.add(element1, element2, setMembership);
			}
		}
		return union;
	}

	@Override
	public <T1, T2> FRelation<T1, T2> intersection(FRelation<T1, T2> set1, FRelation<? extends T1, ? extends T2> set2)
	{
		final FRelation<T1, T2> intersection = new HashFRelation<T1, T2>(set1.elementSize());
		for (final FRelationEntry<? extends T1, ? extends T2> entry : set2.entrySet())
		{
			final T1 element1 = entry.element1();
			final T2 element2 = entry.element2();
			final float entryMembership = entry.strength();
			final float unionMembership = Math.min(entryMembership, set1.strengthOfRelation(element1, element2));
			intersection.add(element1, element2, unionMembership);
		}
		return intersection;
	}

	@Override
	public boolean contains(FRelation<?, ?> set1, FRelation<?, ?> set2)
	{
		for (final FRelationEntry<?, ?> entry : set2.entrySet())
		{
			final Object element1 = entry.element1();
			final Object element2 = entry.element2();
			final float entryMembership = entry.strength();
			final float set1Membership = set1.strengthOfRelation(element1, element2);
			if (entryMembership > set1Membership)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public <T1, T2, T3> FRelation<T1, T3> maxMinComposition(FRelation<T1, T2> relation1, FRelation<T2, T3> relation2)
	{
		final FRelation<T1, T3> newRelation = new HashFRelation<T1, T3>();

		for (final FRelationEntry<T1, T2> entrya : relation1.entrySet())
		{
			final T1 elementa1 = entrya.element1();
			final T2 elementa2 = entrya.element2();
			final float strengtha = entrya.strength();
			for (final FRelationEntry<T2, T3> entryb : relation2.entrySet())
			{
				final T2 elementb1 = entryb.element1();
				if (elementa2.equals(elementb1))
				{
					final T3 elementb2 = entryb.element2();
					final float strengthb = entryb.strength();

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
	public <T1, T2, T3> FRelation<T1, T3> maxProductComposition(FRelation<T1, T2> relation1, FRelation<T2, T3> relation2)
	{
		final FRelation<T1, T3> newRelation = new HashFRelation<T1, T3>();

		for (final FRelationEntry<T1, T2> entrya : relation1.entrySet())
		{
			final T1 elementa1 = entrya.element1();
			final T2 elementa2 = entrya.element2();
			final float strengtha = entrya.strength();
			for (final FRelationEntry<T2, T3> entryb : relation2.entrySet())
			{
				final T2 elementb1 = entryb.element1();
				if (elementa2.equals(elementb1))
				{
					final T3 elementb2 = entryb.element2();
					final float strengthb = entryb.strength();

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

}
