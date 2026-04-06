package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack is an implementation of a stack data structure follow LIFO (last-in, first-out) principle
 * 
 * @param <E> type of elements stored in the stack
 * Feature:
 * -Dynamic resizing (no fixed capacity)
 * -Efficient push and pop operations
 * -Provides iterator traversal from top to bottom
 */
public class MyStack<E> implements StackADT<E> {
	
	/**
	 * Internal storage for the stack using a dynamic array list
	 * Constructs an empty stack
	 */
    private MyArrayList<E> list;
    public MyStack() {
        list = new MyArrayList<>();
    }
    
    /**
     * Pushes an element onto the top of the stack 
     * 
     * @param toAdd the element to add
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null onto stack.");
        }
        list.add(toAdd); 
    }
    
    /**
     * Removes and returns the top element of the stack
     * 
     * @param the top element
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int lastIndex = list.size() - 1;
        E value = list.get(lastIndex);
        list.remove(lastIndex);
        return value;
    }
    
    /**
     * Returns (without removing) the top element of the stack
     * 
     * @param the top element
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }
    
    /**
     * removes all elements from the stack
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Checks if the stack is empty
     * @return true if stack has no element, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }
    
    /**
     *  Converts the stack into an Object array
     *  The order is from top to bottom
     *  
     *  @return array containing stack elements in LIFO order
     */
    @Override
    public Object[] toArray() {
        Object[] original = list.toArray();
        Object[] reversed = new Object[original.length];

        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }

        return reversed;
    }
    
    /**
     * Converts the stack into a typed array
     * The order is from top to bottom
     * 
     * @param holder array to store elements
     * @return array containing stack elements
     * @throws NullPointerException if holder is null
     */
    @SuppressWarnings("unchecked")
	@Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Holder array cannot be null.");
        }

        Object[] original = list.toArray();
        int size = original.length;

        if (holder.length < size) {
            holder = (E[]) java.lang.reflect.Array.newInstance(
                holder.getClass().getComponentType(), size);
        }

        for (int i = 0; i < size; i++) {
            holder[i] = (E) original[size - 1 - i];
        }

        return holder;
    }
    
    /**
     * Checks if the stack contains a specific element
     * 
     * @param toFind the element to search for
     * @return true if found, false otherwise
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null.");
        }
        return list.contains(toFind);
    }
    
    /**
     * Searches for an element and returns its position relative to the top
     * The position is 1-based (top = position 1)
     * 
     * @param toFind the element to search for
     * @return position from the top if found, otherwise -1
     */
    @Override
    public int search(E toFind) {
        if (toFind == null || !list.contains(toFind)) return -1;

        // search from top of stack (end of list)
        for (int i = list.size() - 1, distance = 1; i >= 0; i--, distance++) {
            E element = list.get(i);
            if (toFind.equals(element)) {
                return distance; // 1-based distance from top
            }
        }
        return -1;
    }
    
    /**
     * Returns an iterator to traverse the stack from top to bottom
     * a snapshot of the stack is taken to prevent modification issue
     * 
     * @return an iterator over the stack elements
     */
    @Override
    public Iterator<E> iterator() {
    	return new Iterator<E>() {
          
            private final E[] snapshot = (E[]) toArray(); 
            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < snapshot.length;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext()) throw new NoSuchElementException();
                return snapshot[pos++];
            }
        };
    }
    
    /**
     * Compare this stack with another stack for equality
     * 2 stacks are equal if they have the same size and contain same elements in the same order
     * 
     * @param that the other stack to compare
     * @return true if both stacks are equal, false otherwise
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();

        while (thisIt.hasNext() && thatIt.hasNext()) {
            E a = thisIt.next();
            E b = thatIt.next();

            if (a == null && b != null) return false;
            if (a != null && !a.equals(b)) return false;
        }

        return true;
    }
    
    /**
     * Return the number of elements in the stack
     * @return the stack size
     */
    @Override
    public int size() {
        return list.size();
    }
    
    /**
     * Check if the stack is full
     * Since this is dynamically sized, it will never be full
     * 
     * @return always false
     */
    @Override
    public boolean stackOverflow() {
        return false; 
    }
}