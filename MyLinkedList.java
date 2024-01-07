import java.util.NoSuchElementException;

/**
 * COPYRIGHT MATERIAL -- DO NOT DISTRIBUTE
 *
 * @author Mehrdad Sabetzadeh 
 */
public class MyLinkedList<E> {
	private static class Node<T> {
		private T value;
		private Node<T> backward, forward;

		private Node(T value, Node<T> backward, Node<T> forward) {
			this.value = value;
			this.backward = backward;
			this.forward = forward;
		}
	}

	private Node<E> head;
	private Node<E> tail;

	public MyLinkedList(E[] array) {		

		if (array == null || array.length == 0) {
			throw new IllegalArgumentException();
		}
				
		head = new Node<E>(array[0], null, null);
		Node<E> current = head;
		for(int i = 1; i < array.length; i++) {
			current.forward = new Node<E>(array[i], current, null);
			current = current.forward;
		}
		tail = current;
	}

	public void tweak() {
		Node<E> current, prev;
		
		current = tail;
		
		while (current.backward != null && current.backward != head) {
			prev = current.backward;
			current.backward = current.backward.backward;
			current = prev;
		}
	}

	public String toString() {

		StringBuffer buffer;
		buffer = new StringBuffer("Forward traversal:  [");

		Node<E> current = head;

		while (current != null) {
			if (current != head) {
				buffer.append("->");
			}
			buffer.append(current.value);
			current = current.forward;
		}

		buffer.append("]");
	
		buffer.append(System.lineSeparator());
	
		buffer.append("Backward traversal: [");

		current = tail;

		while (current != null) {
			if (current != tail) {
				buffer.append("->");
			}
			buffer.append(current.value);
			current = current.backward;
		}
		
		buffer.append("]");

		return buffer.toString();
	}
	
	public Iterator<E> iterator() {
		return new MyLinkedListIterator();
	}

	private class MyLinkedListIterator implements Iterator<E> {

        private Node<E> currentIterator;
        private boolean isForward;

        public MyLinkedListIterator() {
            currentIterator = null;
            isForward = true;
        }

        public E next() {
			if (!hasNext())
				throw new NoSuchElementException();

            if(currentIterator == null) { // we are at head and moving forward
            	currentIterator = head;
            } else if (currentIterator == tail) {
            	isForward = false;
            	currentIterator = tail.backward;
            } 
            else {
                if (isForward) {
                	currentIterator = currentIterator.forward;
                } else {
                    currentIterator = currentIterator.backward;
                }
  
            }
            return currentIterator.value;
        }
        
        public boolean hasNext(){
            if (isForward)
                return currentIterator == null || head != tail;
            else 
            	return currentIterator.backward != null;
		}
	}
}