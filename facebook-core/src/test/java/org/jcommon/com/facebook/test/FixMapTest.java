package org.jcommon.com.facebook.test;

import org.jcommon.com.facebook.utils.FixMap;

public class FixMapTest {
	public static void main(String[] args) {
		FixMap<Integer,Integer> map = new FixMap<Integer,Integer>(5);
		
		for(int i=0;i<10;i++){
			map.put(i, i);
			map.put(i, i);
			System.out.println(i + ":" + map.keySet());
		}

	}
	
	
}
