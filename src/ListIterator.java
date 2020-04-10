public interface ListIterator<AnyType> {
	
	public void add(AnyType newValue);
	
	public void remove();

	public boolean hasPrevious();
	
	public boolean hasNext();

	public AnyType previous();
	
	public AnyType next();
	
}
