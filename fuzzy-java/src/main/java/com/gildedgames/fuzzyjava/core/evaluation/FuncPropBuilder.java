package com.gildedgames.fuzzyjava.core.evaluation;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAntecedent;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncConsequent;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IPropBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.functions.FFunction;

public class FuncPropBuilder implements IPropBuilder
{

	@Override
	public <E> FFuncAntecedent<E> and(final FFuncAntecedent<E> function1, final FFuncAntecedent<E> function2)
	{
		return new FFuncAntecedent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return Math.min(function1.membershipOfEl(element), function2.membershipOfEl(element));
			}

			@Override
			public float membershipOf(Float element)
			{
				return Math.min(function1.membershipOf(element), function2.membershipOf(element));
			}
		};
	}

	@Override
	public <E> FFuncAntecedent<E> or(final FFuncAntecedent<E> function1, final FFuncAntecedent<E> function2)
	{
		return new FFuncAntecedent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return Math.max(function1.membershipOfEl(element), function2.membershipOfEl(element));
			}

			@Override
			public float membershipOf(Float element)
			{
				return Math.max(function1.membershipOf(element), function2.membershipOf(element));
			}
		};
	}

	@Override
	public <E> FFuncAntecedent<E> implies(final FFuncAntecedent<E> function1, final FFuncAntecedent<E> function2)
	{
		return new FFuncAntecedent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return Math.max(1.0f - function1.membershipOfEl(element), function2.membershipOfEl(element));
			}

			@Override
			public float membershipOf(Float element)
			{
				return Math.max(1.0f - function1.membershipOf(element), function2.membershipOf(element));
			}
		};
	}

	@Override
	public <E> FFuncAntecedent<E> not(final FFuncAntecedent<E> function)
	{
		return new FFuncAntecedent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return 1.0f - function.membershipOfEl(element);
			}

			@Override
			public float membershipOf(Float element)
			{
				return 1.0f - function.membershipOf(element);
			}
		};
	}

	@Override
	public <E> FFuncConsequent<E> notC(final FFuncConsequent<E> function)
	{
		return new FFuncConsequent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return 1.0f - function.membershipOfEl(element);
			}

			@Override
			public float membershipOf(Float element)
			{
				return 1.0f - function.membershipOf(element);
			}

			@Override
			public IProperty<E> getProperty()
			{
				return function.getProperty();
			}
		};
	}

	@Override
	public <E> FFuncAntecedent<E> constant(final FFuncAntecedent<E> function, final E constant)
	{
		return new FFuncAntecedent<E>()
		{
			@Override
			public float membershipOfEl(E element)
			{
				return function.membershipOfEl(constant);
			}

			@Override
			public float membershipOf(Float element)
			{
				return this.membershipOfEl(constant);
			}
		};
	}

	@Override
	public <E> FFuncAntecedent<E> constant(final float constant)
	{
		return new FFuncAntecedent<E>()
		{

			@Override
			public float membershipOfEl(E element)
			{
				return constant;
			}

			@Override
			public float membershipOf(Float element)
			{
				return constant;
			}

		};
	}

	@Override
	public <E> FFuncProp<E> toPropFunc(FFunction<Float> function, IProperty<E> property)
	{
		return new FuncProp<E>(function, property);
	}

}
