package com.gildedgames.fuzzyjava.api.matrices;

import com.gildedgames.fuzzyjava.api.sets.relations.FRelationSet;

public interface FMatrix extends FRelationSet<Integer, Integer>
{
	int length();

	int width();

	FVector getColumn(int i);

	FVector getRow(int j);

	void setMembership(int i, int j, float membership);

	void clear();
}
