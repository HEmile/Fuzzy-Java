package com.gildedgames.fuzzyjava.core.evaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gildedgames.fuzzyjava.api.evaluation.FContFunc;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncAnt;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncCons;
import com.gildedgames.fuzzyjava.api.evaluation.FFuncProp;
import com.gildedgames.fuzzyjava.api.evaluation.IProperty;
import com.gildedgames.fuzzyjava.api.evaluation.IRule;
import com.gildedgames.fuzzyjava.api.evaluation.IRuleSet;
import com.gildedgames.fuzzyjava.api.evaluation.Parameter;
import com.gildedgames.fuzzyjava.api.evaluation.Variable;
import com.gildedgames.fuzzyjava.api.functions.FFunction;
import com.gildedgames.fuzzyjava.api.functions.FFunctionOps;
import com.gildedgames.fuzzyjava.core.evaluation.exceptions.InvalidNumberOfArgumentsException;
import com.gildedgames.fuzzyjava.core.functions.StandardFunctionOps;
import com.gildedgames.fuzzyjava.util.Pair;

public class RuleSet<E> implements IRuleSet<E>
{
	private final List<IRule<E, E>> rules = new ArrayList<IRule<E, E>>();

	private final List<E> universe;

	private static final FFunctionOps fBuilder = new StandardFunctionOps();

	/**
	 * Amount of times the algorithm tries to infer the interpretation of variables.
	 * Higher *could* in some specific cases probably result in better accuracy, but 
	 * at a great performance cost.
	 */
	private static final int varInferrenceIterations = 2;

	public RuleSet(Collection<E> universe)
	{
		this.universe = new ArrayList<E>(universe);
	}

	@Override
	public void addRule(FFuncAnt<E> antecedent, FFuncCons<E> consequent)
	{
		this.rules.add(new Rule<E, E>(antecedent, consequent));
	}

	@Override
	public float valueOf(IProperty<E> searching, Object[] value)
	{
		return this.inferProperty(searching, value, new HashSet<Entry<Object[], IProperty<E>>>());
	}

	private float inferProperty(IProperty<E> searching, Object[] parameters, Set<Entry<Object[], IProperty<E>>> inferred)
	{
		inferred.add(new Pair<Object[], IProperty<E>>(parameters, searching));

		//Find all rules that have searching as its consequent

		final List<IRule<E, E>> applicableRules = this.applicableRules(searching, this.rules);

		FFunction<Float> aggregate = null;

		for (final IRule<E, E> rule : applicableRules)
		{
			final FFunction<Float> func = this.evaluateRule(rule, searching, parameters, inferred);
			if (func != null)
			{
				if (aggregate == null)
				{
					aggregate = func;
				}
				else
				{
					aggregate = fBuilder.or(func, aggregate);
				}
			}
		}
		if (aggregate == null)
		{
			return 0.0f;
		}

		//Defuzzify 
		return fBuilder.defuzzify(aggregate, searching.getMinBound(), searching.getMaxBound());
	}

	private FFunction<Float> evaluateRule(IRule<E, E> rule, IProperty<E> searching, Object[] parameters, Set<Entry<Object[], IProperty<E>>> inferred)
	{
		//Perform backwards propagation to find all rules
		//that are somehow applicable.
		final FFuncAnt<E> antFunc = rule.getAntecedent();
		final FFuncCons<E> consFunc = rule.getConsequent();
		final Map<Variable, Object> interpretation = new HashMap<Variable, Object>();

		final Variable[] consVariables = consFunc.variables();
		final Set<Variable> missingVars = new HashSet<Variable>();

		for (int i = 0; i < searching.arity(); i++)
		{
			final Variable variable = consVariables[i];
			final Object param = parameters[i];
			if (param != null)
			{
				interpretation.put(variable, param);
			}
			else
			{
				missingVars.add(variable);
			}
		}

		final Set<Entry<IProperty<E>, Parameter[]>> vars = antFunc.propertiesWithVars();

		//Infer variables by using knowledge of how the property works.
		//This is used in rules where information of an element needs to
		//be exposed, such as at(A, x, y) and near(B, x, y) -> fears(A, B)
		//This will assign x and y with the value of the position of A.
		//This is usually only used for boolean valued properties.

		//The amount of iterations is used when you have information
		//about a new interpretation, then you may be able to infer more.
		for (int i = 0; i < varInferrenceIterations; i++)
		{
			for (final Entry<IProperty<E>, Parameter[]> propnVar : vars)
			{
				final IProperty<E> prop = propnVar.getKey();
				final Parameter[] params = propnVar.getValue();
				if (prop.arity() != params.length)
				{
					throw new InvalidNumberOfArgumentsException();
				}

				boolean hasInterpretation = true;
				final Object[] interpOfParams = new Object[prop.arity()];

				for (int j = 0; j < prop.arity(); j++)
				{
					boolean hasParamInterpretation = true;
					final Parameter param = params[j];
					for (final Variable var : param.variables())
					{
						final boolean hasKey = interpretation.containsKey(var);
						hasInterpretation = hasKey && hasInterpretation;
						hasParamInterpretation = hasKey && hasParamInterpretation;
					}
					if (hasParamInterpretation)
					{
						interpOfParams[j] = param.getValue(interpretation);
					}
				}

				if (!hasInterpretation)
				{
					//If it has no full interpretation, try to see if some vars 
					//can be inferred by the property.
					final Object[] inferredVars = prop.tryInferMissing(interpOfParams);
					for (int j = 0; j < prop.arity(); j++)
					{
						final Object o = inferredVars[j];
						if (interpOfParams[j] == null && o != null)
						{
							interpretation.putAll(params[j].tryInferVars(o));
						}
					}
				}
				else if (inferred.contains(new Pair<Object[], IProperty<E>>(interpOfParams, prop)))//For preventing endless loop
				{
					//If we have already tried inferring a property for the given elements, stop
					//trying to execute the query.
					return null;
				}
			}
		}

		for (final Entry<IProperty<E>, Parameter[]> entry : vars)
		{
			final Parameter[] params = entry.getValue();
			for (final Parameter p : params)
			{
				for (final Variable v : p.variables())
				{
					if (!interpretation.containsKey(v))
					{
						missingVars.add(v);
					}
				}
			}
		}

		if (this.universe.isEmpty() && !missingVars.isEmpty())
		{
			return null;
		}

		FFunction<Float> aggregate = null;

		if (!missingVars.isEmpty())
		{
			//Iterate over all possible interpretation given the variables that ARE known.
			for (final Map<Variable, ?> interpretationIt : this.variableIterator(missingVars, interpretation))
			{
				final float membership = this.getMembership(antFunc, vars, interpretationIt, inferred);
				if (membership == 0)
				{
					continue;
				}
				final FFunction<Float> cut = this.cut(rule.getConsequent(), membership);

				//Aggregate all the functions for the
				//proposition.
				if (aggregate == null)
				{
					aggregate = cut;
				}
				else
				{
					aggregate = fBuilder.or(aggregate, cut);
				}
			}
		}
		else
		{
			final float membership = this.getMembership(antFunc, vars, interpretation, inferred);
			if (membership == 0)
			{
				return null;
			}
			aggregate = this.cut(rule.getConsequent(), membership);
		}
		return aggregate;
	}

	private float getMembership(FFuncAnt<E> ant, Set<Entry<IProperty<E>, Parameter[]>> vars, Map<Variable, ?> interpretation, Set<Entry<Object[], IProperty<E>>> inferred)
	{
		for (final Entry<IProperty<E>, Parameter[]> propnVar : vars)
		{
			final IProperty<E> prop = propnVar.getKey();
			final Object[] propInterp = new Object[prop.arity()];
			final Parameter[] params = propnVar.getValue();
			for (int j = 0; j < prop.arity(); j++)
			{
				propInterp[j] = params[j].getValue(interpretation);
			}
			//If the thing we are looking for is not
			//yet aware of the property that is required
			//for the rule to evaluate, try to find
			//it through recursion.
			//AKA: Backwards chaining :)
			if (!prop.propertySet(propInterp))
			{
				final float crisp = this.inferProperty(prop, propInterp, new HashSet<Entry<Object[], IProperty<E>>>(inferred));
				prop.setProperty(crisp, propInterp);
			}
		}
		return ant.evaluate(interpretation);
	}

	private Iterable<Map<Variable, ?>> variableIterator(Set<Variable> missingVars, final Map<Variable, Object> foundVars)
	{
		final List<Variable> missingVarsL = new ArrayList<Variable>(missingVars);
		return new Iterable<Map<Variable, ?>>()
		{

			@Override
			public Iterator<Map<Variable, ?>> iterator()
			{
				return new Iterator<Map<Variable, ?>>()
				{
					int[] indices;

					E first = RuleSet.this.universe.get(0);

					@Override
					public boolean hasNext()
					{
						return this.indices == null || this.indices[this.indices.length - 1] < RuleSet.this.universe.size();
					}

					@Override
					public Map<Variable, ?> next()
					{
						if (this.indices == null)
						{
							this.indices = new int[missingVarsL.size()];
							Arrays.fill(this.indices, 0);
							final E el = this.first;
							for (final Variable v : missingVarsL)
							{
								foundVars.put(v, el);
							}
							return foundVars;
						}
						for (int index = 0; index < this.indices.length; index++)
						{
							final Variable v = missingVarsL.get(index);
							this.indices[index]++;
							final int indexInUniverse = this.indices[index];
							if (indexInUniverse == RuleSet.this.universe.size())
							{
								this.indices[index] = 0;
								foundVars.put(v, this.first);
							}
							else
							{
								foundVars.put(v, RuleSet.this.universe.get(indexInUniverse));
								return foundVars;
							}
						}
						return null;
					}

				};
			}

		};
	}

	private <F, G> List<IRule<F, G>> applicableRules(IProperty<G> searching, List<IRule<F, G>> list)
	{
		final List<IRule<F, G>> rules = new ArrayList<IRule<F, G>>();
		for (final IRule<F, G> rule : list)
		{
			final IProperty<G> property = rule.getConsequent().getProperty();
			if (property.equals(searching) || property.getName().equals(searching.getName()))
			{
				rules.add(rule);
			}
		}
		return rules;
	}

	private FFunction<Float> cut(final FContFunc<E> func, final float value)
	{
		return new FFunction<Float>()
		{
			@Override
			public float membershipOf(Float element)
			{
				return Math.min(value, func.membershipOfFloat(element));
			}
		};
	}

	@Override
	public E[] missing(FFuncProp<E> func, Object... parameters)
	{
		//TODO!
		return null;
	}

	@Override
	public E missingSnd(FFuncProp<E> func, E first)
	{
		return null;
	}

	@Override
	public E missingFst(FFuncProp<E> func, E second)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
