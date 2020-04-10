import java.util.NoSuchElementException;

public class DoublyLinkedList<AnyType> implements List<AnyType>{

	private static class Node<AnyType> {
		
		private AnyType data;
		private Node<AnyType> prev;
		private Node<AnyType> next;
		
		public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
			setData(d);
			setPrev(p);
			setNext(n);
		}
		
	    public AnyType getData() { return data; }

	    public void setData(AnyType d) { data = d; }

	    public Node<AnyType> getPrev() { return prev; }

	    public void setPrev(Node<AnyType> p) { prev = p; }

	    public Node<AnyType> getNext() { return next; }

	    public void setNext(Node<AnyType> n) { next = n; }
		
	}
	
	private int size;
	private int modCount;
	private Node<AnyType> head;
	private Node<AnyType> tail;
	
	public DoublyLinkedList() {
	    head = new Node<AnyType>(null, null, null);
	    tail = new Node<AnyType>(null, null, null);
	    modCount = 0;
	    clear();	
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}
		
	public boolean isEmpty() {
	    return size == 0;
	}

	public AnyType get(int index) {
		Node<AnyType> indexNode = getNode(index);
		return indexNode.getData();
	}

	private Node<AnyType> getNode(int index){
		return getNode(index, 0, size + 1);
	}

	private Node<AnyType> getNode(int index, int lower, int upper){
		if(index < lower || index > upper) throw new IndexOutOfBoundsException();
		
		Node<AnyType> currNode;
		if(index < size/2) { 
			currNode = head.getNext();
			for(int i = 0; i < index; i++) currNode = currNode.getNext();
		} else {
			currNode = tail;
			for(int i = size; i > index; i--) currNode = currNode.getPrev();
		}
		
		return currNode;
	}
	
	public AnyType set(int index, AnyType newValue) {
		Node<AnyType> indexNode = getNode(index);
		AnyType oldValue = indexNode.getData();

		indexNode.setData(newValue);
		return oldValue;	
	}
	
	public boolean add(AnyType newValue) {
		if(size == 0) {
			Node<AnyType> newNode = new Node<AnyType>(newValue, head, tail);
			
			head.setNext(newNode);
			tail.setPrev(newNode);
			
			size++;
		} else {
			add(size, newValue);
		}
		return true;
	}

	public void add(int index, AnyType newValue) {
		Node<AnyType> nextNode = getNode(index);
		Node<AnyType> prevNode = nextNode.getPrev();
		Node<AnyType> newNode = new Node<AnyType>(newValue, prevNode, nextNode);
		
		prevNode.setNext(newNode);
		nextNode.setPrev(newNode);	
		
		size++;
	}
	
	private void addBefore(Node<AnyType> nextNode, AnyType newValue) {
		Node<AnyType> prevNode = nextNode.getPrev();
		Node<AnyType> newNode = new Node<AnyType>(newValue, prevNode, nextNode);
		
		prevNode.setNext(newNode);
		nextNode.setPrev(newNode);
		
		size++;
		modCount++;
	}

	public AnyType remove(int index) {
	    return remove(getNode(index));
	}
	
	private AnyType remove(Node<AnyType> currNode) {
	    Node<AnyType> prevNode = currNode.getPrev();
	    Node<AnyType> nextNode = currNode.getNext();

	    prevNode.setNext(nextNode);
	    nextNode.setPrev(prevNode);
	    
	    size--;
	    modCount++;

	    return currNode.getData();
	}
	
	public ListIterator<AnyType> iterator(){
		return new LinkedListIterator();
	}
	
	public class LinkedListIterator implements ListIterator<AnyType> {

	    private Node<AnyType> cursor;
//	    private int expectedModCount;
	    private boolean removeReady;
	    private boolean removeNext;
	    private boolean removePrev;
	    
	    public LinkedListIterator() {
	    	cursor = head.getNext();
		    removeReady = false;
		    removeNext = false;
		    removePrev = false;
	    }
	
		public boolean hasNext() {
			return cursor != tail;
		}
		
		public AnyType next() {
			if(!hasNext()) throw new NoSuchElementException();
			
			AnyType nextValue = cursor.getData();
			cursor = cursor.getNext();
			removeReady = true;
			removePrev = true;
			return nextValue;
		}

		public boolean hasPrevious() {
			return cursor.getPrev() != head;
		}
		
		public AnyType previous() {
			if(!hasPrevious()) throw new NoSuchElementException();
			
			cursor = cursor.getPrev();
			AnyType prevValue = cursor.getData();
			removeReady = true;
			removeNext = true;
			return prevValue;		
		}

		public void add(AnyType newValue) {
			DoublyLinkedList.this.addBefore(cursor, newValue);
			removeReady = false;
		}

		public void remove() {
			if(!removeReady) throw new IllegalStateException();
			
			if(removeNext) {
				Node<AnyType> temp = cursor.getNext();
				DoublyLinkedList.this.remove(cursor);
				cursor = temp;
			} else if(removePrev) {
			    DoublyLinkedList.this.remove(cursor.getPrev());
			}
			
			removeReady = false;
		    removeNext = false;
		    removePrev = false;
		}
			
	}
}

