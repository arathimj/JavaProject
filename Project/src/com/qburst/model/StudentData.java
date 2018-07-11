package com.qburst.model;

public class StudentData 
{
	public String first_name, last_name, email, password, sex; 
	public Integer student_id;
	public   Integer getStudentID() 
	{
		return student_id;
	}
	public String getFirstName() 
	{
		return first_name;
	}
	public String getLastName() 
	{
		return last_name;
	}
	public String getEmail() 
	{
		return email;
	}
	public String getPassword() 
	{
		return password;
	}
	public String getSex() 
	{
		return sex;
	}
	
	public void setStudentID(Integer student_id) 
	{
		this.student_id = student_id;
	}
	public void setFirstName(String first_name) 
	{
		this.first_name = first_name;
	}
	
	public void setLastName(String last_name) 
	{
		this.last_name = last_name;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public void setSex(String sex) 
	{
		this.sex = sex;
	}
}
