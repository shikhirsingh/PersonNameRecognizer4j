package com.shikhir.person_name_recognizer;

import com.shikhir.person_name_recognizer.firstname.FirstName;
import com.shikhir.person_name_recognizer.firstname.FirstNameBloomFilterMaker;
/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	FirstNameBloomFilterMaker bf = new FirstNameBloomFilterMaker();
    	bf.buildWithDefaults();
    
    }
}
