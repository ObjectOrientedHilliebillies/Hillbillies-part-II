package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.Util;

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
	protected Material(Vector initialPosition, World world){
		this.setWeight();
		this.setWorld(world);
		this.setPosition(initialPosition.getEnclosingCube(world).getCentreOfCube());
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
	protected Material(Vector initialPosition, World world, int weight){
		this.setWorld(world); 
		this.setWeight(weight);
		this.setPosition(initialPosition.getEnclosingCube(world).getCentreOfCube());
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
	private void setWeight(int weight) throws IllegalArgumentException {
		if (isValidWeight(weight))
			this.weight = weight;
		else throw new IllegalArgumentException();
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
	private void setPosition(Vector vector){
		if (! this.world.isPositionInWorld(vector)) // TODO falling toevoegen
			throw new IllegalArgumentException();
		this.position = vector;
	}
	
	//No documentation needed
	public void advanceTime(double tickTime) {
		if (!isValidTickTime(tickTime)){
			throw new IllegalArgumentException();
		}
		else{
			this.setTickTime(tickTime);
		}
		this.falling();
	}
	
	/**
	 * Returns whether this unit is falling or not.
	 */
	private boolean isFalling() {
		return this.falling;
	}
	
	/**
	 * Variable registering whether this unit is falling or not.
	 */
	private boolean falling;
	
	/**
	 * Variable registering the fall speed.
	 */
	private final static Vector fallSpeed = new Vector(0, 0, -3);
	
	/**
	 * If this material is not falling, check whether it should be falling 
	 * 	and initiate the fall.
	 * If this material is falling, check whether it should stop falling
	 * 	and stop the fall.
	 * 
	 * @post if this material was falling and had a solid block underneath, 
	 * 		it is not falling anymore. If this material was not falling and
	 * 		hadn't a solid block underneath, it is falling.
	 */
	private void falling(){
		if (!this.isFalling()){
			if (!this.position.hasSupportOfSolidUnderneath(this.getWorld())){
				System.out.println("Material started falling");
				this.falling = true;
			}
		}	
		if (this.isFalling()){
			if (this.position.hasSupportOfSolidUnderneath(this.getWorld())){
				this.position = getCube().getCenterOfCube();
			}else{
				this.position = Vector.sum(this.position, fallSpeed.scale(this.tickTime));
			}}
		}
	
	/**
	 * Return the cube this material is in.
	 */
	private Cube getCube() {
		List<Integer> cubeCoord = new ArrayList<>();
		cubeCoord.add((int) getPosition().getXCoord());
		cubeCoord.add((int) getPosition().getYCoord());
		cubeCoord.add((int) getPosition().getZCoord());
		return getWorld().getCube(cubeCoord);
	}

	/**
	 * Returns the world of this material.
	 */
	private World getWorld() {
		return this.world;
	}


	/**
	 * Check whether the given tick time is a valid tick time.
	 *  
	 * @param  tickTime
	 *         The tick time to check.
	 * @return 
	 *       |  | result == (0 < tickTime) && (tickTime < maxTimeLapse)
	*/
	private boolean isValidTickTime(double tickTime) {
		if (!(0 <= tickTime) && (Util.fuzzyGreaterThanOrEqualTo(maxTimeLapse, tickTime))){
			System.out.println(tickTime);
			// TODO tijd nul lijkt ook te mogen (zie testen twee)
		}

		return ((0 <= tickTime) && (Util.fuzzyGreaterThanOrEqualTo(maxTimeLapse, tickTime)));
	}
	
	/**
	 * Set the time to the given time.
	 * 
	 * @param  time
	 *         The new time.
	 * @post   The time is equal to the given time
	 *       | new.getTime() == time
	 *  //FIXME moet dit niet checken of het een valid time is en een exception throwen?
	 */
	@Raw
	private void setTickTime(double time) {
		this.tickTime = time;
	}
	
	/**
	 * Variable registering the current time
	 */
	private double tickTime;

	/**
	 * Variable registering the maximum time interval
	 */
	private double maxTimeLapse = 0.2;
	
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
