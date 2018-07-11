package com.qburst.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class passwordValidation 
{
private static final String PASS_REGEX	= "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	
	private static Pattern pattern;
	private Matcher matcher;
	public passwordValidation() {
		pattern = Pattern.compile(PASS_REGEX);
	}
	
	public boolean passwordValidator(String pass) {
		/*String pass;
		pass = s.getPass();*/
		matcher = pattern.matcher(pass);
		return matcher.matches();
	}
}
