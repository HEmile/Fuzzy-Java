package com.gildedgames.fuzzyjava.api.sets.relations;


public interface FRelationOperations
{

	<T1, T2, T3> FRelationSet<T1, T3> maxMinComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2);

	<T1, T2, T3> FRelationSet<T1, T3> maxProductComposition(FRelationSet<T1, T2> relation1, FRelationSet<T2, T3> relation2);

}
