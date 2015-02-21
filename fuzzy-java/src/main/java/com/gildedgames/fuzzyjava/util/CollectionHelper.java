package com.gildedgames.fuzzyjava.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionHelper
{
	public static List<Integer> rangeOf(int i, int j)
	{
		final List<Integer> set = new ArrayList<Integer>(j - i);
		for (int c = i; c < j; c++)
		{
			set.add(c);
		}
		return set;
	}

	public static List<Integer> rangeTo(int length)
	{
		return rangeOf(0, length);
	}
}
