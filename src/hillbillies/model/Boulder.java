package hillbillies.model;

import java.util.List;

public class Boulder extends Material {

	public Boulder(List<Integer> intitialCube, World world){
		super(intitialCube, world);
	}
	
	public Boulder(Vector initialPosition, World world, int weight){
		super(initialPosition, world, weight);
	}
	
	
}
