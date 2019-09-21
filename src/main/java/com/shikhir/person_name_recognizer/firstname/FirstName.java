package com.shikhir.person_name_recognizer.firstname;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.shikhir.StrWrangler4j.fileops.ClassLoaderUtilz;

public class FirstName {
	private static BloomFilter<String> firstNameCSBloomFilter = null;
	private static BloomFilter<String> firstNameCIBloomFilter = null;

	public static boolean isFirstName(String firstName, boolean caseSensitive){
		firstName = firstName.trim();
		if(caseSensitive==true && firstNameCSBloomFilter == null) {
			try {
				deserializeCS();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(caseSensitive==false && firstNameCIBloomFilter == null) {
			try {
				deserializeCI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		

		if(caseSensitive==true) {
			if(firstNameCSBloomFilter.mightContain(firstName)) return true;
			else return false;
		}
		else {
			firstName=firstName.toLowerCase();
			if(firstNameCIBloomFilter.mightContain(firstName)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void deserializeCS() throws IOException {

		URL bloomFilterUrl=ClassLoaderUtilz.getResource(FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CS,
														FirstName.class);

		try
		{
			File bloomfilter = new File(bloomFilterUrl.toURI());		

			if(firstNameCSBloomFilter==null) {
				FileInputStream fis = new FileInputStream(bloomfilter);
				ObjectInputStream in = new ObjectInputStream(fis);
				firstNameCSBloomFilter = (BloomFilter<String>) in.readObject();
				fis.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static void deserializeCI() throws IOException {

		URL bloomFilterUrl=ClassLoaderUtilz.getResource(FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CI,
														FirstName.class);

		try
		{
			File bloomfilter = new File(bloomFilterUrl.toURI());

			if(firstNameCIBloomFilter==null) {
				FileInputStream fis = new FileInputStream(bloomfilter);
				ObjectInputStream in = new ObjectInputStream(fis);
				firstNameCIBloomFilter = (BloomFilter<String>) in.readObject();
				fis.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
