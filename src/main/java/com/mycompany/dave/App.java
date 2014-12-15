package com.mycompany.dave;

import java.util.HashSet;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
        TerminologyLookUp LU = new TerminologyLookUp();
//        ArrayList<String> ans = LU.lookUp("C0032344", 5);
//        for(String x:ans)
//        {
//        	System.out.println(x);
//        }	
        
        
//          TESTING the new/improved terminology lookup method         
        

        HashSet<String> ans2 = LU.lookUp2("Mild mental retardation",5);
        for(String x:ans2)
        {
        	System.out.println(x);
        }
        
 
//           TESTING preferred name method        
        System.out.println("-----------");
        System.out.println(LU.retrievePreferredName("toxic chemical"));
    }
}
