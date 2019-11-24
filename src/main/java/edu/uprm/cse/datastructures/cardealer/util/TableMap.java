package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class TableMap<K,V> extends AbstractMap<K,V> { 
	private ArrayList<MapEntry<K,V>> table = new ArrayList<>();
	public TableMap() { 
	} 
	private int findPos(K key) {  
		int n = table.size();
		for (int i=0; i < n; i++) {
			if (table.get(i).getKey().equals(key)) {
				return i;
			}
		}
		return -1; 
	}
	public int size() { 
		return table.size( ); 
	}
	public V get(K key) {  
		int i = findPos(key);
		if (i == -1) {
			return null;
		}
		return table.get(i).getValue();
	} 
	public V put(K key, V value) {
		int i = findPos(key);
		if (i == -1) {  
			table.add(new MapEntry<>(key, value)); 
			return null;
		} 
		else {
			return table.get(i).setValue(value);
		}
	} 
	public V remove(K key) { 
		int i = findPos(key);
		int n = size( );
		if (i == -1) {
			return null;
		}
		V result = table.get(i).getValue();
		if (i != n - 1) {
			table.set(i, table.get(n-1));
		}
		table.remove(n-1);
		return result;
	} 
	private class EntryIterator implements Iterator<Entry<K,V>> {
		private int i=0;
		public boolean hasNext() { 
			return i < table.size(); 
		}  
		public Entry<K,V> next() { 
			if (i == table.size()) {
				throw new NoSuchElementException();
			}
			return table.get(i++);
		}  
		public void remove() {
			throw new UnsupportedOperationException( );
		}  
	} 
	private class EntryIterable implements Iterable<Entry<K,V>> { 
		public Iterator<Entry<K,V>> iterator() { 
			return new EntryIterator(); 
		} 
	} 
	public Iterable<Entry<K,V>> entrySets() {
		return new EntryIterable();
	}
}