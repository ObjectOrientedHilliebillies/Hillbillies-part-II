package hillbillies.model;


public class Boulder extends Material {
	
	/**
	 * Initialize this new boulder with the given position and the given world.
	 * 
	 * @param initialPosition
	 * @param world
	 * 
	 * @post if the given position is a position inside the world, the position of 
	 * 		this boulder is equal to the given position.
	 * @post if the given world is a valid world, the world of this boulder is 
	 * 		equal to the given world.
	 */
	public Boulder(int[] initialPosition, World world){
		super(initialPosition, world);
		
		this.getWorld().addBoulder(this);
	}
	
	/**
	 * Initialize this new boulder with the given position, world and weight.
	 * 
	 * @param initialPosition
	 * @param world
	 * @param weight
	 * 
	 * @post if the given position is a position inside the world, the position of 
	 * 		this boulder is equal to the given position.
	 * @post if the given world is a valid world for any boulder, the world of 
	 * 		this boulder is equal to the given world.
	 * @post if the given weight is a valid weight for any boulder, the weight
	 * 		of this boulder is equal to the given weight.
	 */
	public Boulder(int[] initialPosition, World world, int weight){
		super(initialPosition, world, weight);
	}
	
	
}
