package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.util.ValueComparator;
import edu.uprm.cse.datastructures.cardealer.util.Map;
import edu.uprm.cse.datastructures.cardealer.util.HashTableOA;

public class CarTable {
	public int size = CarTable.size();

	
	public static  Map<Long,Car> CarTable = new HashTableOA<Long, Car>(
			10,new ValueComparator(),new CarComparator());

	public static  Map<Long,Car> getInstance(){
		return CarTable;
	}
	public static  void resetCars() {
		CarTable = new HashTableOA<Long, Car>(10,new ValueComparator(),new CarComparator());
	}
}
