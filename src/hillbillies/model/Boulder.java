package hillbillies.model;

public class Boulder {
	
	public void boulder(int[] initialPosition) {
		this.setWeight();
		this.setPosition(initialPosition);
	}
	
	/**
	 * Set the weight of this boulder to a random weight between 10 and 50.
	 */
	private void setWeight() {
		double random = Math.random();
		int randomInt = (int) random*40;
		int weight = 10 + randomInt;
		this.weight = weight;
	}
	
	/**
	 * Return the weight of this boulder.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Variable registering the weight of this boulder
	 */
	private int weight;
}
