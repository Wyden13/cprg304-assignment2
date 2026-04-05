package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

// Add <E> in both sides of implements
public class MyArrayList<E> implements ListADT<E> {
	
	private static final int MULTIPLIER = 2;  // resizing 0 to 2
	//constants
	private final int INITIAL_CAPACITY = 10;
	//attributes
	private E[] array;
	private int size;
	
	//constructor
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		array = (E[]) new Object[INITIAL_CAPACITY] ;
	}

	@Override
	public int size() { 
		
		return size;  // changed 0 to size
	}

	@Override
	public void clear() {
		array =(E[]) new Object[INITIAL_CAPACITY];
		size = 0;
		
	}

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

	private void shiftToRight(int index) {
		for(int i = size; i > index; i--) {
			array[i] = array[i-1];
		}
		
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException {
		
		if(toAdd == null) {
			throw new NullPointerException();
		}
		ensureCapacity();
		array[size++] = toAdd;
		return true;
	}

	private void ensureCapacity() {
		if(size == array.length ) {
			E[] newArray = (E[]) new Object[array.length * MULTIPLIER];
			for(int i = 0; i < array.length; i++) {
				newArray[i] = array[i];
			}
			array = newArray;
		}
		
	}

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

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

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

	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}

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

	@Override
	public Iterator<E> iterator() {
		
		return new MyArrayListIterator();
	}
	
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
		
		@Override
		public boolean hasNext() {
			
			return pos < copy.length;
		}

		@Override
		public E next() throws NoSuchElementException {

			if(pos >= copy.length) {
				throw new NoSuchElementException();
			}
			
			return copy[pos++];
		}
		
	}


}
