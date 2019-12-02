package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Random;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> { 
	protected abstract void createTable();
	protected abstract V getBucket(int i, K key);
	protected abstract V putBucket(int i, K key, V value);
	protected abstract V removeBucket(int i, K key);
	protected int num = 0; 
	protected int capacity;
	private int primeNum; 
	private long scaling, shifting; 
	protected Comparator<K> keyComparator;
	protected Comparator<V> valueComparator;
	public AbstractHashMap(int c, int p, Comparator<K> c1, Comparator<V> c2) { 
		primeNum = p;
		capacity = c;
		keyComparator = c1;
		valueComparator = c2;
		Random rand = new Random();
		scaling = rand.nextInt(primeNum-1) + 1;
		shifting = rand.nextInt(primeNum);
		createTable( );
	} 
	public AbstractHashMap(int cap, Comparator<K> c1, Comparator<V> c2) { 
		this(cap, 31, c1,c2); 
	} 
	
	public AbstractHashMap(Comparator<K> c1, Comparator<V> c2) {
		this(10, c1, c2);
	}
	public int size() {
		return num; 
	}  
	public V get(K key) {
		return getBucket(hashValue(key), key); 
	} 
	public V remove(K key) {
		return removeBucket(hashValue(key), key);
	}
	public V put(K key, V value) { 
		V answer = putBucket(hashValue(key), key, value);
		if (num > capacity / 2) { // keep load factor <= 0.5
			reAllocate(2 * capacity - 1);// (or find a nearby prime)
		} 
		return answer;
	}  
	private int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode( )*scaling + shifting) % primeNum) % capacity);
	}  
	private void reAllocate(int newCapacity) { 
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
	public boolean contains(K key) {
		return get(key) == null;
	}
	

}