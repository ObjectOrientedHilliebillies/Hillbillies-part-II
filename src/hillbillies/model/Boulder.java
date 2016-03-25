package hillbillies.model;


public class Boulder extends Material {

	public Boulder(int[] intitialCube, World world){
		super(intitialCube, world);
	}
	
	public Boulder(Vector initialPosition, World world, int weight){
		super(initialPosition, world, weight);
	}
	
	
}
