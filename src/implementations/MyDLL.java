package implementations;

import utilities.Iterator;
import utilities.ListADT;

/**
 * MyDLL<E> is a double linked list that implements the ListADT interface. It stores element in nodes for references to both previous and next elements.
 * @param <E> type of elements stored in the list
 * 
 * features:
 * - Dynamic size (grows and shrinks as needed)
 * - Index access
 * - insertion/ removal at head, tail, and middle
 * - iterator support for traversal
 * 
 * 
 */
public class MyDLL<E> implements ListADT<E> {
	
	/**
	 * Reference to first node and last node
	 */
	private MyDLLNode<E> head, tail;
	/**
	 * Number of elements in the list
	 */
	private int size;
	
	/**
	 * return the number of elements in the list
	 */
	@Override
	public int size() {
		
		return size;
	}
	
	/**
	 * remove all elements from the list (resets head and tail to null and size to 0)
	 */
	@Override
	public void clear() {
		head = tail = null;
		size = 0;
		
	}

	/**
	 * Insert an element at a specific index
	 * 
	 * @param index position to insert at
	 * @param toAdd element to insert
	 * @return true if insertion is successful
	 * @throws NullPointerException if toAdd is null
	 * @throws
	 *  IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
	    if (toAdd == null) {
	        throw new NullPointerException();
	    }
	    if (index < 0 || index > size) {
	        throw new IndexOutOfBoundsException();
	    }

	    MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

	    if (index == 0) { // insert at head
	        newNode.setNext(head);
	        if (head != null) head.setPrev(newNode);
	        head = newNode;
	        if (size == 0) tail = newNode;
	    } else if (index == size) { // insert at tail
	        tail.setNext(newNode);
	        newNode.setPrev(tail);
	        tail = newNode;
	    } else { // insert in middle
	        MyDLLNode<E> current = head;
	        for (int i = 0; i < index; i++) {
	            current = current.getNext();
	        }
	        newNode.setNext(current);
	        newNode.setPrev(current.getPrev());
	        current.getPrev().setNext(newNode);
	        current.setPrev(newNode);
	    }

	    size++;
	    return true;
	}
	
	/**
	 * Appends an element to the end of the list
	 * 
	 * @param toAdd element to insert
	 * @return true if the list is modified
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if(toAdd == null) {
			throw new NullPointerException();
		}
		
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		
		// empty list
		if(head == null) {
			head = tail = newNode;
		} else {
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail = newNode;
		}
		
		size++;
		return true;
	}

	/**
	 * Appends all elements from another list to this list
	 * 
	 * @param toAdd elements to add
	 * @return true if successful
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
	    if (toAdd == null) {
	        throw new NullPointerException();
	    }
	    boolean changed = false;
	    for (int i = 0; i < toAdd.size(); i++) {
	        add(toAdd.get(i));
	        changed = true;
	    }
	    return changed;
	}
	
	/**
	 * Retrieves the element at a specific index
	 * 
	 * @param index position of element
	 * @return element at the index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		
	}
		MyDLLNode<E> current =head;
		
		for(int i =0; i < index; i++) {
			current = current.getNext();
		}
		
		return current.getElement();
	}
	
	/**
	 * Remove the element at a specific index
	 * 
	 * @param index position of element
	 * @return remove element at the index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		MyDLLNode<E> current = head;
		
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		
		if(current.getPrev() != null) {
			current.getPrev().setNext(current.getNext());
		} else {
			head = current.getNext(); //removing head
		}
	
		if(current.getNext() != null) {
			current.getNext().setPrev(current.getPrev());
		} else { 
			tail = current.getPrev(); // removing tail
		}

		size--;
		return current.getElement();
	}
	
	/**
	 * Remove the first occurrence of a specific index
	 * 
	 * @param toRemove element to remove
	 * @return the removed element, or null if not found
	 * @throws NullPointerExpcetion if toRemove is null
	 */
	@Override
	public E remove(E toRemove) {
	    if(toRemove == null) {
	        throw new NullPointerException();
	    }

	    MyDLLNode<E> current = head;
	    int index = 0;

	    while(current != null) {
	        if(current.getElement().equals(toRemove)) {
	            return remove(index);
	        }
	        current = current.getNext();
	        index++;
	    }

	    return null;
	}

	/**
	 * Replace the element at a given index
	 * 
	 * @param index position to update
	 * @param toChange new value
	 * @return the old value of that index
	 * @throws NullPointerException if toChange is null
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
	    if (toChange == null) {
	        throw new NullPointerException();
	    }
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }

	    MyDLLNode<E> current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.getNext();
	    }

	    E oldElement = current.getElement();
	    current.setElement(toChange);
	    return oldElement;
	}
	
	/**
	 * Check if the list is empty
	 * @return ttrue if size is 0, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}
	
	/**
	 * Check if the list contains a given element
	 * 
	 * @param toFind element to search for
	 * @return true if found, false if not
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(E toFind) {
		if(toFind == null) {
			throw new NullPointerException();
	}
		MyDLLNode<E> current =head;
		
		while(current != null) {
			if(current.getElement().equals(toFind)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	/**
	 * Converts the list into an Object array
	 * 
	 * @return array containing all elements in order
	 */
	@Override
	public Object[] toArray() {
	    Object[] array = new Object[size];
	    int i = 0;
	    MyDLLNode<E> current = head;
	    while (current != null) {
	        array[i++] = current.getElement();
	        current = current.getNext();
	    }
	    return array;
	}
	
	/**
	 * Convert the list into a typed array
	 * 
	 * @param toHold array to store elements
	 * @return array containing all elements
	 * @throws NullPointerException if toHold is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
	    if (toHold == null) throw new NullPointerException();

	    // If array is too small, create a new one of same type
	    if (toHold.length < size) {
	        toHold = (E[]) java.lang.reflect.Array.newInstance(
	            toHold.getClass().getComponentType(), size
	        );
	    }

	    MyDLLNode<E> current = head;
	    int i = 0;
	    while (current != null) {
	        toHold[i++] = current.getElement();
	        current = current.getNext();
	    }

	    // If array is larger than list, set the next element to null
	    if (toHold.length > size) {
	        toHold[size] = null;
	    }

	    return toHold;
	}
	
	/**
	 * return an iterator to traverse the list from head to tail
	 * 
	 * @return an iterator over the elements
	 */
	@Override
	public Iterator<E> iterator() {
	    return new Iterator<E>() {
	        private MyDLLNode<E> current = head;

	        @Override
	        public boolean hasNext() {
	            return current != null;
	        }

	        @Override
	        public E next() {
	            if (!hasNext()) throw new java.util.NoSuchElementException();
	            E element = current.getElement();
	            current = current.getNext();
	            return element;
	        }
	    };
	}

}