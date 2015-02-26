package com.gildedgames.fuzzyjava.core.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleBuilder;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.core.Ops;
import com.gildedgames.fuzzyjava.core.evaluation.exceptions.BoundedVarUsedFreeException;
import com.gildedgames.fuzzyjava.util.Pair;

/**
 * Uses tonnes of inner classes to build
 * new propositions out of multiple 
 * functions.
 * @author Emile
 *
 */
public class RuleBuilder implements IRuleBuilder
{

	private static abstract class FuncAntMerge<E> implements FFuncAnt<E>
	{
		private final Set<Entry<IProperty<E>, Parameter[]>> pWithVars;

		private FuncAntMerge(FFuncAnt<E>... fs)
		{
			this.pWithVars = new HashSet<>();
			for (final FFuncAnt<E> f : fs)
			{
				this.pWithVars.addAll(f.propertiesWithVars());
			}
		}

		@Override
		public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
		{
			return this.pWithVars;
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
	public <E> FFuncAnt<E> and(final FFuncAnt<E>... functions)
	{
		return new FuncAntMerge<E>(functions)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				float and = 1.0f;
				for (final FFuncAnt<E> f : functions)
				{
					and = Ops.and(and, f.membershipOf(element));
				}
				return and;
			}

			@Override
			public float evaluate(Map<Variable, ?> env, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				float and = 1.0f;
				for (final FFuncAnt<E> f : functions)
				{
					and = Ops.and(and, f.evaluate(env, ruleSet, inferred));
				}
				return and;
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> or(final FFuncAnt<E>... functions)
	{
		return new FuncAntMerge<E>(functions)
		{
			@Override
			public float membershipOf(Object[] element)
			{
				float or = 0.0f;
				for (final FFuncAnt<E> f : functions)
				{
					or = Ops.or(or, f.membershipOf(element));
				}
				return or;
			}

			@Override
			public float evaluate(Map<Variable, ?> env, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				float or = 0.0f;
				for (final FFuncAnt<E> f : functions)
				{
					or = Ops.or(or, f.evaluate(env, ruleSet, inferred));
				}
				return or;
			}
		};
	}

	@Override
	public <E> FFuncAnt<E> implies(final FFuncAnt<E> function1, final FFuncAnt<E> function2)
	{

		final Set<Entry<IProperty<E>, Parameter[]>> pWithVars = new HashSet<>(function1.propertiesWithVars());
		pWithVars.addAll(function2.propertiesWithVars());
		return new FFuncAnt<E>()
		{
			@Override
			public float membershipOf(Object[] element)
			{
				return Ops.or(Ops.not(function1.membershipOf(element)), function2.membershipOf(element));
			}

			@Override
			public float evaluate(Map<Variable, ?> env, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				return Ops.or(Ops.not(function1.evaluate(env, ruleSet, inferred)), function2.evaluate(env, ruleSet, inferred));
			}

			@Override
			public Set<Entry<IProperty<E>, Parameter[]>> propertiesWithVars()
			{
				return pWithVars;
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
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				return Ops.not(function.evaluate(interpretation, ruleSet, inferred));
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
			public FFuncProp<E> getPropFunc()
			{
				return function.getPropFunc();
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
		return new FuncProp<>(function, property);
	}

	@Override
	public <E> FFuncAnt<E> ant(final FFunction<E> function, final IProperty<E> property, final Parameter param)
	{
		final Set<Entry<IProperty<E>, Parameter[]>> set = new HashSet<>(1);
		if (property != null)
		{
			set.add(new Pair<>(property, new Parameter[] { param }));
		}
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
				return set;
			}

			@Override
			public float evaluate(Map<Variable, ?> env, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
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
		final Set<Entry<IProperty<E>, Parameter[]>> set = new HashSet<>();
		if (property != null)
		{
			set.add(new Pair<>(property, param));
		}
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
				return set;
			}

			@Override
			public float evaluate(Map<Variable, ?> env, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
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

	private static final Map<Variable, ?> emptyMap = new HashMap<>(0);

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
				return RuleBuilder.emptyMap;
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
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				return (float) Math.pow(function.evaluate(interpretation, ruleSet, inferred), 2);
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
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				return (float) Math.pow(function.evaluate(interpretation, ruleSet, inferred), 0.5f);
			}

		};
	}

	@Override
	public <E> FFuncAnt<E> all(final Variable var, final FFuncAnt<E> function)
	{
		final Set<Entry<IProperty<E>, Parameter[]>> set = function.propertiesWithVars();
		for (final Entry<IProperty<E>, Parameter[]> entry : set)
		{
			final Parameter[] params = entry.getValue();
			for (int i = 0; i < params.length; i++)
			{
				final Parameter param = params[i];
				boolean replace = false;
				for (final Variable varI : param.variables())
				{
					replace = replace || varI == var;
				}
				if (replace)
				{
					final Variable[] newVarA = new Variable[param.variables().length - 1];
					final int count = 0;
					for (final Variable varI : param.variables())
					{
						if (varI != var)
						{
							newVarA[count] = varI;
						}
					}
					params[i] = new Parameter()
					{
						@Override
						public Variable[] variables()
						{
							return newVarA;
						}

						@Override
						public Object getValue(Map<Variable, ?> env)
						{
							return param.getValue(env);
						}

						@Override
						public Map<Variable, ?> tryInferVars(Object paramValue)
						{
							return RuleBuilder.emptyMap;
						}
					};
				}
			}
		}
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
				return set;
			}

			@Override
			public float evaluate(Map<Variable, ?> interpretation, IRuleSet<E> ruleSet, Set<Entry<List<Object>, IProperty<E>>> inferred)
			{
				if (interpretation.containsKey(var))
				{
					throw new BoundedVarUsedFreeException();
				}
				//Go over the universe and create a new interpretation for the var.
				final Map<Variable, Object> newInterpretation = new HashMap<>(interpretation);
				float and = 1.0f;
				for (final E el : ruleSet.getUniverse())
				{
					newInterpretation.put(var, el);
					final float membership = ruleSet.getMembership(function, newInterpretation, inferred);
					and = Ops.and(and, membership);
				}
				return and;
			}
		};
	}
}
