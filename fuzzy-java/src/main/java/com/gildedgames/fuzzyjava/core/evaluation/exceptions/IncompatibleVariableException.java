package com.gildedgames.fuzzyjava.core.evaluation.exceptions;

public class IncompatibleVariableException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7504802927970008314L;

	public IncompatibleVariableException(String parameter, Object got)
	{
		super("Got incompatible variable " + parameter + " in proposition. Type of object was " + got.getClass().toGenericString());
	}
}
