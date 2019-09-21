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

    	
    	assertEquals(FirstName.isFirstName("Mary", true), true); // lowercase should be false    	
    	assertEquals(FirstName.isFirstName("MARY", true), false); // lowercase should be false
    	assertEquals(FirstName.isFirstName("MARY", false), true); // lowercase should be false

    	assertEquals(FirstName.isFirstName("qwerty", true), false); // lowercase should be false    	
    	assertEquals(FirstName.isFirstName("qwerty", false), false); // lowercase should be false
    }
}
