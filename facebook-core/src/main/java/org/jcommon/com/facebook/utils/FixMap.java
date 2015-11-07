package org.jcommon.com.facebook.utils;

import java.util.HashMap;
import java.util.LinkedList;

public class FixMap<K, V> extends HashMap<K,V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int fixSize;
	private LinkedList<K> keys;
	
	public FixMap(int fixSize){
		super();
		this.fixSize = fixSize>0?fixSize:0;
		this.keys    = new LinkedList<K>();
	}
	
	public V put(K key, V value) {
		if(!keys.contains(key))
			keys.add(key);
		V v = super.put(key, value);
		
		if(keys.size()>fixSize){
			remove(keys.get(0));
		}
		return v;
	}
	
	public V remove(Object key){
		keys.remove(key);
		V v = super.remove(key);
		return v;
	}
}
