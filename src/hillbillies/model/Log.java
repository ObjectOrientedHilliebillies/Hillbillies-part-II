package hillbillies.model;

import java.util.List;

public class Log extends Material {
<<<<<<< HEAD
	
	/**
	 * Initialize this new log with the given position and the given world.
	 * 
	 * @param initialPosition
	 * @param world
	 * 
	 * @post if the given position is a position inside the world, the position of 
	 * 		this log is equal to the given position.
	 * @post if the given world is a valid world, the world of this log is 
	 * 		equal to the given world.
	 */
	public Log(int[] initialPosition, World world){
		super(initialPosition, world);
		// TODO Auto-generated constructor stub
		
<<<<<<< HEAD
		//this.getWorld().addLog(this);
=======
		this.getWorld().addLog(this);
=======

	public Log(List<Integer> intitialCube, World world){
		super(intitialCube, world);
>>>>>>> refs/remotes/origin/PathfindingTryout
>>>>>>> origin/master
	}
	
	
	/**
	 * Initialize this new log with the given position, world and weight.
	 * 
	 * @param initialPosition
	 * @param world
	 * @param weight
	 * 
	 * @post if the given position is a position inside the world, the position of 
	 * 		this log is equal to the given position.
	 * @post if the given world is a valid world for any log, the world of 
	 * 		this log is equal to the given world.
	 * @post if the given weight is a valid weight for any log, the weight
	 * 		of this log is equal to the given weight.
	 */
	public Log(int[] initialPosition, World world, int weight){
		super(initialPosition, world, weight);
	}
	
	
}
