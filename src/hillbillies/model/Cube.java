package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of cubes involving a position, terrain type and world.
 * @invar The position of the cube must be a valid position.
 * 		| isValidPosition()
 * @invar The terrain type of the cube most be a valid terrain type.
 * 		| isValidTerrainType()
 * @invar The world of this cube must be a valid world.
 * 		| isValidWorld()
 * 
 * https://github.com/ObjectOrientedHilliebillies/Hillbillies-part-II.git
 * @version 1.0
 * @author Jonas Vantrappen & Victor Van Eetvelt
 */
@Value
public class Cube{
	/**
	 * Initialize this new cube with the given position, terrainType and world.
	 * @param position
	 * 		The position of this new cube.
	 * @param terrainType
	 * 		The terrainType of this new cube.
	 * @param world
	 * 		The world of this cube.
	 * @post The position of this new cube is equal to the given position.
	 * 		| new.getPosition() == position
	 * @post The terrain type of this new cube is equal to the given terrain type.
	 * 		| new.getTerrainType() == terainType
	 * @post The world of this new cube is equal to the given world.
	 * 		| new.getWorld() == world
	 * @throws IllegalArgumentException
	 * 		The given position is not a valid position
	 * 		| ! isValidPosition(position)
	 * @throws IllegalArgumentException
	 * 		The given terrain type is not a valid terrain type for any cube.
	 * 		| ! isValidTerrainType
	 * @throws ClassCastException
	 *  	The given world is not effective.
	 *  	| ! isValidWorld
	 */
	public Cube(List<Integer> position, int terrainType, World world){
		if (! isValidWorld(world))
			throw new ClassCastException("Non-effective world");
		this.world = world;
		
		if (!isValidPosition(position, this.world))
			throw new IllegalArgumentException("Invalid Position");
		if (!isValidTerrainType(terrainType, this.world))
			throw new IllegalArgumentException("Invalid terrain type");
		this.position = new ArrayList<>(position);
		this.terrainType = terrainType;
	}
	
	/**
	 * Initialise this new cube with given position, given world and terrain type air.
	 * @param position
	 * 		The position of this new cube.
	 * @param world
	 * 		The world of this new cube.
	 * @effect This new cube is initialised with the given position and world 
	 * 			and terrain type air.
	 * 		| this(position, 0, world)
	 */
	public Cube(List<Integer> position, World world){
		this(position, 0, world);
	}
	
	/**
	 * Initialise this new cube with the position and world of the given cube and the given terrain type.
	 * @param otherCube
	 * 		The other cube of wish the position en world will be copieed.
	 * @param terraintype
	 * 		The terrain type of this new cube
	 * @effect This new cube is initialised with the position and world of the given cube and the
	 * 			given terrain type.
	 */
	public Cube changeTerrainType(int terrainType){
		return new Cube(getPosition(), terrainType, getWorld());
	}
	
	/**
	 * Return the world of this cube
	 */
	@Basic @Raw @Immutable
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Check whether the given world is a valid world for any cube.
	 * @param world
	 * 		The world to check.
	 * @return True if and only if the given world is not null.
	 * 		| result == (world != 0)
	 */
	public static boolean isValidWorld(World world){
		return (world != null);
	}
	
	/**
	 * Variable referencing the world of this cube.
	 */
	private final World world;
	
	/** 
	 * Return the position of this cube
	 */
	@Basic @Raw @Immutable
	public List<Integer> getPosition(){
		return this.position;
	}
	
	/**
	 *Check whether the given position is a valid position for this cube. 
	 *@param position
	 *		The position to check.
	 *@return True if and only if the position is inside the world of this cube.
	 *		| result == world.isCubeInWorld()
	 */
	public static boolean isValidPosition(List<Integer> position, World world){
		return world.isCubeInWorld(position);
	}
	
	/**
	 * Variable referencing the position of this Cube.
	 */
	private final List<Integer> position;
	
	/**
	 * Return the terrain type of this cube.
	 * 0: Air
	 * 1: Rock
	 * 2: Wood
	 * 3: Workshop
	 */
	@Basic @Raw @Immutable
	public int getTerrainType(){
		return this.terrainType;
	}
	
	/**
	 * Check whether the given terrain type is a valid terrain type for any cube.
	 * @param terrainType
	 * 		The terrain type to check
	 * @return True if and only of the terrain type is an effective terrain type.
	 * 		| result == world.isValidTerrainType
	 */
	public static boolean isValidTerrainType(int terrainType, World world){
		return world.isValidTerrainType(terrainType);
	}
	
	/**
	 * Variable referencing the terrain type of this cube.
	 */
	private final int terrainType;
	
	/**
	 * Return the centre of this cube.
	 * @return The resulting vector point to the middle of this cube.
	 */
	public Vector getCenterOfCube(){
		return new Vector(getPosition().get(0) + 0.5, 
							getPosition().get(1) + 0.5, 
							getPosition().get(2) + 0.5);
	}
	
	/**
	 * Return the adjenct cubes of this cube.
	 * @return The cubes adjenct to this cube.
	 */
	public Set<Cube> getNeighbourCubes(){
		List<Integer> thisCube = new ArrayList<>(getPosition());
		Set<Cube> neighbourCubes = new HashSet<Cube>();
		for (int x=-1; x!=2; x++){
			thisCube.set(0, getPosition().get(0) + x); 
			for (int y=-1; y!=2; y++){
				thisCube.set(1, getPosition().get(1) + y); 
				for (int z=-1; z!=2; z++){
					thisCube.set(2, getPosition().get(2) + z); 
					Cube neighbour = getWorld().getCube(thisCube);
					if (neighbour != null){
						neighbourCubes.add(neighbour);
					}
				}
			}
		}
		neighbourCubes.remove(this);
		return neighbourCubes;
	}
	
	/**
	 * Return a random cube that is adjenct to this cube.
	 * @return A random adjenct cube that is adjenct to this cube.
	 */
	
	
	/**
	 * Variable referring the to offsets of direct adjenct neighbours.
	 */
	private final Integer[][] directAdjacentOffsets = new Integer[][] { { -1, 0, 0 }, { +1, 0, 0 }, 
		{ 0, -1, 0 }, { 0, +1, 0 },{ 0, 0, -1 }, { 0, 0, +1 } };
	
	/**
	 * Return the directly adjenct cubes of this cube
	 * @return The cubes directly adjenct to this cube.
	 */
	public Set<Cube> getDirectAdjenctCubes(){
		Integer[] thisCube = {getPosition().get(0),getPosition().get(1),getPosition().get(2)};
		Set<Cube> directAdjectCubes = new HashSet<>();
		for (Integer[] offset : directAdjacentOffsets){
			List<Integer> directAdject = Arrays.asList(Vector.sum(thisCube, offset));
			if (world.isCubeInWorld(directAdject)){
				directAdjectCubes.add(getWorld().getCube(directAdject));
			}
		}
		return directAdjectCubes;
	}
	
	/**
	 * Check whether this cube is a neighbour of the given cube.
	 * @param otherCube
	 * 		The potential neighbour cube.
	 * @return True if and only if the cubes are neighbours.
	 * @throws ClassCastException
	 * 		The otherCube is not effective
	 */
	public boolean isNeighbourCube(Cube otherCube){
		if (otherCube == null)
			throw new ClassCastException();
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(getPosition().get(i) - otherCube.getPosition().get(i));
		    if (difference == 1)
		    	neighbourForAtleastOneComponent = true;
		    else if (difference != 0)
		    	return false;
		}
		return neighbourForAtleastOneComponent;
	}
	
	/**
	 * Return whether this cube is solid or not.
	 * @return True if and only if this cube is not made of dirt of wood.
	 */
	public boolean isSolid(){
		return !(getTerrainType() != 1 && getTerrainType() != 2);
	}
	
	/**
	 * Return whether this cube is passable or not.
	 * @return True if and only if this cube is not solid.
	 */
	public boolean isPassable(){
		return !isSolid();
	}
	
	/**
	 * Return a textual representation of this cube
	 * 
	 * @return A string consisting of a textual representation of the position
	 * 			enclosed in square brackets.
	 */
	@Override
	public String toString(){
		return "["+getPosition().get(0)+", "
				  +getPosition().get(1)+", "
				  +getPosition().get(2)+"]";
	}
	
	/** 
	 * Check whether this cube is equal to the given object.
	 * @return True if and only if the given object is effective,
	 * 		   if this cube and the given object belong to the same class,
	 * 		   and if this cube and the given object have the same position and world.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other.getClass() != this.getClass())
			return false;
		Cube otherCube = (Cube)other; 
		return (this.getPosition() == otherCube.getPosition()&& this.getWorld() == otherCube.getWorld());
	}
	
	/**
	 * Returns the hash code of this cube.
	 */
	@Override
	public int hashCode(){
		return getPosition().hashCode() + this.getWorld().hashCode();
	}
	
	/**
	 * Returns the centre of this cube.
	 * @return
	 * 		| new Vector(getPosition().get(0) + 0.5,
	 * 					 getPosition().get(1) + 0.5,
	 * 					 getPosition().get(2) + 0.5)
	 */
	public Vector getCentreOfCube(){
		return new Vector(getPosition().get(0) + 0.5,
						  getPosition().get(1) + 0.5,
						  getPosition().get(2) + 0.5);
	}
}