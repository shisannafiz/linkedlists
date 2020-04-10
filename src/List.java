public interface List<AnyType> {
	
	public int size();
	
	public void clear();
	
	public boolean isEmpty();
	
	public boolean add(AnyType newValue);
	
	public void add(int index, AnyType newValue);
	
	public AnyType get(int index);

	public AnyType set(int index, AnyType newValue);
	
	public AnyType remove(int index);
		
}
