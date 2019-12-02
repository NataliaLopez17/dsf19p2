package edu.uprm.cse.datastructures.cardealer.util;

//kk.intValue(); whole int value
public class MapEntry<K, V>{
	
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
 

	
}
