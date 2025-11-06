package com.tecsup.petclinic.exceptions;

/**
 * Exception for Specialty not found
 * 
 * @author jgomezm
 *
 */
public class SpecialtyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpecialtyNotFoundException(String message) {
		super(message);
	}
	
}
