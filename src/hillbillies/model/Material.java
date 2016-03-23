package hillbillies.model;

import ogp.framework.util.ModelException;

public class Material {
	
	public void material(double[] initialPosition) throws ModelException {
		this.setWeight();
		this.setPosition(initialPosition);
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
	public void setPosition(double[] position) throws ModelException {
		if (! Vector.isPositionInsideWorld(position)) // TODO falling toevoegen
			throw new ModelException();
		this.position = new Vector(position);
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
}
