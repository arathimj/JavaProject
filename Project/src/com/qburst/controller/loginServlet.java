package com.qburst.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qburst.dao.MySQLAccess;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MySQLAccess sqlaccess = new MySQLAccess();
		try 
		{
			sqlaccess.databaseConnection();
			PrintWriter out = response.getWriter();
			ServletInputStream inputjson = null;
			inputjson = request.getInputStream();
			Integer n = sqlaccess.login(inputjson);
			if(n==1)
			{
				out.println("Logged in");
			}
			if(n==0)
			{
				out.println("Unsuccessful");
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

}
