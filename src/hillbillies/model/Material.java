package hillbillies.model;

import ogp.framework.util.ModelException;

public class Material {
	
	public Material(Vector initialPosition, World world) throws ModelException {
		this.setWeight();
		this.setPosition(initialPosition);
		this.setWorld(world);
		world.addMaterial(this);
	}
	
	public Material(Vector initialPosition, World world, int weight) throws ModelException {
		this.setWeight(weight);
		this.setPosition(initialPosition);
		this.setWorld(world); 
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
	public void setPosition(Vector position) throws ModelException {
		if (! position.isPositionInsideWorld()) // TODO falling toevoegen
			throw new ModelException();
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
	
	public World getWorld() {
		return this.world;
	}
	
	private void setWorld(World world) {
		this.world = world;
	}
	
	private World world;
}
