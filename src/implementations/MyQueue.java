package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<E> implements QueueADT<E>{
	
	//attributes
	private MyDLL<E> q;
	public MyQueue() {
		q = new MyDLL<E>();
	}
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		q.add(toAdd);
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(q.isEmpty()) throw new EmptyQueueException();
		return q.remove(0);
	}

	@Override
	public E peek() throws EmptyQueueException {
		if(q.isEmpty()) throw new EmptyQueueException();
		return q.get(0);
	}

	@Override
	public void dequeueAll() {
		q.clear();
	}

	@Override
	public boolean isEmpty() {
		return q.isEmpty();
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {	
		if(toFind==null) throw new NullPointerException();
		return q.contains(toFind);
	}

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

	@Override
	public Object[] toArray() {
		return q.toArray();
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) {
			throw new NullPointerException();
		}
		return q.toArray(holder);
		
	}

	@Override
	public boolean isFull() {
		return false;
		
	}

	@Override
	public int size() {
		return q.size();
	}

}
