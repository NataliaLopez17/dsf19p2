package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.util.ProbingHashMap;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;
import edu.uprm.cse.datastructures.cardealer.util.ValueComparator;

public class CarTable {
	public int size = CarTable.size();
	
	public static ProbingHashMap<Long, Car> CarTable = new ProbingHashMap<Long,Car>(10, new ValueComparator(), new CarComparator()); 
	
	public static ProbingHashMap<Long,Car> getInstance(){
		return CarTable;
	}
	public static void resetCars() {
		SortedList<Long> neue = CarTable.getKeys();
		int i = 0;
		while(i < neue.size()) {
			CarTable.remove(neue.get(i));
			i++;
		}
	}

}
