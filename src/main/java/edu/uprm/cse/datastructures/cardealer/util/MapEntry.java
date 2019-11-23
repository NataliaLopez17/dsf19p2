package edu.uprm.cse.datastructures.cardealer.util;

//kk.intValue(); whole int value
public class MapEntry<K, V> implements Map<K,V> {
	
	private int kk;
    private V vv;
    
    
    MapEntry(K key, V value) {
    
          this.kk = (int) key;
          this.vv = value;
    }
    public V getValue() {
          return vv;
    }
    public void setValue(V value) {
          this.vv = value;
    }
    public int getKey() {
          return kk;
    }
    private int size = 0;
	@Override
	public int size() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public SortedList<K> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SortedList<V> getValues() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
