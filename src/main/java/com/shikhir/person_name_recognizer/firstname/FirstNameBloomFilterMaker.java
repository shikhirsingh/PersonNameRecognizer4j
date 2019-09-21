package com.shikhir.person_name_recognizer.firstname;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
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

public class FirstNameBloomFilterMaker {
	
	private TreeMap<String, Integer> firstNameCounter = new TreeMap<String, Integer>();
	private static BloomFilter<String> firstNameBloomFilter = null;
	
	final private static double FALSE_POSITIVE_PROBABILITY = 0.01;
	final public static int EXPECTED_SIZE = 100000;	
	final public static String BLOOM_FILTER_FILENAME_CS = "firstName_bloomfilter.dat";
	final public static String BLOOM_FILTER_FILENAME_CI = "firstName_bloomfilter_not_CS.dat";

	
	private void loadCSV(String fileNameRead, int pruneSize, boolean caseSensitive) {

		Reader in = null;

		String[] HEADERS = { "Id", "Name", "Year", "Gender", "Count"};
		
		try {
			in = new FileReader(fileNameRead);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterable<CSVRecord> records = null;
		try {
			records = CSVFormat.DEFAULT.withHeader(HEADERS)
						.withFirstRecordAsHeader()
						.parse(in);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		int i=0;
		for (CSVRecord record : records) {
		    String name = record.get("Name").trim();
		    if(!caseSensitive) name=name.toLowerCase();
		    int nameCount = Integer.parseInt(record.get("Count"));		    
		    Integer existingCount = firstNameCounter.get(name);

		    if(existingCount!=null) {
		    	firstNameCounter.put(name, existingCount+nameCount);
		    }
		    else {
		    	firstNameCounter.put(name, nameCount);		    	
		    }
		 
		   if(i%100000==0) System.out.println(".");
		   else if(i%10000==0) System.out.print(".");

		    i++;
		}
		
		System.out.println("Original Size in CSV: "+firstNameCounter.size());
		
	    Set set = firstNameCounter.entrySet();
	    
	    // Get an iterator
	    Iterator it = set.iterator();
	 
	    ArrayList<String> removeList = new ArrayList<String>();
	    // Display elements
	    while(it.hasNext()) {
	      Map.Entry me = (Map.Entry)it.next();
	      
	      String key = (String) me.getKey();
	      int value = (Integer) me.getValue();
	      //System.out.print("Key is: "+me.getKey() + " & ");
	      if(value < pruneSize) removeList.add(key);
	    } 
	    
	    for(String pruneName: removeList) {
	    	firstNameCounter.remove(pruneName);
	    }
		System.out.println(firstNameCounter.size());
	}
	
	private void buildBloomFilter(String outputFileName) {

		firstNameBloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_16), 
												firstNameCounter.size(),
												FALSE_POSITIVE_PROBABILITY
												);
		
		Set set = firstNameCounter.entrySet();
	    
	    // Get an iterator
	    Iterator it = set.iterator();
	 
	    ArrayList<String> removeList = new ArrayList<String>();
	    // Display elements
	    while(it.hasNext()) {
	      Map.Entry me = (Map.Entry)it.next();	      
	      String key = (String) me.getKey();
	      firstNameBloomFilter.put(key);
	    }
	    
	    try {
			serializeBloomFilter(firstNameBloomFilter, outputFileName);
			System.out.println("Done! - "+outputFileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not write file to disk");

		}
	     		
	}

	private void serializeBloomFilter(BloomFilter<String> firstNameBloomFilter, String fileName) throws IOException {
		
		File file = new File("src/main/resources/"+fileName);
		file.createNewFile();
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		final ObjectOutputStream oOutputStream = new ObjectOutputStream(fileOutputStream);
	    System.out.println("Writing file to disk...");

	    oOutputStream.writeObject(firstNameBloomFilter);
	    
	    oOutputStream.flush();
	    oOutputStream.close();
	    
	    String absolutePath = file.getAbsolutePath();
	    
	    System.out.println("File path : " + absolutePath);
		fileOutputStream.close();
	}	

	public void build(String inputFileName, int prune, String outputFileName, boolean caseSensitive) {
		loadCSV(inputFileName, prune, caseSensitive);
		buildBloomFilter(outputFileName);
	}
	
	public void buildWithDefaults() {
		build("./dataset/NationalNames.csv", 10, BLOOM_FILTER_FILENAME_CS, true);
		build("./dataset/NationalNames.csv", 10, BLOOM_FILTER_FILENAME_CI, false);
	
	}
}
