package implementations;

public class MyDLLNode<E> {
	// attributes
	private E element;
	MyDLLNode<E> next;
	MyDLLNode<E> prev;
	/**
	 * @param element
	 */
	public MyDLLNode(E element) {
		super();
		this.element = element;
	}
	/**
	 * @return the element
	 */
	public E getElement() {
		return element;
	}
	/**
	 * @return the next
	 */
	public MyDLLNode<E> getNext() {
		return next;
	}
	/**
	 * @return the prev
	 */
	public MyDLLNode<E> getPrev() {
		return prev;
	}
	/**
	 * @param element the element to set
	 */
	public void setElement(E element) {
		this.element = element;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(MyDLLNode<E> next) {
		this.next = next;
	}
	/**
	 * @param prev the prev to set
	 */
	public void setPrev(MyDLLNode<E> prev) {
		this.prev = prev;
	}
	
	
}
