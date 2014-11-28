package com.mycompany.dave;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TerminologyLookUp {

	 public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 public static final String DB_URL = "jdbc:mysql://umls.effectivedynamics.com";
	 public static final String USER_NAME = "umls_ro";
	 public static final String PASSWORD = "s4#fde2";
	 
	 
	
	public ArrayList<String> lookUp(String cui, int limit) throws Exception
	{
		ArrayList<String> output = new ArrayList<String>();
		ArrayList<String> relatedCUI = new ArrayList<String>();
		Class.forName(JDBC_DRIVER);
		Connection  conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT CUI2 FROM umls.MRREL WHERE CUI1='" + cui + "' LIMIT " + limit + ";";
		ResultSet res = stmt.executeQuery(sql);
		
	   while(res.next())
		 {
			 relatedCUI.add(res.getString("CUI2"));
		 }
		 
	    // close response
	    res.close();
	    
	    // now get str from def of all the related CUIs
	    stmt = conn.createStatement();
		for(String relCUI: relatedCUI)
		{
			sql = "SELECT STR FROM umls.MRCONSO WHERE CUI='" + relCUI + "';";
			res = stmt.executeQuery(sql);
			while(res.next())
			 {
				 output.add(res.getString("STR"));
			 }
		}
	    
	    
		
	    
	    
	    
	    
	    // close connection
	    conn.close();
		
		return output;
	}
	
}
