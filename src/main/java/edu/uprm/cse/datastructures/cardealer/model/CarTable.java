package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.util.CircularSortedDoublyLinkedList;

public class CarTable {
	private  static CircularSortedDoublyLinkedList<Car> cList = new CircularSortedDoublyLinkedList<Car>(new CarComparator());
	public static CircularSortedDoublyLinkedList<Car>  getInstance(){
		return cList;
	}
	
	public static void resetCars() {
		cList = new CircularSortedDoublyLinkedList<Car>(new CarComparator());
	}

}
