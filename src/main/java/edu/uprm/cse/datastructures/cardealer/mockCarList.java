package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.uprm.cse.datastructures.cardealer.model.Car;

public class mockCarList extends Car{
	
	private static final ArrayList<Car> cList = new ArrayList<Car>();

	static Car car1 = new Car(1, "Jeep", "Compass", "Trailhawk", 29675);
	static Car car2 = new Car(2, "Jaguar", "XJ", "R-Sport SWB", 76000);
	static Car car3 = new Car(3, "Maserati", "Granturismo", "MC", 151720);
	static Car car4 = new Car(4, "Peugeot", "308", "GTi",37124.63);
	static Car car5 = new Car(5, "McLaren", "12C", "MP4", 231400);
	  
	static {
	    // Create list of customers
	    cList.add(car1);
	    cList.add(car2);
	    cList.add(car3);
	    cList.add(car4);
	    cList.add(car5);
	    

	  }
	  
	  private mockCarList(){}
	  
	  public static ArrayList<Car> getInstance(){
	    return cList;
	  }

}
