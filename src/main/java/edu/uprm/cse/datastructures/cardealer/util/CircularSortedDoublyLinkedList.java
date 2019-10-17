package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CircularSortedDoublyLinkedList<E> implements SortedList<E>{

	/**
	 * Additional sub class for the Node
	 */
	private class Node<E>{
		private E element;
		private Node<E> next, prev;
		public Node(E element, Node<E> next, Node<E> prev){
			this.element=element;
			this.next=next;
			this.prev=prev;
		}
		public Node(){
			this.element=null;
			this.next=null;
			this.prev=null;
		}
		public E getElement(){
			return element;
		}
		public void setElement(E element){
			this.element=element;
		}
		public Node<E> getNext(){
			return next;
		}
		public Node<E> getPrev(){
			return prev;
		}
		public void setNext(Node<E> next){
			this.next=next;
		}
		public void setPrev(Node<E> prev) {
			this.prev = prev;
		}
	}
	
	
	/**
	 * Creates an Iterator
	 * 
	 * @return the Node Element - if not found returns null, else returns the element.
	 */
	private class CircularDoublyLinkedListIterator<E> implements Iterator<E>{
		Node<E> temp = new Node<E>();
		@Override
		public boolean hasNext() {
			return temp.getNext().getElement() != null;
		}
		@Override
		public E next() {
			if(this.hasNext()) {
				return temp.getNext().getElement();
			}
			return null;
		}
	}
	
	private int currentSize;
	private Node<E> head;
	private Comparator<E> comp;

	public CircularSortedDoublyLinkedList(Comparator<E> comparator) {
		head = new Node<E>(null, head, head);
		this.head.setElement(null);
		this.head.setNext(head);
		this.head.setPrev(head);
		currentSize = 0;
		comp = comparator;
	}

	/**
	 * Returns an Iterator
	 * 
	 * @return Iterator - if the node isn't the head then return the iterator.
	 */
	@Override
	public Iterator<E> iterator() {
		List<E> newList = new ArrayList<E>();
		
		Node<E> newNode = this.head.getNext();
		
		while(newNode != head) {
			newList.add(newNode.getElement());
			newNode = newNode.getNext();
		}
		return newList.iterator();
	}
	
	/**
	 * Adds a new node either at the beginning, end and in the middle.
	 * 
	 * @param obj - a node element.
	 * @return true - if the insertion of the node was possible, else go to the next node.
	 */
	@Override
	public boolean add(E e) {
		if(this.isEmpty()){
			Node<E> newNode = new Node<E>(e, this.head, this.head);
			this.head.setNext(newNode);
			this.head.setPrev(newNode);
			currentSize++;
			return true;
		}
		else{
			Node<E> temp = head.getNext();
			while(temp.getElement()!=null) {
				if(this.comp.compare(e, temp.getElement())<=0) {
					Node<E> newNode = new Node<E>(e, temp, temp.getPrev());
					temp.getPrev().setNext(newNode);
					temp.setPrev(newNode);
					currentSize++;
					return true;
				}
				temp=temp.getNext();
			}
			Node<E> newNode = new Node<E>(e, this.head, this.head.getPrev());
			this.head.getPrev().setNext(newNode);
			this.head.setPrev(newNode);
			currentSize++;
			return true;
		}
	}

	@Override
	public int size() {
		return currentSize;
	}

	/**
	 * Removes the element in a node.
	 * 
	 * @param e - node element.
	 * @return false - if element is not there return false, else returns true.
	 */
	@Override
	public boolean remove(E e) {
		int i = this.firstIndex(e);
		if(i >= 0) {
			this.remove(i);
			return true;
		}
		return false;
		
	}
	
	/**
	 * removes a node at a specific index.
	 * 
	 * @param index - node index.
	 * @return true - if invalid index returns IndexOutOfBoundsException, else returns true.
	 */
	@Override
	public boolean remove(int index) {
		if(index > this.size() || index < -1) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		else {
			Node<E> temp = this.head;
			int i = 0;
			while(i != index) {
				temp = temp.getNext();
				i++;
			}
			Node<E> target = temp.getNext();
			temp.setNext(target.getNext());
			target.getNext().setPrev(temp);
			target.setElement(null);
			target.setNext(null);
			target.setPrev(null);
			target = null;
			this.currentSize--;
			return true;
			
		}
	}

	/**
	 * counts how many of the same element removed exists.
	 * 
	 * @param e - node element.
	 * @return int - if not found keeps counting, else returns an int.
	 */
	@Override
	public int removeAll(E e) {
		int count = 0;
		while(this.remove(e) != false) {
			count++;
		}
		return count;
	}
	
	/**
	 * Gets the first element in the list.
	 * 
	 * @return element - if empty returns null, else returns the element.
	 */
	@Override
	public E first() {
		return (this.isEmpty() ? null : this.head.getNext().getElement());
	}

	/**
	 * Gets the last element in the list.
	 * 
	 * @return element - if empty returns null, else returns the element.
	 */
	@Override
	public E last() {
		return (this.isEmpty() ? null : this.head.getPrev().getElement());
	}

	/**
	 * Gets the element at a specific index.
	 * 
	 * @param index - node index.
	 * @return element - if not reached end of list go to the next one, else returns the element.
	 */
	@Override
	public E get(int index) {
		Node<E> temp = this.head.getNext();
		int i = 0;
		while(i != index) {
			temp = temp.getNext();
			i++;
		}
		return temp.getElement();
	}

	@Override
	public void clear() {
		while(!this.isEmpty()) {
			this.remove(0);
		}

	}

	/**
	 * tells you if the element exists.
	 * 
	 * @param e - node element.
	 * @return true - if not found returns false, else returns true.
	 */
	@Override
	public boolean contains(E e) {
		if(this.firstIndex(e) != -1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Gets the first index of a node element.
	 * 
	 * @param e - node element.
	 * @return int - if not found returns -1, else returns the position i found.
	 */
	@Override
	public int firstIndex(E e) {
		int i = 0;
		for (Node<E> temp = this.head.getNext(); temp.getElement() != null; 
				temp = temp.getNext(), ++i) {
			if (temp.getElement().equals(e)) {
				return i;
			}
		}
		// not found
		return -1;
	}

	/**
	 * Gets the last index of a node element.
	 * 
	 * @param e - node element.
	 * @return int - if not found returns -1, else returns the position i found.
	 */
	@Override
	public int lastIndex(E e) {
		int i = 0, result = -1;
		for (Node<E> temp = this.head.getNext(); temp.getElement() != null; 
				temp = temp.getNext(), ++i) {
			if (temp.getElement().equals(e)) {
				result = i;
			}
		}
		// not found
		return result;
	}

}
