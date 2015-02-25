package com.gildedgames.fuzzyjava.api.evaluation;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IPropBuilder
{
	<E> FFuncAnt<E> and(FFuncAnt<E> function1, FFuncAnt<E> function2);

	<E> FFuncAnt<E> or(FFuncAnt<E> function1, FFuncAnt<E> function2);

	<E> FFuncAnt<E> implies(FFuncAnt<E> function1, FFuncAnt<E> function2);

	<E> FFuncAnt<E> not(FFuncAnt<E> function);

	<E> FFuncCons<E> notC(FFuncCons<E> function);

	Parameter constant(Object constant);

	Variable createVar();

	<E> FFuncProp<E> prop(FFunction<Float> function, IProperty<E> property);

	<E> FFuncAnt<E> very(FFuncAnt<E> function);

	<E> FFuncAnt<E> slightly(FFuncAnt<E> function);

	<E> FFuncAnt<E> ant(FFunction<E> function, IProperty<E> property, Parameter param);

	<E> FFuncAnt<E> ant(FFunction<E> function, Parameter param);

	<E> FFuncAnt<E> ant(FFunction<Object[]> function, IProperty<E> property, Parameter... param);

	<E> FFuncAnt<E> ant(FFunction<Object[]> function, Parameter... param);
}
