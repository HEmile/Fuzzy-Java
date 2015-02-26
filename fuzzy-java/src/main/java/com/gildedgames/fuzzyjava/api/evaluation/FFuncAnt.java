package com.gildedgames.fuzzyjava.api.evaluation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface FFuncAnt<E> extends FFunction<Object[]>
{
	Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars();

	float evaluate(Map<Variable, ?> interpretation, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred);
}
