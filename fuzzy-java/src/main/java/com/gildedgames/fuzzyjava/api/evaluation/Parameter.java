package com.gildedgames.fuzzyjava.api.evaluation;

import java.util.Map;

public interface Parameter
{
	Variable[] variables();

	Object getValue(Map<Variable, ?> env);

	Map<Variable, ?> tryInferVars(Object paramValue);
}
