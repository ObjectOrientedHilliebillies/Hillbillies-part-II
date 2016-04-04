package hillbillies.model;

public class Log extends Material {

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
<<<<<<< HEAD
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
=======
	public Log(Vector intitialPosition, World world){
		super(intitialPosition, world);
		world.addLog(this);

>>>>>>> refs/remotes/origin/VictorLaptop
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
	public Log(Vector initialPosition, World world, int weight){
		super(initialPosition, world, weight);
		// FIXME Why is there no "world.addLog(this)" here?
	}
	
	
}
