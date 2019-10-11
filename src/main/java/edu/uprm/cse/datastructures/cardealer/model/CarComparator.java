package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarComparator<E> implements Comparator<E>{

	@Override
	public int compare(E o1, E o2) {

		if(!(o1 instanceof Car) && (!(o2 instanceof Car))) {
			throw new IllegalStateException("Object isnt an instance of Car.");

		}
		
		Car c1 = (Car) o1;
		Car c2 = (Car) o2;

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
