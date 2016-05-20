package hillbillies.model;

import java.util.HashMap;

public interface IVariableDictionary<S, E> {
	
	public E createValue();
	
	public S createName();
}
