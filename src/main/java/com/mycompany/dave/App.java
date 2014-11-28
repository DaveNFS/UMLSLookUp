package com.mycompany.dave;

import java.util.ArrayList;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
        TerminologyLookUp LU = new TerminologyLookUp();
        ArrayList<String> ans = LU.lookUp("C0032344", 5);
        for(String x:ans)
        {
        	System.out.println(x);
        }	
     
    }
}
