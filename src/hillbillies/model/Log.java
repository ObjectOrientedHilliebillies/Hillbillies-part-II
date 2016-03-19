package hillbillies.model;

public class Log {
	
	public void log(int[] initialPosition) {
		this.setWeight();
		this.setPosition(initialPosition);
	}
	
	/**
	 * Set the weight of this log to a random weight between 10 and 50.
	 */
	private void setWeight() {
		double random = Math.random();
		int randomInt = (int) random*40;
		int weight = 10 + randomInt;
		this.weight = weight;
	}
	
	/**
	 * Return the weight of this log.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Variable registering the weight of this log.
	 */
	private int weight;		
	}
}
