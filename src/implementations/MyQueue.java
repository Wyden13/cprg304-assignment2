package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * MyQueue is a queue data structure follow FIFO (first-in, first-out)
 * @param <E> the type of element stored in the queue
 * 
 * Feature:
 * -No Fixed capacity
 * -Efficient enqueue at tail 
 * -Efficient dequeue from head
 * -Iterator support for traversal
 */
public class MyQueue<E> implements QueueADT<E>{
	
	/**
	 * Internal storage for the queue using a double linked list
	 * Constructs an empty queue
	 */
	private MyDLL<E> q;
	public MyQueue() {
		q = new MyDLL<E>();
	}
	
	/**
	 * Add an element to the rear of the queue
	 * 
	 * @param toAdd the element to be added
	 * @throws NullPointerException if toAdd is null
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		q.add(toAdd);
	}
	
	/**
	 * Removes and returns the front element of the queue
	 * 
	 * @return the element at the front
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public E dequeue() throws EmptyQueueException {
		if(q.isEmpty()) throw new EmptyQueueException();
		return q.remove(0);
	}

	/**
	 *Returns (without removing) the front element of the queue
	 * 
	 * @return the element at the front
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public E peek() throws EmptyQueueException {
		if(q.isEmpty()) throw new EmptyQueueException();
		return q.get(0);
	}
	/**
	 * Removes all elements from the queue
	 */
	@Override
	public void dequeueAll() {
		q.clear();
	}
	
	/**
	 * Check if the queue is empty
	 * @return true if queue has no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return q.isEmpty();
	}
	
	/**
	 * Check whether the queue contains a specific element
	 * 
	 * @param toFind the element to search for
	 * @return true if found, false otherwise
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {	
		if(toFind==null) throw new NullPointerException();
		return q.contains(toFind);
	}
	
	/**
	 * Searches for an element and returns its index in the queue
	 * Position is 1-based (front = position 1)
	 * 
	 * @param toFind the element to search for
	 * @return the position (1-based) if found, otherwise -1
	 * 
	 */
	@Override
	public int search(E toFind) {
		if(toFind == null || !q.contains(toFind)) {
			return -1;
		}
		for (int i  = 0 ;i < q.size();i++) {
			if(toFind.equals(q.get(i))) {
				return i+1;
			}
		}
		return -1;
	}
	
	/**
	 * Returns an iterator to traverse the queue from front to rear
	 * 
	 * @return an iterator over the elements
	 */
	@Override
	public Iterator<E> iterator() {
		return q.iterator();
	}

	@Override
	public boolean equals(QueueADT<E> that) {
		if(that == null || that.size() != this.size()) {
			return false;
		}
		Iterator<E> thisIt = this.iterator();
		Iterator<E> thatIt = that.iterator();
		while(thisIt.hasNext() && thatIt.hasNext()) {
			E a = thisIt.next();
			E b = thatIt.next();
			if(!a.equals(b)) {
				return false;
			}
		}
		return true;
		
	}

	/**
	 * Converts the queue into an Object array
	 * 
	 * @return array containing all elements in FIFO order
	 */
	@Override
	public Object[] toArray() {
		return q.toArray();
	}
	
	/**
	 * Converts the queue into a typed array
	 * 
	 * @param holder array to store elements
	 * @return array containing queue elements
	 * @throws NullPointerException if holder is null
	 */
	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) {
			throw new NullPointerException();
		}
		return q.toArray(holder);
		
	}
	
	/**
	 * Check if the queue is full
	 * Since this is a dynamically sized structure, it is never full
	 * 
	 * @return always false
	 */
	@Override
	public boolean isFull() {
		return false;
		
	}
	
	/**
	 * return the number of elements in the queue
	 * @return the queue size
	 */
	@Override
	public int size() {
		return q.size();
	}

}
