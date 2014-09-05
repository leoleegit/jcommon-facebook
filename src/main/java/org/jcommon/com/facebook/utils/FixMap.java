package org.jcommon.com.facebook.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//import org.apache.log4j.Logger;

public class FixMap<K,V> extends HashMap<K,V>
	implements Map<K,V>{
	private int fixSize;
	private LinkedList<K> keys;
	//private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FixMap(int fixSize){
		super(fixSize>0?fixSize:0);
		this.fixSize = fixSize;
		this.keys    = new LinkedList<K>();
	}
	
	public V put(K key, V value) {
		//logger.info(keys);
		V v = super.put(key, value);
		if(v!=null)
			keys.remove(key);
		keys.add(key);
		if(this.size()>fixSize && fixSize>0){
			K key0 = get(0);
			remove(key0);
		}
		//logger.info(keys);
		return v;
	}
	
	public K get(int index){
		if(keys.size()>index)
			return keys.get(index);
		return null;
	}
	
	public V remove(Object key){
		keys.remove(key);
		V v = super.remove(key);
		////logger.info(key);
		return v;
	}
	
	
}
