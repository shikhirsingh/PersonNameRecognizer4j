package com.shikhir.person_name_recognizer.bloomfilter;

public class NameData implements Comparable<NameData> {
	private String name;
	private int count;
	
	public NameData(String name, int count) {
		this.name=name;
		this.count=count;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
    public int compareTo(NameData nd) {
    	if(getCount()< nd.getCount()) return 1;
    	else if(getCount()> nd.getCount()) return -1;
    	return 0;
    }
    
}
