package com.mycompany.dave;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class TerminologyLookUp {

	 public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 public static final String DB_URL = "jdbc:mysql://umls.effectivedynamics.com";
	 public static final String USER_NAME = "umls_ro";
	 public static final String PASSWORD = "s4#fde2";
	 
	 //------
//	 public static final String DB_URL = "jdbc:mysql://mysql.chpc.utah.edu";
//	 public static final String USER_NAME = "umlsro";
//	 public static final String PASSWORD = "umls";
	 
	 
	 
	
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
	
	
	
	public HashSet<String> lookUp2(String concept, int limit) throws Exception
	{
		HashSet<String> output = new HashSet<String>();
		String conceptCUI = "";
		
		// First find the CUI for input string:
		Class.forName(JDBC_DRIVER);
		Connection  conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT CUI FROM umls.MRCONSO WHERE STR='" + concept + "' LIMIT 1;";
		ResultSet res = stmt.executeQuery(sql);
		
	   while(res.next())
		 {
			 conceptCUI= res.getString("CUI");
		 }
	
	    // close response
	    res.close();
	    
	    // now get str from def of all the related CUIs
	    stmt = conn.createStatement();	
		
	    // related concepts: 
	    ArrayList<String> relatedCUIs = new ArrayList<String>();
	    
	    // now get all related CUIs
	    stmt = conn.createStatement();
	    sql = "SELECT CUI2 FROM umls.MRREL WHERE CUI1='"+ conceptCUI + "' LIMIT " + limit + ";";
		res = stmt.executeQuery(sql);
		while(res.next())
		{
				 relatedCUIs.add(res.getString("CUI2"));
		}
		
		// now get the preferred names for the CUIs
		
		for(String x:relatedCUIs)
		{
		    stmt = conn.createStatement();
		    sql = "SELECT STR FROM umls.MRCONSO WHERE CUI='" + x + "' AND ISPREF='Y' AND LAT='ENG' AND SAB='MTH';";
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
	
	
	
	public String retrievePreferredName(String input) throws Exception
	{
		String output = input;
		Class.forName(JDBC_DRIVER);
		Connection  conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT CUI FROM umls.MRCONSO WHERE STR='" + input.trim() + "';";
		ResultSet res = stmt.executeQuery(sql);	

		String cui = "";
	    while(res.next())
	    {
	    	cui = res.getString("CUI");
		}
		
	    if(cui=="")
	    {
	    	return "Please check the input term";
	    }

	    //not get the preferred term:
	    stmt = conn.createStatement();
	    sql = "SELECT STR FROM umls.MRCONSO WHERE CUI='" + cui + "' AND ISPREF='Y' AND TTY='PN';";
		res = stmt.executeQuery(sql);
	    while(res.next())
	    {
	    	output = res.getString("STR");
		}
		
	    // close connection
	    conn.close();		
		
		return output;
	}
	
	
	
	
	
	
	
	
}
