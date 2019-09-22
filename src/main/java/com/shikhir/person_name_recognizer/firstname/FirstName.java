package com.shikhir.person_name_recognizer.firstname;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

	public static boolean isFirstName(String firstName) {
		return isFirstName(firstName, false);
	}
	
	public static boolean isFirstName(String firstName, boolean caseSensitive){
		if(caseSensitive==true && firstNameCSBloomFilter == null) {
			try {
				deserialize(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(caseSensitive==false && firstNameCIBloomFilter == null) {
			try {
				deserialize(false);
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
	public static void deserialize(boolean caseSensitive) throws IOException {

		InputStream in = ClassLoaderUtilz.getResourceAsStream(caseSensitive?FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CS:
																FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CI,
																FirstName.class);
		try
		{
			if(caseSensitive) {
				if(firstNameCSBloomFilter==null) {
					ObjectInputStream ois = new ObjectInputStream(in);
					firstNameCSBloomFilter = (BloomFilter<String>) ois.readObject();
					in.close();
				}
			}
			else {
				if(firstNameCIBloomFilter==null) {
					ObjectInputStream ois = new ObjectInputStream(in);
					firstNameCIBloomFilter = (BloomFilter<String>) ois.readObject();
					in.close();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
