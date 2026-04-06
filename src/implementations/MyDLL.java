package implementations;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E> {
	
	
	
	//attributes
	private MyDLLNode<E> head, tail;
	private int size;

	@Override
	public int size() {
		
		return size;
	}

	@Override
	public void clear() {
		head = tail = null;
		size = 0;
		
	}

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

	// replaces the element at a specific index.
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

	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}

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
	
	// Converts the list into an array.
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
