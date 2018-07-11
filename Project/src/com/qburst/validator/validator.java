package com.qburst.validator;

import com.qburst.model.*;

public class validator 
{
	public validation validate(StudentData s)throws Exception
	{
		validation valid = new validation();
		emailValidation email = new emailValidation();
		passwordValidation password = new passwordValidation();
		if(email.emailValidator(s.email)==true && password.passwordValidator(s.password)==true)
		{
			valid.setValidity(1);
		}
		if(email.emailValidator(s.email)==true && password.passwordValidator(s.password)==false)
		{
			valid.setValidity(2);
		}
		if(email.emailValidator(s.email)==false && password.passwordValidator(s.password)==true)
		{
			valid.setValidity(3);
		}
		if(email.emailValidator(s.email)==false && password.passwordValidator(s.password)==false)
		{
			valid.setValidity(4);
		}
		return valid;
	}
}
