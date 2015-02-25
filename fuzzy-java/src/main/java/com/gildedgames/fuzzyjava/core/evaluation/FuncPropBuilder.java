package com.gildedgames.fuzzyjava.core.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IPropBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.core.Ops;
import com.gildedgames.fuzzyjava.util.Pair;

/**
 * Uses tonnes of inner classes to build
 * new propositions out of multiple 
 * functions.
 * @author Emile
 *
 */
public class FuncPropBuilder implements IPropBuilder
{

	private static abstract class FuncAntMerge<E> implements FFuncAnt<E>
	{
		private final FFuncAnt<E> f1, f2;

		private FuncAntMerge(FFuncAnt<E> f1, FFuncAnt<E> f2)
		{
			this.f1 = f1;
			this.f2 = f2;
		}

		@Override
		public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
		{
			final Set<Entry<IProperty<E>, Parameter[]>> ret = new HashSet<Entry<IProperty<E>, Parameter[]>>(this.f1.propertiesWithVars());
			ret.addAll(this.f2.propertiesWithVars());
			return ret;
		}
	}

	private static abstract class FuncAntBase<E> implements FFuncAnt<E>
	{
		private final FFuncAnt<E> func;

		private FuncAntBase(FFuncAnt<E> func)
		{
			this.func = func;
		}

		@Override
		public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
		{
			return this.func.propertiesWithVars();
		}

	}

	@Override
	public <E> FFuncAnt<E> and(final FFuncAnt<E> function1, final FFuncAnt<E> function2)
	{
		return new FuncAntMerge<E>(function1, function2)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.and(function1.membershipOf(element), function2.membershipOf(element));
			}

			@Override
			public float evaluate(Map<Variable, ?> env)
			{
				return Ops.and(function1.evaluate(env), function2.evaluate(env));
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> or(final FFuncAnt<E> function1, final FFuncAnt<E> function2)
	{
		return new FuncAntMerge<E>(function1, function2)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.or(function1.membershipOf(element), function2.membershipOf(element));
			}

			@Override
			public float evaluate(Map<Variable, ?> env)
			{
				return Ops.or(function1.evaluate(env), function2.evaluate(env));
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> implies(final FFuncAnt<E> function1, final FFuncAnt<E> function2)
	{
		return new FuncAntMerge<E>(function1, function2)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.or(Ops.not(function1.membershipOf(element)), function2.membershipOf(element));
			}

			@Override
			public float evaluate(Map<Variable, ?> env)
			{
				return Ops.or(Ops.not(function1.evaluate(env)), function2.evaluate(env));
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> not(final FFuncAnt<E> function)
	{
		return new FuncAntBase<E>(function)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.not(function.membershipOf(element));
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation)
			{
				return Ops.not(function.evaluate(interpretation));
			}
		};
	}

	@Override
	public <E> FFuncCons<E> notC(final FFuncCons<E> function)
	{
		return new FFuncCons<E>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.not(function.membershipOf(element));
			}

			@Override
			public float membershipOfFloat(float element)
			{
				return Ops.not(function.membershipOfFloat(element));
			}

			@Override
			public IProperty<E> getProperty()
			{
				return function.getProperty();
			}

			@Override
			public Variable[] variables()
			{
				return function.variables();
			}
		};
	}

	@Override
	public <E> FFuncProp<E> prop(FFunction<Float> function, IProperty<E> property)
	{
		return new FuncProp<E>(function, property);
	}

	@Override
	public <E> FFuncAnt<E> ant(final FFunction<E> function, final IProperty<E> property, final Parameter param)
	{
		return new FFuncAnt<E>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return function.membershipOf((E) element[0]);
			}

			@Override
			public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
			{
				final Set<Entry<IProperty<E>, Parameter[]>> set = new HashSet<Entry<IProperty<E>, Parameter[]>>(1);
				if (property != null)
				{
					set.add(new Pair<IProperty<E>, Parameter[]>(property, new Parameter[] { param }));
				}
				return set;
			}

			@Override
			public float evaluate(Map<Variable, ?> env)
			{
				return function.membershipOf((E) param.getValue(env));
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> ant(FFunction<E> function, Parameter param)
	{
		return this.ant(function, null, param);
	}

	@Override
	public <E> FFuncAnt<E> ant(final FFunction<Object[]> function, final IProperty<E> property, final Parameter... param)
	{
		return new FFuncAnt<E>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return function.membershipOf(element);
			}

			@Override
			public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
			{
				final Set<Entry<IProperty<E>, Parameter[]>> set = new HashSet<Entry<IProperty<E>, Parameter[]>>();
				if (property != null)
				{
					set.add(new Pair<IProperty<E>, Parameter[]>(property, param));
				}
				return set;
			}

			@Override
			public float evaluate(Map<Variable, ?> env)
			{
				final Object[] els = new Object[param.length];
				for (int i = 0; i < param.length; i++)
				{
					els[i] = param[i].getValue(env);
				}
				return this.membershipOf(els);
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> ant(FFunction<Object[]> function, Parameter... param)
	{
		return this.ant(function, null, param);
	}

	@Override
	public Parameter constant(final Object constant)
	{
		return new Parameter()
		{
			@Override
			public Variable[] variables()
			{
				return new Variable[0];
			}

			@Override
			public Object getValue(Map<Variable, ?> env)
			{
				return constant;
			}

			@Override
			public Map<Variable, ?> tryInferVars(Object paramValue)
			{
				return new HashMap<Variable, Object>(0);
			}
		};
	}

	@Override
	public Variable createVar()
	{
		return new Variable();
	}

	@Override
	public <E> FFuncAnt<E> very(final FFuncAnt<E> function)
	{
		return new FuncAntBase<E>(function)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return (float) Math.pow(function.membershipOf(element), 2);
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation)
			{
				return (float) Math.pow(function.evaluate(interpretation), 2);
			}

		};
	}

	@Override
	public <E> FFuncAnt<E> slightly(final FFuncAnt<E> function)
	{
		return new FuncAntBase<E>(function)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return (float) Math.pow(function.membershipOf(element), 0.5f);
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation)
			{
				return (float) Math.pow(function.evaluate(interpretation), 0.5f);
			}

		};
	}

}
