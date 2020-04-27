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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.shikhir.StrWrangler4j.fileops.ClassLoaderUtilz;
import com.shikhir.person_name_recognizer.bloomfilter.FirstNameBloomFilterMaker;

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
	
	public static List<String> findNamesInString(String document, boolean caseSensitive){
		List<String> names = new ArrayList<String>();
		
		String[] docWords = document.replaceAll("[^'a-zA-Z]", " ").split("\\s+");
		for(int i=0; i<docWords.length; i++) {
			if(isFirstName(docWords[i], caseSensitive)){
				names.add(docWords[i]);
			}
		}
		return names;
	}
	
	@SuppressWarnings("unchecked")
	public static void deserialize(boolean caseSensitive) throws IOException {
		try
		{
			if(caseSensitive) {
				if(firstNameCSBloomFilter==null) {
					InputStream in = ClassLoaderUtilz.getResourceAsStream(FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CS,
						FirstName.class);

					ObjectInputStream ois = new ObjectInputStream(in);
					firstNameCSBloomFilter = (BloomFilter<String>) ois.readObject();
					in.close();
				}
			}
			else {
				if(firstNameCIBloomFilter==null) {
					InputStream in = ClassLoaderUtilz.getResourceAsStream(FirstNameBloomFilterMaker.BLOOM_FILTER_FILENAME_CI,
																			FirstName.class);

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
	
	public static void close() {
		firstNameCSBloomFilter=null;
		firstNameCIBloomFilter=null;
		System.gc();
	}
}
