package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * MyArrayList is an implementation of a dynamic array that implements the ListADT interface
 * 
 * @param <E> type of elements stored in the list
 * Feature:
 * - Dynamic resizing using a grow multiplier
 * - Indexed access to element
 * - Insertion, deletion, and replacement
 * - Iterator support using a snapshot copy
 */
// Add <E> in both sides of implements
public class MyArrayList<E> implements ListADT<E> {
	
	/**
	 * Multiplier used to expend array capacity 
	 * Initial capacity of the array
	 * Internal array used to store elements
	 * Current number of elements in the list
	 */
	private static final int MULTIPLIER = 2;  // resizing 0 to 2
	//constants
	private final int INITIAL_CAPACITY = 10;
	//attributes
	private E[] array;
	private int size;
	
	/**
	 * Constructs an empty list with default initial capacity
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		array = (E[]) new Object[INITIAL_CAPACITY] ;
	}
	
	/**
	 * Returns the number of elements in the list
	 * @return current size of the list
	 */
	@Override
	public int size() { 
		
		return size;  // changed 0 to size
	}
	
	/**
	 * Removes all elements and resets the list
	 */
	@Override
	public void clear() {
		array =(E[]) new Object[INITIAL_CAPACITY];
		size = 0;
		
	}
	
	/**
	 * Inserts an element at a specific index
	 * 
	 * @param index position to insert
	 * @param toAdd element to add
	 * @return true if success
	 * @throws NullPointerException if toAdd is null
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) {
			throw new NullPointerException();
		}
		if(index <0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		ensureCapacity();
		shiftToRight(index);
		array[index] = toAdd;
		size++;
//		array[size++] = toAdd;
		return true;
	}
	
	/**
	 * Shift elements to the right starting from a given index
	 * Used during insertion
	 * 
	 * @param index starting position for shifting
	 */
	private void shiftToRight(int index) {
		for(int i = size; i > index; i--) {
			array[i] = array[i-1];
		}
		
	}
	
	/**
	 * Appends an element to the end of the list
	 * 
	 * @param toAdd element to add
	 * @return true if successful
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		
		if(toAdd == null) {
			throw new NullPointerException();
		}
		ensureCapacity();
		array[size++] = toAdd;
		return true;
	}

	/**
	 * Ensure the internal array has enough capacity
	 * Multiply the size when capacity is reached
	 */
	private void ensureCapacity() {
		if(size == array.length ) {
			E[] newArray = (E[]) new Object[array.length * MULTIPLIER];
			for(int i = 0; i < array.length; i++) {
				newArray[i] = array[i];
			}
			array = newArray;
		}
		
	}
	
	/**
	 * Adds all elements from another list
	 * 
	 * @param toAdd list of elements to add
	 * @return true if modified
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if(toAdd == null) {
			throw new NullPointerException();
		}
		
		Iterator<? extends E> it = toAdd.iterator();
		while(it.hasNext()) {
			add(it.next());
		}
		return true;
	}
	
	/**
	 * Retrieves the element at a given index
	 * 
	 * @param index position of the element
	 * @return the element at the index
	 * @throws IndexOutOfBoundsException if index is valid
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	/**
	 * remove the element at a given index
	 * 
	 * @param index position of element
	 * @return removed element
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E removed = array[index];
		
		for(int i = index; i<size-1; i++) {
			array[i] = array[i + 1];
		}
		array[size - 1] = null;
		size--;
		return removed;
	}

	/**
	 * Removes the first occurrence of a specific index
	 * 
	 * @param toRemove element to remove
	 * @return removed element or null if not found
	 * @throws NullPOinterException if toRemove is null
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if(toRemove == null) {
			throw new NullPointerException();
		}
		
		for(int i = 0; i < size; i++) {
			if(array[i].equals(toRemove)) {
				return remove(i);
			}
		}
		return null;
	}
	
	/**
	 * Replaces the element at the specific index
	 * 
	 * @param index position to update
	 * @param toChange the new value
	 * @return old value at the index
	 * @throws NullPointerException if toChange is null
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(toChange == null) {
			throw new NullPointerException();
		}
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E old = array[index];
		array[index] = toChange;
		return old;
	}
	
	/**
	 * Check if the list is empty
	 * 
	 * @return true if size is 0, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}
	
	/**
	 * Check if the list contains a specific element
	 * 
	 * @param toFind element to search for
	 * @return true if found, false otherwise
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) {
			throw new NullPointerException();
		}
		
		for(int i = 0; i< size; i++) {
			if(array[i].equals(toFind)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts the list into a typed array
	 * 
	 * @param toHold array to store elements
	 * @return array containing list elements
	 * @throws NullPointerException if toHold is null
	 */
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if(toHold == null) {
			throw new NullPointerException();
		}
		if(toHold.length <size) {
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}
		System.arraycopy(array, 0, toHold, 0, size);
		return toHold;
	}
	
	/**
	 * Converts the list into an Object array
	 * 
	 * @return array containing all elements
	 */
	@Override
	public Object[] toArray() {
		
		Object[] toHold = new Object[size];
		for(int i = 0; i < size; i++) {
			toHold[i]= array[i];
		} 
//		or
//		System.arraycopy(array, 0, toHold, 0, size);
		return toHold;
	}
	
	/**
	 * returns an iterator to traverse the list
	 * a snapshot copy is used to avoid concurrent modification issues.
	 * 
	 * @return an iterator over the elements
	 */
	@Override
	public Iterator<E> iterator() {
		
		return new MyArrayListIterator();
	}
	
	/**
	 * Iterator implementation for MyArrayList
	 * uses a snapshot of the list at creation time
	 */
	private class MyArrayListIterator implements Iterator<E> {

		//attribute
		private E[] copy;
		private int pos;
		
		@SuppressWarnings("unchecked")
		public MyArrayListIterator() {
			copy = (E[]) new Object[size];
			for(int i = 0; i < size; i++) {
				copy[i] = array[i];
			}
		}
		
		/**
		 * Check if more elements are available
		 * @return true if more element exist
		 */
		@Override
		public boolean hasNext() {
			
			return pos < copy.length;
		}
		
		/**
		 * return the next element in the iteration
		 * 
		 * @return next element
		 * @throws NoSuchElementException if no more elements exist
		 */
		@Override
		public E next() throws NoSuchElementException {

			if(pos >= copy.length) {
				throw new NoSuchElementException();
			}
			
			return copy[pos++];
		}
		
	}


}