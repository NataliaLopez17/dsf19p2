package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;

public class ValueComparator implements Comparator<Long>{

	@Override
	public int compare(Long val1, Long val2) {
		if(val1.equals(null)) {
			return -1;
		}
		else if(val2.equals(null)) {
			return 1;
		}
		else {
			return val1.compareTo(val2);
		}
	}

}
