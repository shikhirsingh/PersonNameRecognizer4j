package com.shikhir.person_name_recognizer;


import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase;
import org.junit.Test;

import com.shikhir.person_name_recognizer.firstname.FirstName;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    @Test
    public static void testApp()
    {
    	assertEquals(FirstName.isFirstName("John",true), true);
    	assertEquals(FirstName.isFirstName("john",true), false); // lowercase should be false

    	assertEquals(FirstName.isFirstName("JOHN",false), true); // lowercase should be false
    	assertEquals(FirstName.isFirstName("JoHn"), true); // lowercase should be false

    	
    	assertEquals(FirstName.isFirstName("Mary", true), true); // lowercase should be false    	
    	assertEquals(FirstName.isFirstName("MARY", true), false); // lowercase should be false
    	assertEquals(FirstName.isFirstName("MARY", false), true); // lowercase should be false

    	assertEquals(FirstName.isFirstName("qwerty", true), false); // lowercase should be false    	
    	assertEquals(FirstName.isFirstName("qwerty", false), false); // lowercase should be false
    	
    	assertEquals(FirstName.isFirstName("may"), false);
    	assertEquals(FirstName.isFirstName("SUMMER"), false);
    	assertEquals(FirstName.isFirstName("bill"), false);

    	String test = "Hey John, when are you coming into town with Beth?"; // tricky because both John and Beth tokens contain symbols "," and "?"
    	assertEquals(FirstName.findNamesInString(test, false).size(),2);

    	String test2 = "I don't and won't worry about it"; // need to ensure that the firstname Don is not found in don't
    	assertEquals(FirstName.findNamesInString(test2, false).size(),0);
    	
    }
}
