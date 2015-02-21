package com.gildedgames.fuzzyjava.core.sets.relations;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.relations.FRelationDiscr;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;

public class StandardRelationOperations implements FRelationOperations
{

	@Override
	public <T1, T2, T3> FRelationSet<T1, T3> maxMinComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2)
	{
		final FRelationDiscr<T1, T3> newRelation = new HashFRelation<T1, T3>();

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
		final FRelationDiscr<T1, T3> newRelation = new HashFRelation<T1, T3>();

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

}
