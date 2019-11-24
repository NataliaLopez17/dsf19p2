package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Map.Entry;

public class ProbingHashMap<K,V> extends AbstractHashMap<K,V> { 
	private MapEntry<K,V>[ ] table; // a fixed array of entries (all initially null)
	private MapEntry<K,V> DELETED = new MapEntry<>(null, null); 
	public ProbingHashMap() { 
		super(); 
	} 
	public ProbingHashMap(int capacity) {
		super(capacity); 
	} 
	public ProbingHashMap(int c, int p) {
		super(c, p); 
	} 
	protected void createTable( ) { 
		table = (MapEntry<K,V>[ ]) new MapEntry[capacity]; 
	} 
	private boolean isAvailable(int i) { 
		return (table[i] == null || table[i] == DELETED);
	}
	private int findSlot(int j, K key) {  
		int availability = -1; // no slot available (thus far)
		int i = j; // index while scanning table
		do {  
			if (isAvailable(i)) { // may be either empty or deleted
				if (availability == -1) {
					availability = i; // this is the first available slot!
				}
				if (table[i] == null) {
					break; // if empty, search fails immediately
				}
			} 
			else if (table[i].getKey( ).equals(key)) {
				return i; // successful match
			}
			i = (i+1) % capacity; // keep looking (cyclically)
		} 
		while (i != j); // stop if we return to the start
		return -(availability + 1); // search has failed
	}  
	/** Returns value associated with key key in bucket with hash value j, or else null. */
	protected V getBucket(int j, K key) {  
		int i = findSlot(j, key);
		if (i < 0) {
			return null; // no match found
		}
		return table[i].getValue( );
	}  
	/** Associates key key with value value in bucket with hash value j; returns old value. */
	protected V putBucket(int j, K key, V value) { 
		int i = findSlot(j, key);
		if (i >= 0) { // this key has an existing entry
			return table[i].setValue(value);
		}
		table[-(i+1)] = new MapEntry<>(key, value); // convert to proper index
		num++;
		return null;
	} 
	/** Removes entry having key key from bucket with hash value j (if any). */
	protected V removeBucket(int j, K key) { 
		int i = findSlot(j, key);
		if (i < 0) {
			return null; // nothing to remove
		}
		V result = table[i].getValue();
		table[i] = DELETED; // mark this slot as deactivated
		num--;
		return result;
	}  
	/** Returns an iterable collection of all key-value entries of the map. */
	public Iterable<Entry<K,V>> entrySets() { 
		ArrayList<Entry<K,V>> theBuffer = new ArrayList<>( );
		for (int i=0; i < capacity; i++) {
			if (!isAvailable(i)) theBuffer.add(table[i]);
		}
		return theBuffer;
	}
}