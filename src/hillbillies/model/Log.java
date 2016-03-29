package hillbillies.model;

import java.util.List;

public class Log extends Material {

	public Log(List<Integer> intitialCube, World world){
		super(intitialCube, world);
	}

	public Log(Vector initialPosition, World world, int weight){
		super(initialPosition, world, weight);
	}
	
	
}
