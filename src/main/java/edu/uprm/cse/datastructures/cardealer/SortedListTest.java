package edu.uprm.cse.datastructures.cardealer;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Comparator;

import org.junit.Test;

import edu.uprm.cse.datastructures.cardealer.util.CircularSortedDoublyLinkedList;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

// Comparator for integers
class IntegerComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		if(o1 < o2)
			return -1;
		else if (o1 > o2)
			return 1;
		return 0;
	}
	
}

public class SortedListTest {
	@Test
	public void testIsEmpty() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		assertTrue("List should be empty", list.isEmpty());
		list.add(5);
		assertFalse(list.isEmpty());
	}
	@Test
	public void testSizeAddGet() {
		Integer[] results = {1,2,5};
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should have size of 3",3, list.size());
		Integer[] listIn = new Integer[3];
		for (int i = 0; i < list.size(); i++) {
			listIn[i] = list.get(i);
		}
		assertArrayEquals("Should equal [1,2,5]", results, listIn);
		list.add(3);
		list.add(9);
		list.add(7);
		Integer[] results2 = {1,2,3,5,7,9};
		assertEquals("Should now have size 6", 6, list.size());
		Integer[] listIn2 = new Integer[6];
		for (int i = 0; i < list.size(); i++) {
			listIn2[i] = list.get(i);
		}
		assertArrayEquals("Should be sorted and equal [1,2,3,5,7,9]", results2, listIn2);
		
	}
	@Test
	public void testRemoveObject() {
		Integer[] results = {1,5};
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should have size of 3",3, list.size());
		assertTrue("Should remove element 2", list.remove((Integer) 2));
		assertEquals("Size should be 2 after remove()", 2, list.size());
		Integer[] listIn = new Integer[2];
		for (int i = 0; i < list.size(); i++) {
			listIn[i] = list.get(i);
		}
		assertArrayEquals("List should be [1,5]", results, listIn);
		assertFalse("Should not be able to remove element not in list", list.remove((Integer) 7));
		assertEquals("Size should still be 2", 2, list.size());
		for (int i = 0; i < list.size(); i++) {
			listIn[i] = list.get(i);
		}
		assertTrue("Should remove element 5", list.remove((Integer) 5));
		assertTrue("Should remove element 1", list.remove((Integer) 1));
		assertTrue("List should now be empty", list.isEmpty());
		
	}
	@Test
	public void testRemoveIndex() {
		Integer[] results = {1,5};
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should have size of 3",3, list.size());
		assertTrue("Should remove element 2 (index 1)", list.remove(1));
		assertEquals("Size should be 2 after remove()", 2, list.size());
		Integer[] listIn = new Integer[2];
		for (int i = 0; i < list.size(); i++) {
			listIn[i] = list.get(i);
		}
		assertArrayEquals("List should be [1,5]", results, listIn);
		assertTrue("Should remove element in index 0", list.remove(0));
		assertEquals("Size should now be 1", 1, list.size());
		assertEquals("List should now only have elemnt 5", (Integer) 5, (Integer) list.get(0));
		assertTrue("Should remove last element in list", list.remove(0));
		assertTrue("List should now be empty", list.isEmpty());
		
		//assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(7);}, "Should return false if it cannot find the element");
	}
	@Test
	public void testRemoveAll() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		list.add(5);
		list.add(2);
		list.add(5);
		assertEquals("Should remove 3 fives", 3, list.removeAll(5));
		assertEquals("Size should now be 3", 3, list.size());
		
	}
	@Test
	public void testFirst() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should get first element 1", (Integer) 1, list.first());
		
	}
	@Test
	public void testLast() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should get last element 5", (Integer) 5, list.last());
		
	}
	@Test
	public void testClear() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertEquals("Should have size 3", 3, list.size());
		list.clear();
		assertTrue("List should be empty:", list.isEmpty());
	}
	@Test
	public void testContains() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		assertTrue("Should contain element 5", list.contains(5));
		assertFalse("Should not contain element 10", list.contains(10));
		
	}
	@Test
	public void testFirstIndex() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		list.add(2);
		list.add(2);
		assertEquals("Should get first index of 2 which is 1", 1, list.firstIndex(2));
		assertEquals("Should get first index of 5 which is 4", 4, list.firstIndex(5));
		assertEquals("Should return -1 if not found", -1, list.firstIndex(20));
		
	}
	@Test
	public void testLastIndex() {
		SortedList<Integer> list = new CircularSortedDoublyLinkedList<Integer>(new IntegerComparator());
		list.add(1);
		list.add(2);
		list.add(5);
		list.add(2);
		list.add(2);
		assertEquals("Should get last index of 2 which is 3", 3, list.lastIndex(2));
		assertEquals("Should get last index of 5 which is 4", 4, list.lastIndex(5));
		assertEquals("Should return -1 if not found", -1, list.lastIndex(20));
		
	}
	
}

