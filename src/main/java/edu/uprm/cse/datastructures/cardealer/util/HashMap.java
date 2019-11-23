package edu.uprm.cse.datastructures.cardealer.util;

public class HashMap<K,V>{
	private final static int TABLE_SIZE = 10;

	MapEntry<K,V>[] table;

	HashMap(){
		table = new MapEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++) {
			table[i] = null;
		}
	}
	public int get(int key) {
		int hash = (key % TABLE_SIZE);
		int firstHash = -1;
		while (hash != firstHash && (table[hash] == DeletedBucket.getUniqueDeletedEntry() 
				|| table[hash] != null && table[hash].getKey() != key)){
			if (firstHash == -1) {
				firstHash = hash;
			}
			hash = (hash + 1) % TABLE_SIZE;
		}

		if (table[hash] == null || hash == firstHash) {
			return -1;
		}
		else {
			return (int) table[hash].getValue();
		}
	}



	public void put(int key, V value) {
		int hash = (key % TABLE_SIZE);
		int firstHash = -1;
		int bucketIndex = -1;
		while (hash != firstHash
				&& (table[hash] == DeletedBucket.getUniqueDeletedEntry() || table[hash] != null
				&& table[hash].getKey() != key)) 
		{
			if (firstHash == -1) {
				firstHash = hash;
			}
			if (table[hash] == DeletedBucket.getUniqueDeletedEntry()) {
				bucketIndex = hash;
			}
			hash = (hash + 1) % TABLE_SIZE;
		}

		if ((table[hash] == null || hash == firstHash) && bucketIndex != -1) {
			table[bucketIndex] = new MapEntry(key, value);
		}
		else if (firstHash != hash) {
			if (table[hash] != DeletedBucket.getUniqueDeletedEntry() 
					&& table[hash] != null && table[hash].getKey() == key) 
			{
				table[hash].setValue(value);
			}
			else {
				table[hash] = new MapEntry(key, value);
			}
		}	
	}

	public void remove(int key) {
		int hash = (key % TABLE_SIZE);
		int firstHash = -1;
		while (hash != firstHash
				&& (table[hash] == DeletedBucket.getUniqueDeletedEntry() || table[hash] != null
				&& table[hash].getKey() != key)) 
		{
			if (firstHash == -1) {
				firstHash = hash;
			}
			hash = (hash + 1) % TABLE_SIZE;
		}

		if (hash != firstHash && table[hash] != null) {
			table[hash] = DeletedBucket.getUniqueDeletedEntry();
		}
	}
}
