package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Map.Entry;

public interface SecondMap<K, V> {
	int size();
	boolean isEmpty();
	V get(K key);
	V put(K key, V value);
	V remove(K key);
	Iterable<K> keySets();
	Iterable<V> valueSets();
	Iterable<Entry<K,V>> entrySets();
}
