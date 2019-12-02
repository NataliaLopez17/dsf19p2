package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarComparator implements Comparator<Car>{

	@Override
	public int compare(Car c1, Car c2) {

		if(!(c1 instanceof Car) && (!(c2 instanceof Car))) {
			throw new IllegalStateException("Object isnt an instance of Car.");

		}

		if(c1.getCarBrand().compareTo(c2.getCarBrand()) == 0) {
			if(c1.getCarModel().compareTo(c2.getCarModel()) == 0) {
				return c1.getCarModelOption().compareTo(c2.getCarModelOption());
			}
			else {
				return c1.getCarModel().compareTo(c2.getCarModel());
			}
		}
		else {
			return c1.getCarBrand().compareTo(c2.getCarBrand());
		}
	}

}
