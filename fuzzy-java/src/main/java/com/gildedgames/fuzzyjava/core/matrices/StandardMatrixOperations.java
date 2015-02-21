package com.gildedgames.fuzzyjava.core.matrices;

import com.gildedgames.fuzzyjava.api.matrices.FMatrix;
import com.gildedgames.fuzzyjava.api.matrices.FMatrixOperations;
import com.gildedgames.fuzzyjava.api.sets.relations.FRelation;
import com.gildedgames.fuzzyjava.core.sets.relations.HashFRelation;

public class StandardMatrixOperations implements FMatrixOperations
{

	@Override
	public FRelation<Integer, Integer> cosineAmplitude(FMatrix vectors)
	{
		if (vectors == null)
		{
			return new HashFRelation<Integer, Integer>(0);
		}
		final int n = vectors.width();
		final FMatrix relation = new ArrayFMatrix(n, n);
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				float dotProduct = 0.0f;
				float sum1 = 0.0f;
				float sum2 = 0.0f;
				for (int k = 0; k < vectors.length(); k++)
				{
					final float value1 = vectors.strengthOfRelation(i, k);
					final float value2 = vectors.strengthOfRelation(j, k);

					dotProduct += value1 * value2;
					sum1 += value1 * value1;
					sum2 += value2 * value2;
				}
				dotProduct = Math.abs(dotProduct);
				final float nom = (float) Math.sqrt(sum1 * sum2);//TODO: invalid cast?
				relation.setMembership(i, j, dotProduct / nom);
			}
		}

		return relation;
	}

}
