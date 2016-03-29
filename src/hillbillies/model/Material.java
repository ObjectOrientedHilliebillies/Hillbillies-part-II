package hillbillies.model;


public class Material {
	
	protected Material(int[] intialCube, World world){
		this.setWeight();
		this.setWorld(world);
		this.setPosition(Vector.getCentreOfCube(intialCube));
		
		world.addMaterial(this);
	}
	
	protected Material(Vector initialPosition, World world, int weight){
		this.setWorld(world); 
		this.setWeight(weight);
		this.setPosition(initialPosition);
		world.addMaterial(this);
	}
	
	/**
	 * Set the weight of this material to a random weight between 10 and 50.
	 */
	private void setWeight() {
		double random = Math.random();
		int randomInt = (int) random*40;
		int weight = 10 + randomInt;
		this.weight = weight;
	}
	
	private void setWeight(int weight) {
		if (isValidWeight(weight))
			this.weight = weight;
	}
	
	private boolean isValidWeight(int weight) {
		if (10 <= weight && weight <= 50)
			return true;
		return false;			
	}
	
	/**
	 * Return the weight of this material.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Variable registering the weight of this material
	 */
	private int weight;
	
	/**
	 * Set the position of this material to the given position.
	 */
	private void setPosition(Vector position){
		if (! this.world.isPositionInWorld(position)) // TODO falling toevoegen
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	/**
	 * Returns the position of this material.
	 */
	public Vector getPosition() {
		return this.position;
	}
	
	/**
	 * Variable registering the position of this material.
	 */
	private Vector position;
	
	private World getWorld() {
		return this.world;
	}
	
	private void setWorld(World world) {
		this.world = world;
	}
	
	private World world;
}
