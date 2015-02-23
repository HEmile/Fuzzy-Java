package com.gildedgames.fuzzyjava.core.evaluation.exceptions;

public class VariableMissingException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1750700854130654423L;

	public VariableMissingException(String variableMissing)
	{
		super("Missing variable " + variableMissing + " while evaluating proposition");
	}

}
