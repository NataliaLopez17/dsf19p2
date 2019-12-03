package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Map.Entry;

public class HashTableOA<K,V> implements Map<K,V>{ 
	public Object[] table; // a fixed array of entries (all initially null)
	public MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); 

	public class MapEntry<K,V> implements Entry<K,V> { 
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
		table = new Object[c];
		createTable( );
	} 

	public HashTableOA(int capacity,Comparator<K> c1, Comparator<V> c2) {
		this(capacity, 11939, c1, c2);
	} 



	public void reAllocate() { 

		Object[] temp = this.table;
		capacity *= 2;
		this.table = new Object[capacity];
		createTable(); // based on updated capacity
		num = 0; // will be recomputed while reinserting entries
		for (Object o : temp) {
			MapEntry<K,V> e = (MapEntry<K,V>)o;
			put(e.getKey(), e.getValue());
		}
	} 


	public void createTable() { 
		for(int i = 0; i < table.length; i++) {
			table[i] = new MapEntry<K, V>(null, null);
		}
	} 
	public boolean isAvailable(int i) { 
		return ((MapEntry<K,V>)table[i]).getKey() == null;
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
	public int hashValue2(K key) {
		return (int) Math.round((Math.pow(hashValue(key), 2))% capacity);
	}




	public V putHashFunct(int i, K key, V value) {  
		int availability = -1; // no slot available (thus far)
		int j = i; // index while scanning table

		int hash1 = hashValue(key);
		int hash2 = hashValue2(key);

		if (isAvailable(hash1) || key.equals(((MapEntry<K,V>) table[hash1]).getKey())) {
			V valueToReturn = ((MapEntry<K,V>) table[hash1]).getValue();
			if(isAvailable(hash1)) {
				this.num++;
			}
			// this is the first available slot!
			((MapEntry<K,V>) table[hash1]).setKey(key);
			((MapEntry<K,V>) table[hash1]).setValue(value);

			return valueToReturn;
		}

		if (isAvailable(hash2) || key.equals(((MapEntry<K,V>) table[hash2]).getKey())) {
			V valueToReturn = ((MapEntry<K,V>) table[hash2]).getValue();
			if(isAvailable(hash2)) {
				this.num++;
			}
			((MapEntry<K,V>) table[hash2]).setKey(key);
			((MapEntry<K,V>) table[hash2]).setValue(value);

			return valueToReturn;
		}

		j = hash2 + 1;

		do {  
			if (isAvailable(j) || key.equals(((MapEntry<K,V>) table[j]).getKey())) { 
				V valueToReturn = ((MapEntry<K,V>) table[j]).getValue();
				if(isAvailable(j)) {
					this.num++;
				}
				((MapEntry<K,V>) table[j]).setKey(key);
				((MapEntry<K,V>) table[j]).setValue(value);

				return valueToReturn;
			}

			j = (j+1) % capacity;
		} 
		while (j != hash2); 
		return null;
	}  


	public MapEntry<K,V> getHashFunct(int i, K key, V value) {  

		int hash1 = hashValue(key);
		int hash2 = hashValue2(key);

		if (key.equals(((MapEntry<K,V>) table[hash1]).getKey())) { 
			return ((MapEntry<K,V>) table[hash1]);
		}

		if (key.equals(((MapEntry<K,V>) table[hash2]).getKey())) { 
			return ((MapEntry<K,V>) table[hash2]);
		}

		int j = (hash2 + 1) % capacity;

		do {  
			MapEntry<K, V> entry = ((MapEntry<K,V>) table[j]);
			if (key.equals(entry.getKey())) {
				return ((MapEntry<K,V>) table[j]);
			}

			j = (j+1) % capacity;
		} 
		while (j != hash2); 
		return DEFUNCT;
	} 


	/** Returns value associated with key key in bucket with hash value j, or else null. */
	@Override
	public V get(K key) {
		int j = hashValue(key);
		return getHashFunct(j, key, null).getValue();
	}
	/** Associates key key with value value in bucket with hash value j; returns old value. */
	@Override
	public V put(K key, V value) {
		//check size and reallocate if necessary
		if(this.size() == this.capacity) {
			reAllocate();
		}
		int j = hashValue(key);
		return putHashFunct(j, key, value);
	}

	/** Removes entry having key key from bucket with hash value j (if any). */
	@Override
	public V remove(K key) {
		int j = hashValue(key);
		MapEntry<K,V> toDelete = getHashFunct(j, key, null);
		V value = toDelete.getValue();
		toDelete.setValue(null);
		toDelete.setKey(null);
		return value;
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
		SortedList<K> neue = new CircularSortedDoublyLinkedList<K>(keyComparator);
		for(int j = 0; j < table.length; j++) {
			neue.add(((MapEntry<K,V>) table[j]).getKey());
		}
		return neue;
	}
	public SortedList<V> getValues() {
		SortedList<V> neue = new CircularSortedDoublyLinkedList<V>(valueComparator);
		for(int j = 0; j < table.length; j++) {
			if(((MapEntry<K,V>) table[j]).getKey() != null) {
				neue.add(((MapEntry<K,V>) table[j]).getValue());
			}
		}
		return neue;
	}
}