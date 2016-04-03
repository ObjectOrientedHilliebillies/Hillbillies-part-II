package hillbillies.model;


public class Material {
	
/**
 * Initialize this new material with the given position and the given world.
 * 
 * @param initialPosition
 * @param world
 * 
 * @post if the given position is a position inside the world, the position of 
 * 		this material is equal to the given position.
 * @post if the given world is a valid world, the world of this material is 
 * 		equal to the given world.
 */
	protected Material(int[] intialCube, World world){
		this.setWeight();
		this.setWorld(world);
		this.setPosition(Vector.getCentreOfCube(intialCube));
		
		world.addMaterial(this);
	}
	

/**
 * Initialize this new material with the given position, world and weight.
 * 
 * @param initialPosition
 * @param world
 * @param weight
 * 
 * @post if the given position is a position inside the world, the position of 
 * 		this material is equal to the given position.
 * @post if the given world is a valid world for any material, the world of 
 * 		this material is equal to the given world.
 * @post if the given weight is a valid weight for any material, the weight
 * 		of this material is equal to the given weight.
 */
	protected Material(int[] initialPosition, World world, int weight){
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
	
	/**
	 * Set the weight of this material to the given weight
	 */
	private void setWeight(int weight) {
		if (isValidWeight(weight))
			this.weight = weight;
	}
	
	/**
	 * Check whether the given weight is a valid weight for
	 * any material.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return 
	 *       | result == maxWeight > weight >= (strength+agility)/2 
	*/
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

	/**
	 * Return the world of this material
	 */
	private World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of this material to the given world
	 */
	private void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Variable registering the world of this material.
	 */
	private World world;
}
