package com.qburst.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailValidation 
{
	private static final String EMAILEXP = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern;
	private Matcher matcher;

	public emailValidation() {
		// initialize the Pattern object
		pattern = Pattern.compile(EMAILEXP, Pattern.CASE_INSENSITIVE);
	}
	
	public boolean emailValidator(String email) {
		//System.out.println("hii");
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
