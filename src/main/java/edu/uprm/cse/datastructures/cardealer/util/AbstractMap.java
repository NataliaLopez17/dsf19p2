package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Iterator;
import java.util.Map.Entry;

public abstract class AbstractMap<K,V> implements SecondMap<K,V> { 
	public boolean isEmpty( ) { 
		return size() == 0; 
	} 
	protected static class MapEntry<K,V> implements Entry<K,V> { 
		private K kk; 
		private V vv;
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
		protected void setKey(K key) {
			kk = key; 
		} 
		public V setValue(V value) {
			V old = vv;
			vv = value;
			return old;
		} 
	} 
	private class KeyIterator implements Iterator<K> { 
		private Iterator<Entry<K,V>> entry = entrySets().iterator(); 
		public boolean hasNext() { 
			return entry.hasNext(); 
		} 
		public K next() { 
			return entry.next().getKey(); 
		} 
		public void remove() { 
			throw new UnsupportedOperationException( ); 
		} 
	} 
	private class KeyIterable implements Iterable<K> {  
		public Iterator<K> iterator() { 
			return new KeyIterator(); 
		} 
	}  
	public Iterable<K> keySets() { 
		return new KeyIterable(); 
	} 
	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K,V>> entry = entrySets().iterator(); 
		public boolean hasNext() { 
			return entry.hasNext(); 
		} 
		public V next() { 
			return entry.next().getValue(); 
		}
		public void remove() { 
			throw new UnsupportedOperationException(); 
		} 
	}  
	private class ValueIterable implements Iterable<V> { 
		public Iterator<V> iterator() { 
			return new ValueIterator(); 
		} 
	} 
	public Iterable<V> valueSets(){ 
		return new ValueIterable(); 
	}  
}





