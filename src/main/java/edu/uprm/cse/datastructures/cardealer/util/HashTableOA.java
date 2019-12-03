package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Map.Entry;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;

public class HashTableOA<K,V> implements Map<K,V>{ 
	public Object[] table; // a fixed array of entries (all initially null)
	public MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); 
	
	public static class MapEntry<K,V> implements Entry<K,V> { 
		public K kk; 
		public V vv;
		public MapEntry(K key, V value) { 
			kk = key;
			vv = value;
		} 
		public K getKey() {
			return kk; 
		} 
		public V getValue( ) {
			return vv; 
		} 
		public void setKey(K key) {
			kk = key; 
		} 
		public V setValue(V value) {
			V old = vv;
			vv = value;
			return old;
		} 
	} 
	
	
	
	public int num = 0; 
	public int capacity;
	public int primeNum; 
	public long scaling, shifting; 
	public Comparator<K> keyComparator;
	public Comparator<V> valueComparator;
	
	public HashTableOA(int c, int p, Comparator<K> c1, Comparator<V> c2) { 
		primeNum = p;
		capacity = c;
		keyComparator = c1;
		valueComparator = c2;
		Random rand = new Random();
		scaling = rand.nextInt(primeNum-1) + 1;
		shifting = rand.nextInt(primeNum);
		createTable( );
	} 
	
	public HashTableOA(int capacity,Comparator<K> c1, Comparator<V> c2) {
		this(capacity, 11939, c1, c2);
	} 
	
	 
	
	public void reAllocate(int newCapacity) { 
		ArrayList<Entry<K,V>> theBuffer = new ArrayList<>(num);
		for (Entry<K,V> e : entrySets()) {
			theBuffer.add(e);
		}
		capacity = newCapacity;
		createTable( ); // based on updated capacity
		num = 0; // will be recomputed while reinserting entries
		for (Entry<K,V> e : theBuffer) {
			put(e.getKey( ), e.getValue( ));
		}
	} 
	 
	
	public void createTable() { 
		for(int i = 0; i < table.length; i++) {
			table[i] = DEFUNCT;
		}
	} 
	public boolean isAvailable(int i) { 
		return (table[i] == null || table[i] == DEFUNCT);
	}
	
	
	
	@Override
	public int size() {
		return num;
	}
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}
	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	
	
	public int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode( )*scaling + shifting) % primeNum) % capacity);
	} 
	
	public int hashFunct(int i, K key) {  
		int availability = -1; // no slot available (thus far)
		int j = i; // index while scanning table
		do {  
			if (isAvailable(j)) { // may be either empty or deleted
				if (availability == -1) {
					availability = j; // this is the first available slot!
				}
				if (table[j] == null) {
					break; // if empty, search fails immediately
				}
			} 
			else if (key.equals(((MapEntry<K,V>) table[i]).getKey())) {
				return j; // successful match
			}
			j = ((j+1)^2) % capacity;
		} 
		while (j != i); // stop if we return to the start
		//return linearProbing(j,key);
		return linearProbing(i, key); 
	}  
	
	public int linearProbing(int h, K key) {  
		int availability = -1; 
		int j = h; 
		do {  
			if (isAvailable(j)) { 
				if (availability == -1) {
					availability = j; 
				}
				if (table[j] == null) {
					break; 
				}
			} 
			else if (key.equals(((MapEntry<K,V>) table[j]).getKey())) {
				return j; 
			}
			j = (j+1) % capacity;
		} 
		while (j != h); 
		return -(availability + 1); 
	}
	
	
	
	@Override
	public V get(K key) {
		int j = hashValue(key);
		return getAux(j, key);
	}
	/** Returns value associated with key key in bucket with hash value j, or else null. */
	public V getAux(int j, K key) {  
		int i = hashFunct(j, key);
		if (i < 0) {
			return null; // no match found
		}
		return ((MapEntry<K,V>) table[i]).getValue();
	}
	
	
	
	@Override
	public V put(K key, V value) {
		int j = hashValue(key);
		return putAux(j, key, value);
	}
	/** Associates key key with value value in bucket with hash value j; returns old value. */
	public V putAux(int j, K key, V value) { 
		int i = hashFunct(j, key);
		if (i >= 0) { // this key has an existing entry
			return ((MapEntry<K,V>) table[i]).setValue(value);
		}
		table[-(i+1)] = new MapEntry<>(key, value); // convert to proper index
		num++;
		return null;
	}
	
	
	
	@Override
	public V remove(K key) {
		int j = hashValue(key);
		return removeAux(j, key);
	}
	/** Removes entry having key key from bucket with hash value j (if any). */
	public V removeAux(int j, K key) { 
		int i = hashFunct(j, key);
		if (i < 0) {
			return null; // nothing to remove
		}
		V result = ((MapEntry<K,V>) table[i]).getValue();
		table[i] = DEFUNCT; // mark this slot as deactivated
		num--;
		return result;
	}  
	
	
	
	/** Returns an iterable collection of all key-value entries of the map. */
	public Iterable<Entry<K,V>> entrySets() { 
		ArrayList<Entry<K,V>> theBuffer = new ArrayList<>( );
		for (int i=0; i < capacity; i++) {
			if (!isAvailable(i)) theBuffer.add(((MapEntry<K,V>) table[i]));
		}
		return theBuffer;
	}
	
	public SortedList<K> getKeys() {
//		ArrayList<K> list = new ArrayList<K>();
//		for(int i = 0; i < this.size(); i++) {
//			list.add(table[i].getKey());
//		}
		SortedList<K> neue = new CircularSortedDoublyLinkedList<K>(keyComparator);
		for(int j = 0; j < table.length; j++) {
			neue.add(((MapEntry<K,V>) table[j]).getKey());
		}
		return neue;
	}
	public SortedList<V> getValues() {
//		ArrayList<V> list = new ArrayList<V>();
//		for(int i = 0; i < this.size(); i++) {
//			list.add(table[i].getValue());
//		}
		SortedList<V> neue = new CircularSortedDoublyLinkedList<V>(valueComparator);
		for(int j = 0; j < table.length; j++) {
			if(((MapEntry<K,V>) table[j]).getKey() != null) {
				neue.add(((MapEntry<K,V>) table[j]).getValue());
			}
		}
		return neue;
	}
	
//	@Override
//	public V get(K key) {
//		return getBucket(hashValue(key), key);
//	}
//	@Override
//	public V put(K key, V value) {
//		V answer = putBucket(hashValue(key), key, value);
//		if (num > capacity / 2) { // keep load factor <= 0.5
//			reAllocate(2 * capacity - 1);// (or find a nearby prime)
//		} 
//		return answer;
//	}
//	@Override
//	public V remove(K key) {
//		return removeBucket(hashValue(key), key);
//	}
	
}