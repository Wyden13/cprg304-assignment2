package implementations;

/**
 * MyDLLNode represents a single node in a double linked list
 * Each node store:
 * - an element (data)
 * - a reference to the next and previous node
 * 
 * @param <E> the type of element stored in the node
 */
public class MyDLLNode<E>
{
	/**
	 *  data element stored in the node
	 *  reference to the next node
	 *  reference to previous node
	 */
	private E element;
	private MyDLLNode<E> next;
	private MyDLLNode<E> prev;
	
	/**
	 * constructs a new node with the specified element
	 * the next and previous references are ionitialized to null
	 * 
	 * @param element the data to store in the node
	 */
	public MyDLLNode(E element) {
		this.element = element;
		this.next = null;
		this.prev = null;
	}
	
	/**
	 * return the next node
	 * @return reference to the next node
	 */
	public MyDLLNode<E> getNext() {
		return next;
	}
	
	/**
	 * set the next node
	 * @param next the node to link as the next node
	 */
	public void setNext(MyDLLNode<E> next) {
		this.next = next;
	}
	
	/**
	 * return the previous node
	 * @return reference to the previous node
	 */
	public MyDLLNode<E> getPrev() {
		return prev;
	}
	
	/**
	 * set the previous node
	 * @param prev the node to link as the previous node
	 */
	public void setPrev(MyDLLNode<E> prev) {
		this.prev = prev;
	}
	
	/**
	 * return the element store in this node
	 * @return the element value
	 */
	public E getElement() {
		return element;
	}
	
	/**
	 * update the elements stored in this node
	 * @param element the new value to store
	 */
	public void setElement(E element) {
		this.element = element;
	}

}
