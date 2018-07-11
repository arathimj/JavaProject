package com.qburst.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.dao.MySQLAccess;
import com.qburst.model.StudentData;
import com.qburst.model.validation;
import com.qburst.validator.validator;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	MySQLAccess sqlaccess = new MySQLAccess();
    	PrintWriter out = response.getWriter();
    	
    	int max=3;
		try 
		{
			sqlaccess.databaseConnection();
			int pgno=Integer.parseInt(request.getParameter("pageno"));
			//out.println("hii");
			if(pgno==1)
			{
				
			}
			else
			{
	 			pgno=pgno-1;
	 			pgno=pgno*max+1;
	 		}
	 		
			List<StudentData> list = sqlaccess.readfullDataBase(pgno,max);
	 		
	 		for(StudentData s:list){
	 			out.println(+s.getStudentID());
	 			out.println(s.getFirstName());
	 			out.println(s.getLastName());
	 			out.println(s.getEmail());
	 			out.println(s.getSex()+"\n");
	 		}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			sqlaccess.close();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MySQLAccess sqlaccess = new MySQLAccess();
		validator validatr = new validator();
		PrintWriter out = response.getWriter();
		try 
		{
			sqlaccess.databaseConnection();
			ServletInputStream inputjson = null;
			inputjson = request.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			StudentData s = mapper.readValue(inputjson, StudentData.class);
			validation valid = validatr.validate(s);
			if(valid.validity == 1) {
				sqlaccess.insertDataBase(s);
			}
			if(valid.validity == 2) {
				out.println("Password Invalid");
			}
			if(valid.validity == 3) {
				out.println("Email Invalid");
			}
			if(valid.validity == 4) {
				out.println("Email and password Invalid");
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			sqlaccess.close();
		}
		
	 }
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MySQLAccess sqlaccess = new MySQLAccess();
		validator validatr = new validator();
		PrintWriter out = response.getWriter();
		try 
		{
			sqlaccess.databaseConnection();
			ServletInputStream inputjson = null;
			inputjson = request.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			StudentData s = mapper.readValue(inputjson, StudentData.class);
			validation valid = validatr.validate(s);
			if(valid.validity == 1) {
				sqlaccess.updateRecord(s);
			}
			if(valid.validity == 2) {
				out.println("Password Invalid");
			}
			if(valid.validity == 3) {
				out.println("Email Invalid");
			}
			if(valid.validity == 4) {
				out.println("Email and Password invalid");
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			sqlaccess.close();
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySQLAccess sqlaccess = new MySQLAccess();
		try 
		{
			sqlaccess.databaseConnection();
			ServletInputStream inputjson = null;
			inputjson = request.getInputStream();
			
			sqlaccess.deleteRecord(inputjson);
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			sqlaccess.close();
		}
	}
}
