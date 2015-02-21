package com.gildedgames.fuzzyjava.api.sets.relations;

import java.util.Map.Entry;

import com.gildedgames.fuzzyjava.api.sets.FSet;

public interface FRelationSet<T1, T2> extends FSet<Entry<T1, T2>>, FRelation<T1, T2>
{

}
