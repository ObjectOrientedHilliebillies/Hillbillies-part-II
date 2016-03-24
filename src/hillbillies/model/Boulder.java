package hillbillies.model;

import ogp.framework.util.ModelException;

public class Boulder extends Material {

	public Boulder(Vector initialPosition, World world) throws ModelException {
		super(initialPosition, world);
	}
	
	public Boulder(Vector initialPosition, World world, int weight) throws ModelException {
		super(initialPosition, world, weight);
	}
	
	
}
