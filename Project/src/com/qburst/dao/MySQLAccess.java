package com.qburst.dao;

import java.io.InputStream;
import java.security.MessageDigest;
import com.qburst.model.StudentData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.model.StudentData;

public class MySQLAccess {
	
  private Connection connect = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  public static final String SALT = "my-salt-text";
  StringBuilder hash = new StringBuilder();

  public void databaseConnection() throws Exception 
  {
	  Properties prop = new Properties();
	    InputStream input = null;

	    try {

	    	input = this.getClass().getClassLoader().getResourceAsStream("config.properties");
	    	prop.load(input);
	    }
	    finally
	    {
	    	
	    }
	  Class.forName("com.mysql.cj.jdbc.Driver");
	  connect = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + "?" + "user=" + prop.getProperty("user") + "&password=" + prop.getProperty("passwd") );
  }
  public List<StudentData> readfullDataBase(int start, int max) throws Exception 
  {
	  preparedStatement=connect.prepareStatement("select * from StudentTable limit "+(start-1)+","+max+";");
	  resultSet=preparedStatement.executeQuery();
	  

	  List<StudentData> list=new ArrayList<StudentData>();
	  try
	  {
		  while (resultSet.next())
		  {
			  StudentData s=new StudentData();
			  s.setStudentID(resultSet.getInt(1));
			  s.setFirstName(resultSet.getString(2));
			  s.setLastName(resultSet.getString(3));
			  s.setEmail(resultSet.getString(4));
			  s.setSex(resultSet.getString(6));

			  list.add(s);
		  }
	  }
	  catch(Exception e)
	  {
		  
	  }
	return list;
  }
  public void insertDataBase(StudentData s) throws Exception 
  {
	  
	  MessageDigest sha = MessageDigest.getInstance("SHA-1");
	  //System.out.println(inputjson);
	  preparedStatement = connect.prepareStatement("insert into  StudentTable values (default, ?, ?, ?, ? , ?)");
	  
	  preparedStatement.setString(1, s.first_name);
	  preparedStatement.setString(2, s.last_name);
	  preparedStatement.setString(3, s.email);
	  String saltedPassword = SALT + s.password;
		byte[] hashedBytes = sha.digest(saltedPassword.getBytes());
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		
		for (int idx = 0; idx < hashedBytes.length;++idx) {
			byte b = hashedBytes[idx];
			hash.append(digits[(b & 0xf0) >> 4]);
			hash.append(digits[b & 0x0f]);
		}
	  preparedStatement.setString(4, hash.toString());
	  preparedStatement.setString(5, s.sex);
	  preparedStatement.executeUpdate();
	
  }
  public void deleteRecord(ServletInputStream inputjson) throws Exception
  {
	  ObjectMapper mapper = new ObjectMapper();
	  StudentData s = mapper.readValue(inputjson, StudentData.class);
	  String email = s.email;
	  String password = s.password;
	  preparedStatement = connect.prepareStatement("delete from StudentTable where (email='"+email+"' and password='"+password+"');");
	  Integer n = preparedStatement.executeUpdate();
	  
  }
  public void updateRecord(StudentData s) throws Exception
  {
	  MessageDigest sha = MessageDigest.getInstance("SHA-1");

	  preparedStatement = connect.prepareStatement("update StudentTable set first_name=?, last_name=?, email=?, password=?, sex=? where student_id=?");
	  preparedStatement.setString(1, s.first_name);
	  preparedStatement.setString(2, s.last_name);
	  preparedStatement.setString(3, s.email);
	  String saltedPassword = SALT + s.password;
		byte[] hashedBytes = sha.digest(saltedPassword.getBytes());
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		
		for (int idx = 0; idx < hashedBytes.length;++idx) {
			byte b = hashedBytes[idx];
			hash.append(digits[(b & 0xf0) >> 4]);
			hash.append(digits[b & 0x0f]);
		}
	  preparedStatement.setString(4, hash.toString());
	  preparedStatement.setString(5, s.sex);
	  preparedStatement.setInt(6, s.student_id);
	  Integer n = preparedStatement.executeUpdate();
	  
  }
  public Integer login(ServletInputStream inputjson) throws Exception
  {
	  
	  ObjectMapper mapper = new ObjectMapper();
	  StudentData s = mapper.readValue(inputjson, StudentData.class);
	  String email = s.email;
	  String password = s.password;
	  
	  preparedStatement = connect.prepareStatement("select * from StudentTable where (email='"+email+"' and password='"+password+"');");
	  ResultSet resultSet = preparedStatement.executeQuery();
	 
	  if(resultSet.next())
	  {
		  System.out.println("hiii");
		  return 1;
	  }
	  else
	  {
		  return 0;
	  }
  }
  public void close() 
  {
        try 
        {
          if (resultSet != null) 
          {
            resultSet.close();
          }

          if (connect != null) 
          {
            connect.close();
          }
        } 
        catch (Exception e) 
        {

        }
  }
}
  