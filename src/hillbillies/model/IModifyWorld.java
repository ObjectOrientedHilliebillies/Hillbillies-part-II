package hillbillies.model;

import java.util.List;
import java.util.Set;

public interface IModifyWorld<C, M> {
	
	/**
	 * Return whether this cube is solid connected to the border
	 * 
	 * @param cube
	 * 		The cube to check
	 */
	public boolean isSolidConnectedToBorder(C cube);
	
	/**
	 * Make this cube disappear when it is not connected to the border.
	 * 
	 * @param cube
	 * 		The cube to possibaly disappear
	 */
	public void collapseIfFloating(C cube);
	
	/**
	 * Set the terraintype of the given cube to the given terraintype.
	 * 
	 * @param cube
	 * 		The cube to be changed
	 * @param terrainType
	 * 		The new terraintype for the given cube
	 */
	public void setTerrainType(C cube, int terrainType);
	
	/**
	 * Make all cubes who are not solid connected to the border collapse
	 */
	public void collapseAllFloatingCubes();
	
	/**
	 * Return whether the given cube is a workshop with a log and a boulder
	 * 
	 * @param cube
	 * 		The cube to be checked
	 */
	public boolean isWorkshopWithLogAndBoulder(C cube);
	
	/**
	 * Returns a random position in the world valid for any unit
	 */
	public C generateRandomValidPosition();
	
	/**
	 * Returns a list with all the materials at the given cube
	 * 
	 * @param cube
	 * 		The cube to be checked
	 */
	public List<M> getMaterialsAt(C cube);
	
	/**
	 * Returns the material at the given cube that a unit should pick up
	 * 
	 * @param cube
	 * 		The cube to be checked
	 */
	public M materialToPickUp(C cube);
	
	/**
	 * Return the log closest to the given cube
	 * 
	 * @param thisCube
	 * 		The cube to be checked
	 */
	public Log getClosestLog(C thisCube);
	
	/**
	 * Return the boulder closest to the given cube
	 * 
	 * @param thisCube
	 * 		The cube to be checked
	 */
	public Boulder getClosestBoulder(C thisCube);
	
	/**
	 * Return the workshop closest to the given cube
	 * 
	 * @param thisCube
	 * 		The cube to be checked
	 */
	public C getClosestWorkshop(C thisCube);
	
	/**
	 * Add a new material
	 * 
	 * @param material
	 * 		The new material to be added
	 */
	public void addMaterial(M material);
	
	/**
	 * Remove a material
	 * 
	 * @param material
	 * 		The material to be removed
	 */
	public void removeMaterial(M material);
	
	/**
	 * Returns a set with all the accessible neighbour cubes for the given cube
	 * 
	 * @param cube
	 * 		The cube to be checked
	 */
	public Set<Cube> getAccessibleNeigbours (C cube);

}
