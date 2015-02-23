package com.gildedgames.fuzzyjava.api.evaluation;

import com.gildedgames.fuzzyjava.api.functions.FFunction;

public interface IPropBuilder
{
	<E> FFuncAntecedent<E> and(FFuncAntecedent<E> function1, FFuncAntecedent<E> function2);

	<E> FFuncAntecedent<E> or(FFuncAntecedent<E> function1, FFuncAntecedent<E> function2);

	<E> FFuncAntecedent<E> implies(FFuncAntecedent<E> function1, FFuncAntecedent<E> function2);

	<E> FFuncAntecedent<E> not(FFuncAntecedent<E> function);

	<E> FFuncConsequent<E> notC(FFuncConsequent<E> function);

	<E> FFuncAntecedent<E> constant(FFuncAntecedent<E> function, E constant);

	<E> FFuncAntecedent<E> constant(float constant);

	<E> FFuncProp<E> toPropFunc(FFunction<Float> function, IProperty<E> property);
}
