package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;
import java.util.Iterator;

public class CircularSortedDoublyLinkedList<E> implements SortedList<E>{


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


	@Override
	public Iterator<E> iterator() {
		return new CircularDoublyLinkedListIterator<E>();
	}

	@Override
	public boolean add(E obj) {
		if(this.isEmpty()){
			Node<E> newNode = new Node<E>(obj, this.head, this.head);
			this.head.setNext(newNode);
			this.head.setPrev(newNode);
			currentSize++;
			return true;
		}
		else{
			Node<E> temp = head.getNext();
			while(temp.getElement()!=null) {
				if(this.comp.compare(obj, temp.getElement())<=0) {
					Node<E> newNode = new Node<E>(obj, temp, temp.getPrev());
					temp.getPrev().setNext(newNode);
					temp.setPrev(newNode);
					currentSize++;
					return true;
				}
				temp=temp.getNext();
			}
			Node<E> newNode = new Node<E>(obj, this.head, this.head.getPrev());
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

	@Override
	public boolean remove(E e) {
		int i = this.firstIndex(e);
		if(i >= 0) {
			this.remove(i);
			return true;
		}
		return false;
		
	}

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

	@Override
	public int removeAll(E obj) {
		int count = 0;
		while(this.remove(obj) != false) {
			count++;
		}
		return count;
	}

	@Override
	public E first() {
		return (this.isEmpty() ? null : this.head.getNext().getElement());
	}

	@Override
	public E last() {
		return (this.isEmpty() ? null : this.head.getPrev().getElement());
	}

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
