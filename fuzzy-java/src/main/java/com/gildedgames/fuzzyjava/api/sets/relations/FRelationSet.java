package com.gildedgames.fuzzyjava.api.sets.relations;

import java.util.Collection;
import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.FSet;

public interface FRelationSet<T1, T2> extends FSet<Entry<T1, T2>>, FRelation<T1, T2>
{

	int length();

	int width();

	FSet<T2> getColumn(T1 i);

	FSet<T1> getRow(T2 j);

	Collection<T1> universe1();

	Collection<T2> universe2();

}
