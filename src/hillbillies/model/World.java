/*
 * Een unit moet steeds een factie hebben. Dit zorgt er al voor 
 * dat de eerste functie van de facade niet kan werken.
 * Als je dit probleem oplost door te stellen dat een factie niet moet afhangen van een wereld dan impliceerd dit dat
 * je geen functie addUnit kan maken omdat je dan niet een enkele unit kan toevoegen. Je moet dan direct zijn hele 
 * factie toevoegen.
 * Een andere mogelijkheid is dat een unit steeds moet aangemaakt worden door een wereld. Die wereld zorgt er dan voor
 * dat de unit en een correcte factie zit. Maar bij deze oplossing kan er ook geen addUnit bestaan omdat de unit dan
 * per definitie al in de wereld zit.
 * Hierdoor staan wij toe dat een een unit geen factie heeft als er geen enkele wereld is die van zijn bestaan afweet.
 */


package hillbillies.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.Util;


public class World {
	/**
	 * Initialize this new world with given terrain.
	 * 
	 * @param  terrainTypes
	 *         The terrain of this new world.
	 **/         
	public World(int[][][] initialTerrainTypes, TerrainChangeListener givenModelListener){
		NbCubesX = initialTerrainTypes.length;
		NbCubesY = initialTerrainTypes[0].length;
		NbCubesZ = initialTerrainTypes[0][0].length;
		connectedToBorder = new ConnectedToBorder(NbCubesX, NbCubesY, NbCubesZ);
		modelListener = givenModelListener;
		ArrayList<Integer> position = new ArrayList<>();
		position.add(0);
		position.add(0);
		position.add(0);
		for (int x=0 ; x != NbCubesX ; x++){
			position.set(0, x);
			terrainTypes.add(new ArrayList<>());
			for (int y=0 ; y != NbCubesY; y++){
				position.set(1, y);
				terrainTypes.get(x).add(new ArrayList<>());
				for  (int z=0 ; z != NbCubesZ; z++){
					position.set(2, z);
					Cube cube = new Cube(position, initialTerrainTypes[x][y][z], this);
					terrainTypes.get(x).get(y).add(cube);
					modelListener.notifyTerrainChanged(x,y,z);
					if (initialTerrainTypes[x][y][z] != 1 
							&& initialTerrainTypes[x][y][z] != 2){
						connectedToBorder.changeSolidToPassable(x, y, z);
					}
				}
			}
		}
		this.collapseAllFloatingCubes();
	}
	
	/**
	 * Variable registering whether a cube is connected with the border or not.
	 */
	private ConnectedToBorder connectedToBorder;
	
	/**
	 * Variable registering the number of cubes in x-direction
	 */
	private final int NbCubesX;
	
	/**
	 * Variable registering the number of cubes in y-direction
	 */
	private final int NbCubesY;
	
	/**
	 * Variable registering the number of cubes in z-direction
	 */
	private final int NbCubesZ;
	
	/**
	 * Variable registering the change of terrain
	 */
	private final TerrainChangeListener modelListener;
	
	/**
	 * Returns the number of cubes in x-direction
	 */
	public int getNbCubesX(){
		return NbCubesX;
	}
	
	/**
	 * Returns the number of cubes in y-direction
	 */
	public int getNbCubesY(){
		return NbCubesY;
	}
	
	/**
	 * Returns the number of cubes in z-direction
	 */
	public int getNbCubesZ(){
		return NbCubesZ;
	}
	
	/**
	 * Returns whether the vector position is a position inside the world.
	 * 
	 * @param position
	 * 		The position to be checked
	 */
	public boolean isPositionInWorld(Vector position){
		if (position.getXCoord() < 0 || position.getXCoord() > NbCubesX
			|| position.getYCoord() < 0 || position.getYCoord() > NbCubesY
			|| position.getZCoord() < 0 || position.getZCoord() > NbCubesZ){
			System.out.println("Not a vector inside the world");
			return false;
		}
		return true;
	}
	
	/**
	 * Check whether the given position of a cube is inside this world.
	 * @param cubePosition
	 * 		The position of a cube to check.
	 * @return True if and only if the given position is inside this world.
	 * 		| result != (cubePosition.get(0) < 0 || cubePosition.get(0) >= NbCubesX
			|	|| cubePosition.get(1) < 0 || cubePosition.get(1) >= NbCubesY
			|	|| cubePosition.get(2) < 0 || cubePosition.get(2) >= NbCubesZ)
	 */
	boolean isCubeInWorld(List<Integer> cubePosition){
		if (cubePosition.get(0) < 0 || cubePosition.get(0) >= NbCubesX
			|| cubePosition.get(1) < 0 || cubePosition.get(1) >= NbCubesY
			|| cubePosition.get(2) < 0 || cubePosition.get(2) >= NbCubesZ){
			return false;
		}
		return true;
	}
	
	/**
	 * Check whether the given terrain type is a valid terrain type.
	 * @param terrainType
	 * 		The terrain type to check.
	 * @return True if and only if the given terrain type is an effective terrain type.
	 * 		| result == terrainType >=0 && terrainType <=3
	 */
	public boolean isValidTerrainType (int terrainType){
		return (terrainType >=0 && terrainType <=3);
	}
	
	/**
	 * Return the cube at the given position.
	 * @param position
	 * 		The position of the requested cube.
	 * @return The cube at the given position.
	 * @return If the cube does not exist null is returned.
	 */
	public Cube getCube(List<Integer> position){
		try{
			return terrainTypes.get(position.get(0)).get(position.get(1)).get(position.get(2));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Cube getCube(int[] position) {
		try{
			return terrainTypes.get(position[0]).get(position[1]).get(position[2]);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * @param unfilterdCubes
	 * 		The set of wish we need to filter the passable cubes.
	 * @return The set that contains only the passable cubes of the unfilterdCubes set.
	 */
	public static Set<Cube> filterPassableCubes(Set<Cube> unfilterdCubes){
		Set<Cube> remainingCubes = new HashSet<>();
		for (Cube cube : unfilterdCubes){
			if (cube.isSolid())
				remainingCubes.add(cube);
		}
		return remainingCubes;
	}
	
	/**
	 * 0: Air
	 * 1: Rock
	 * 2: Wood
	 * 3: Workshop
	 * 
	 * Variable referencing the terrain of this world.
	 */
	private List<List<List<Cube>>> terrainTypes = new ArrayList<>(); 
	
	public void setTerrainType(Cube cube, int terrainType){
		if (!isValidTerrainType(terrainType)){
			throw new IllegalArgumentException();		
		}
		if (terrainType != 1 && terrainType != 2 && cube.isSolid()){
			terrainTypes.get(cube.getPosition().get(0)).get(cube.getPosition().get(1))
							.set(cube.getPosition().get(2), cube.changeTerrainType(terrainType));
			modelListener.notifyTerrainChanged(cube.getPosition().get(0), 
												cube.getPosition().get(1),
												cube.getPosition().get(2));
			double rand = Math.random();
			if (rand < 0.125) {
				new Log(cube.getCenterOfCube(), this);
			} else if (rand < 0.25){
				new Boulder(cube.getCenterOfCube(), this);
			}
			connectedToBorder.changeSolidToPassable(cube.getPosition().get(0), 
													cube.getPosition().get(1),
													cube.getPosition().get(2));
			Set<Cube> neighbours = cube.getDirectAdjenctCubes();
			Set<Cube> solidNeighbours = filterPassableCubes(neighbours);
			neighbours.removeAll(solidNeighbours);
			for (Cube solidNeighbour : solidNeighbours){
				this.collapseIfFloating(solidNeighbour);
			}
		}
		terrainTypes.get(cube.getPosition().get(0)).get(cube.getPosition().get(1))
						.set(cube.getPosition().get(2), cube.changeTerrainType(terrainType));
		modelListener.notifyTerrainChanged(cube.getPosition().get(0), 
				cube.getPosition().get(1),
				cube.getPosition().get(2));		
	}
	
	/**
	 * Collapse the cube cube if floating.
	 * 
	 * @post If this cube was not connected to a border,
	 * 		its terraintype is air.
	 */
	private void collapseIfFloating(Cube cube){
		if (cube.isSolid()){
			if (!this.isSolidConnectedToBorder(cube)){
				this.setTerrainType(cube, 0);
			}
		}
	}
	
	/**
	 * Returns whether the cube cube is connected with the border.
	 * 
	 * @param cube
	 * 		The cube to be checked.
	 */
	public boolean isSolidConnectedToBorder(Cube cube){
		if (cube.isSolid()){
			return connectedToBorder.isSolidConnectedToBorder(cube.getPosition().get(0), 
																cube.getPosition().get(1),
																cube.getPosition().get(2));
		}
		return true;
	}
	
	/**
	 * Collapse all the cubes who are floating.
	 * 
	 * @effect All terraintypes of cubes who are not connected to 
	 * 		the border are changed in air.
	 * 		
	 */
	private void collapseAllFloatingCubes(){
		for (int x=0 ; x != NbCubesX ; x++){
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; z++){
					 List<Integer> cubeList = new ArrayList<>();
					 cubeList.add(x);
					 cubeList.add(y);
					 cubeList.add(z);
					 this.collapseIfFloating(getCube(cubeList));

				}
			}
		}
	}

	/**
	 * Set registering all materials in this world.
	 */
	private Set<Material> materials = new HashSet<>();
	
	/**
	 * Returns whether the cube cube is a workshop with a log and a
	 * boulder
	 * 
	 * @param cube
	 * 		The cube to be checked.
	 */
	public boolean isWorkshopWithLogAndBoulder(Cube cube){
		if (cube.getTerrainType() != 3){
			return false;
		}
		List<Material> materialsOnCube = this.getMaterialsAt(cube);
		boolean logInStock = false;
		boolean boulderInStock = false;
		for (Material material : materialsOnCube) {
			if (!logInStock && material instanceof Log) {
				logInStock = true;
			}
			if (!boulderInStock && material instanceof Boulder) {
				boulderInStock = true;
			}
			if (boulderInStock && logInStock) {
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Returns a random valid position inside this world.
	 */
	public Cube generateRandomValidPosition(){
		Cube cube;
		do{
			List<Integer> cubeList = new ArrayList<>();
			cubeList.add((int) (Math.random() * getNbCubesX()));
			cubeList.add((int) (Math.random() * getNbCubesY()));
			cubeList.add((int) (Math.random() * getNbCubesZ()));
			cube = getCube(cubeList); 
			}while (cube.isSolid() || !cube.getCenterOfCube().hasSupportOfSolidUnderneath(this));
		return cube;
	}
	
	/**
	 * Returns the material at the cube cube which should be picked up.
	 * If no such material exists, returns null.
	 * 
	 * @param cube
	 * 		The cube to be checked.
	 */
	public Material materialToPickUp(Cube cube){
		List<Material> materialsOnCube = this.getMaterialsAt(cube);
		Material materialToReturn = null;
		for (Material material : materialsOnCube) {
			if (material instanceof Boulder) {
				return material;
			}
			if (material instanceof Log) {
				materialToReturn = material;
			}
		}
		return materialToReturn;
	}
		
	/**
	 * Returns a list with all materials who are at the cube cube.
	 * 
	 * @param cube
	 * 		The cube to be checked.
	 */
	public List<Material> getMaterialsAt(Cube cube) { 
		List<Material> foundMaterials = new ArrayList<>();
		for (Material material : materials){
			if(material.getPosition().getEnclosingCube(this).equals(cube)){
	        	foundMaterials.add(material); 
			}
        }
	    return foundMaterials;
	}
	
	/**
	 * Returns a set with all materials in this world.
	 */
	public Set<Material> getMaterials() {
		return this.materials;
	}
	
	/**
	 * Returns all logs in this world.
	 */
	public Set<Log> getLogs() {
		Set<Log> logs = new HashSet<>();
		for (Material material : materials){
			if(material instanceof Log){
	        	logs.add((Log) material);
			}
        }
	    return logs;
	}
	
	/**
	 * Return all boulders in this world.
	 */
	public Set<Boulder> getBoulders() {
		Set<Boulder> boulders = new HashSet<>();
		for (Material material : materials){
			if(material instanceof Boulder){
	        	boulders.add((Boulder) material);
			}
        }
	    return boulders;
	}
	
//	private void setMaterial(Vector position, Material material){
//		//if (!isValidMaterialType(materialType)){ 
//		//	throw new IllegalArgumentException();		
//		//}
//		
//	}
	
	/**
	 * Add material to this world.
	 * 
	 * @param material
	 * 		the material to be added.
	 * 
	 * @post material is added to this world.
	 */
	public void addMaterial(Material material) {
		materials.add(material);
	}
	
	//public void addLog(Log log) {
	//	logs.add(log);
	//}
	
	//public void addBoulder(Boulder boulder) {
	//	boulders.add(boulder);
	//}
	
	/**
	 * Remove material from this world
	 * 
	 * @param material
	 * 		the material to be removed.
	 * 
	 * @post material is removed from this world.
	 */
	public void removeMaterial(Material material) {
		materials.remove(material);
	}
	
	//private boolean isValidMaterialType (int materialType){
	//	return (materialType >=1 && materialType <=2);
	//}
	
	/*Faction*/
	
	/**
	 * Set registering the factions in this world.
	 */
	private Set<Faction> factions = new HashSet<>();
	
	/**
	 * Return the active factions in this world.
	 */
	public Set<Faction> getActiveFactions() {
		return this.factions;
	}
	
/**
 * Return the number of factions in this world.
 */
	private int getNbOffFactions() {
		return this.getActiveFactions().size();
	}

	
	/**
	 * Add a new faction to this world.
	 * 
	 * @param faction
	 * 		The faction to be added.
	 * 
	 * @post if there was space for a new faction, the faction is added to 
	 * 		this world.
	 */
	public void addFaction(Faction faction) {
		if (!isValidNbOfFactions(this.getNbOffFactions()+1)){
			throw new IllegalArgumentException();
		}
		System.out.println("factionadded");
		this.factions.add(faction);
	}
	
	/**
	 * Return whether the number of factions is a valid number for this world.
	 * 
	 * @param number
	 * 		the number to check.
	 * 
	 */
	private boolean isValidNbOfFactions(int number){
		if (number > 5){
			return false;
		}
		return true;
	}
	
	/**
	 * Remove faction from this world.
	 */
	public void removeFaction(Faction faction) {
		factions.remove(faction);
	}
	
	/**
	 * Return the smallest faction in this world.
	 */
	private Faction getSmallestFaction() {
		Faction smallestFaction = (Faction) this.getActiveFactions().toArray()[0];
		for (Faction faction : factions){
			if (faction.getNbOffUnitsInFaction() 
	        		< smallestFaction.getNbOffUnitsInFaction())
	        	smallestFaction = faction;
		}
	    return smallestFaction;
	}
	
	/*Unit*/
	
	/**
	 * Return all units in this world.
	 */
	public Set<Unit> getUnits(){
		Set<Unit> unitsInWorld = new HashSet<>();
		for (Faction faction : factions){
			unitsInWorld.addAll(faction.getUnitsInFaction());
		}
	    return unitsInWorld;
	}
	
	/**
	 * Return the number of units in this world.
	 */
	private int getNbOfUnits(){
		int nbUnitsInWorld = 0;
		for (Faction faction : factions){
			nbUnitsInWorld = nbUnitsInWorld + faction.getNbOffUnitsInFaction();
		}
	    return nbUnitsInWorld;
	}
	
	/**
	 * Returns a unit in this world.
	 * 
	 * @post a new unit is added to this world.
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior){
		Unit newUnit =  new Unit("Harry", generateRandomValidPosition().getCenterOfCube(), 
				enableDefaultBehavior, this); //TODO name not final
		this.addUnit(newUnit);
		return newUnit;
	}
	
	/**
	 * Add unit in a faction in this world.
	 * 
	 * @post if there were less than 5 factions in this world, a new faction is
	 * 			created and unit is part of it, else unit is added to the 
	 * 			smallest faction.
	 */
	public void addUnit(Unit unit){
		if (this.getNbOfUnits()!=100){
			unit.setWorld(this);
			if (this.getNbOffFactions() != 5){
				System.out.print("new faction");
				Faction newFaction =  new Faction(this);
				//this.addFaction(newFaction);
				newFaction.addUnit(unit);
			} else {
				this.getSmallestFaction().addUnit(unit);
			}
		}		
	}
	
	/**
	 * Remove the unit unit from this world.
	 * 
	 * @param unit
	 * 		The unit to be removed.
	 * 
	 * @post The unit unit doesn't exist in this world.
	 */
	private void removeUnit(Unit unit) {
		unit.getFaction().removeUnit(unit);
	}
	

	/*Time*/
	
	// No documentation required
	public void advanceTime(double dt) {
		if (!isValidTickTime(dt)){
			throw new IllegalArgumentException("Invalid tick time");
		}
		Set<Unit> unitsInWorld = this.getUnits();
		Set<Material> materialsInWorld = this.getMaterials();
		for (Unit unit : unitsInWorld){
			unit.advanceTime(dt);
		}
		for (Material material : materialsInWorld) {
			material.advanceTime(dt);
		}
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
		return ((0 <= tickTime) && (Util.fuzzyGreaterThanOrEqualTo(maxTimeLapse, tickTime)));
	}

	/**
	 * Variable registering the maximum time interval
	 */
	private final static double maxTimeLapse = 0.2;

	public Set<Cube> getAccessibleNeigbours (Cube cube){
		Set<Cube> neighbours = cube.getNeighbourCubes();
		neighbours.removeAll(filterPassableCubes(neighbours));
		Set<Cube> accessibleNeighbours = new HashSet<>();
		for (Cube neighbour: neighbours){
			if (neighbour.getCentreOfCube().hasSupportOfSolid(this)){
				accessibleNeighbours.add(neighbour);
			}
		}
		return accessibleNeighbours;
	}

}


