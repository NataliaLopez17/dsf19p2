package edu.uprm.cse.datastructures.cardealer.util;

public class DeletedBucket<K,V> extends MapEntry<K,V>{

	private static DeletedBucket bucket = null;

	public DeletedBucket() {
		super(null, null); 
	}

	public static DeletedBucket getUniqueDeletedEntry() {
		if (bucket == null) {
			bucket = new DeletedBucket();
		}
		return bucket;

	}

}
