package com.gildedgames.fuzzyjava.api.evaluation;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IRuleBuilder
{
	<E> FFuncAnt<E> and(FFuncAnt<E>... functions);

	<E> FFuncAnt<E> or(FFuncAnt<E>... functions);

	<E> FFuncAnt<E> implies(FFuncAnt<E> function1, FFuncAnt<E> function2);

	/**
	 * Creates an and aggregation for all instances of var.
	 * Use this for queries like:
	 * forall(y, child(x, y) -> smart(y)), happy(x)
	 * This means: If all children of x are smart, then
	 * x is happy. The -> here is the implies function.
	 * 
	 * Note that if the universe is empty, the forall function
	 * will always return 1.
	 * 
	 * Elements that the var represent HAVE to be of the
	 * type of the universe. Furthermore, the var should not
	 * be used outside of the forall query, as it is a bounded
	 * variable. You can, however, use the same var in different
	 * foralls that do not bind eachother.
	 */
	<E> FFuncAnt<E> all(Variable var, FFuncAnt<E> function);

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
