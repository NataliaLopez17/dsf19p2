package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.util.ValueComparator;
import edu.uprm.cse.datastructures.cardealer.util.Map;
import edu.uprm.cse.datastructures.cardealer.util.ProbingHashMap;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

public class CarTable {
	public int size = CarTable.size();
	
	
	public static  Map<Long,Car> CarTable = new ProbingHashMap<Long, Car>(
			10,new ValueComparator(),new CarComparator());
	
	public static  Map<Long,Car> getInstance(){
		return CarTable;
		}
	public static  void resetCars() {
			SortedList<Long> neue = CarTable.getKeys();
			for(Long k : neue) {
				CarTable.remove(k);
			}
		}

}
