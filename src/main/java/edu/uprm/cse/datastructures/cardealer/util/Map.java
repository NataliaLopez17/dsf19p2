package edu.uprm.cse.datastructures.cardealer.util;

public interface Map<K, V> {

    public int size();

    public boolean isEmpty();

    public V get(K key);

    public V put(K key, V value);

    public V remove(K key);

    public boolean contains(K key);

    public SortedList<K> getKeys();

    public SortedList<V> getValues();



}