package hillbillies.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;


public class World {
	/**
	 * Initialize this new world with given terrain.
	 * 
	 * @param  terrainTypes
	 *         The terrain of this new world.
	 **/         
	private ConnectedToBorder connectedToBorder;
	public World(int[][][] initialTerrainTypes, TerrainChangeListener givenModelListener){
		NbCubesX = initialTerrainTypes.length;
		NbCubesY = initialTerrainTypes[0].length;
		NbCubesZ = initialTerrainTypes[0][0].length;
		
		this.terrainTypes = new int[NbCubesX][NbCubesY][NbCubesZ];
		connectedToBorder = new ConnectedToBorder(NbCubesX, NbCubesY, NbCubesZ);
		modelListener = givenModelListener;
		for (int x=0 ; x != NbCubesX ; x++){
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; z++){
					terrainTypes[x][y][z] = initialTerrainTypes[x][y][z];
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

	private final int NbCubesX;
	private final int NbCubesY;
	private final int NbCubesZ;
	private final TerrainChangeListener modelListener;
	
	public int getNbCubesX(){
		return NbCubesX;
	}
	
	public int getNbCubesY(){
		return NbCubesY;
	}
	
	public int getNbCubesZ(){
		return NbCubesZ;
	}
	
	public boolean isPositionInWorld(Vector position){
		if (position.getXCoord() < 0 || position.getXCoord() > NbCubesX
			|| position.getYCoord() < 0 || position.getYCoord() > NbCubesY
			|| position.getZCoord() < 0 || position.getZCoord() > NbCubesZ){
			System.out.println("Not a vector inside the world");
			return false;
		}
		return true;
	}
	
	boolean isCubeInWorld(int[] cube){
		if (cube[0] < 0 || cube[0] >= NbCubesX
			|| cube[1] < 0 || cube[1] >= NbCubesY
			|| cube[2] < 0 || cube[2] >= NbCubesZ){
			return false;
		}
		return true;
	}
	
	public boolean isPassable(Vector position){
		if (isSolid(position.getIntCube())){
			System.out.println("Cube not passable");
			return false;
		}
		return true;
	}
	
	/**
	 * 0: Air
	 * 1: Rock
	 * 2: Wood
	 * 3: Workshop
	 */
	private int[][][] terrainTypes; 
	
	public boolean isSolid(int[] cube){
		int terrainType = terrainTypes[cube[0]][cube[1]][cube[2]];
		if (terrainType != 1 && terrainType != 2){
			return false;
		}
		return true;
	}
	
	private int getTerrainType(Vector cube){
		int[] cubeArray = cube.getIntCube();
		return terrainTypes[cubeArray[0]][cubeArray[1]][cubeArray[2]];
	}
	
	public int getTerrainType(int[] cube){
		return terrainTypes[cube[0]][cube[1]][cube[2]];
	}
	
	private boolean isValidTerrainType (int terrainType){
		return (terrainType >=0 && terrainType <=3);
	}
	
	public void setTerrainType(int[] cube, int terrainType){
		if (!isValidTerrainType(terrainType)){
			throw new IllegalArgumentException();		
		}
		if (terrainType != 1 && terrainType != 2 && this.isSolid(cube)){
			terrainTypes[cube[0]][cube[1]][cube[2]] = terrainType;
			modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
			double rand = Math.random();
			if (rand < 0.125) {
				new Log(cube, this);
			} else if (rand < 0.25){
				new Boulder(cube, this);
			}
			connectedToBorder.changeSolidToPassable(cube[0], cube[1], cube[2]);
			this.accessibleCubes.add(cube);
			Set<int[]> neighbours = Vector.getDirectAdjenctCubes(cube, this);
			Set<int[]> solidNeighbours = Vector.filterPassableCubes(neighbours, this);
			neighbours.removeAll(solidNeighbours);

			for (int[] neighbour : neighbours){
				this.changeAccessibilityIfNessesary(neighbour);
			}
			for (int[] solidNeighbour : solidNeighbours){
				this.collapseIfFloating(solidNeighbour);
			}
		}
		terrainTypes[cube[0]][cube[1]][cube[2]] = terrainType;
		modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
		
	}
	
	private void changeAccessibilityIfNessesary(int[] cube){
		if (!this.isSolid(cube)){
			if (Vector.hasSupportOfSolid(cube, this)){
				accessibleCubes.add(cube);
			} else {
				accessibleCubes.remove(cube);
			}
		}
	}
	
	private void collapseIfFloating(int[] cube){
		if (this.isSolid(cube)){
			if (!this.isSolidConnectedToBorder(cube)){
				this.setTerrainType(cube, 0);
			}
		}
	}
	
	public boolean isSolidConnectedToBorder(int[] cube){
		if (this.isSolid(cube)){
			return connectedToBorder.isSolidConnectedToBorder(cube[0], cube[1], cube[2]);
		}
		return true;
	}
	
	private void collapseAllFloatingCubes(){
		for (int x=0 ; x != NbCubesX ; x++){
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; z++){
					 int[] cube = {x,y,z};
					 if (!this.isSolid(cube) && Vector.hasSupportOfSolid(cube, this)){
						 accessibleCubes.add(cube);
					 }
					 this.collapseIfFloating(cube);
				}
			}
		}
	}
	
	
	
	private Set<int[]> accessibleCubes = new HashSet<>();
	
	
	
	/**
	 * materialTypes:
	 * 1: boulder
	 * 2: log
	 */
	private Set<Material> materials = new HashSet<>();
	private Set<Log> logs = new HashSet<>();
	private Set<Boulder> boulders = new HashSet<>();
	
	public boolean isWorkshopWithLogAndBoulder(int[] cube){
		if (this.getTerrainType(cube) != 3){
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
	
	public Material materialToPickUp(int[] cube){
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
	
	private List<Material> getMaterialsAt(Vector position) { 
		List<Material> foundMaterials = new ArrayList<>();
		for (Material material : materials){
			if(material.getPosition() == position){
	        	foundMaterials.add(material); 
			}
        }
	    return foundMaterials;
	}
	
	private List<Material> getMaterialsAt(int[] cube) { 
		List<Material> foundMaterials = new ArrayList<>();
		for (Material material : materials){
			if(Vector.equals(material.getPosition().getIntCube(), cube)){
	        	foundMaterials.add(material); 
			}
        }
	    return foundMaterials;
	}
	
	public Set<Log> getLogs() {
		Set<Log> logs = new HashSet<>();
		for (Material material : materials){
			if(material instanceof Log){
	        	logs.add((Log) material);
			}
        }
	    return logs;
	}
	
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
//		//if (!isValidMaterialType(materialType)){ //TODO
//		//	throw new IllegalArgumentException();		
//		//}
//		
//	}

	public void addMaterial(Material material) {
		materials.add(material);
	}
	
	public void removeMaterial(Material material) {
		materials.remove(material);
	}
	
	//private boolean isValidMaterialType (int materialType){
	//	return (materialType >=1 && materialType <=2);
	//}
	
	/*Faction*/
	
	private Set<Faction> factions = new HashSet<>();
	
	public Set<Faction> getActiveFactions() {
		return this.factions;
	}
	
	private int getNbOffFactions() {
		return this.getActiveFactions().size();
	}
	
	private void addFaction(Faction faction) {
		if (!isValidNbOfFactions(this.getNbOffFactions()+1)){
			throw new IllegalArgumentException();
		}
		System.out.println("factionadded");
		this.factions.add(faction);
	}
	
	private boolean isValidNbOfFactions(int number){
		if (number > 5){
			return false;
		}
		return true;
	}
	
	private void removeFaction(Faction faction) {
		factions.remove(faction);
	}

	private Faction getSmallestFaction() {
		Faction smallestFaction = null;
		for (Faction faction : factions){
			if (faction.getNbOffUnitsInFaction() 
	        		< smallestFaction.getNbOffUnitsInFaction())
	        	smallestFaction = faction;
		}
	    return smallestFaction;
	}
	
	/*Unit*/
	
	public Set<Unit> getUnits(){
		Set<Unit> unitsInWorld = new HashSet<>();
		for (Faction faction : factions){
			unitsInWorld.addAll(faction.getUnitsInFaction());
		}
	    return unitsInWorld;
	}
	
	private int getNbOfUnits(){
		int nbUnitsInWorld = 0;
		for (Faction faction : factions){
			nbUnitsInWorld = nbUnitsInWorld + faction.getNbOffUnitsInFaction();
		}
	    return nbUnitsInWorld;
	}
	
	public Unit spawnUnit(boolean enableDefaultBehavior){
		int[] initialCube = {10,10,10}; 
		Unit newUnit =  new Unit("Test", initialCube, enableDefaultBehavior, this);
		this.addUnit(newUnit);
		return newUnit;
	}
	
	public void addUnit(Unit unit){
		if (this.getNbOfUnits()!=100){
			unit.setWorld(this);
			if (this.getNbOffFactions() != 5){
				System.out.print("new faction");
				Faction newFaction =  new Faction(this);
				this.addFaction(newFaction);
				newFaction.addUnit(unit);
			} else {
				this.getSmallestFaction().addUnit(unit);
			}
		}		
	}

	/*Time*/
	
	public void advanceTime(double dt) {
		Set<Unit> unitsInWorld = this.getUnits();
		for (Unit unit : unitsInWorld){
		}
	}

	private void removeUnit(Unit unit) {
		unit.getFaction().removeUnit(unit);
	}
	
	/*Pathfinding*/
	
	private Set<int[]> getAccessibleCubes(){
		return this.accessibleCubes;
	}
	
	private Set<int[]> getAccessibleNeigbours (int[] cube){
		Set<int[]> neighbours = Vector.getDirectAdjenctCubes(cube, this);
		neighbours.removeAll(Vector.filterPassableCubes(neighbours, this));
		Set<int[]> accessibleNeighbours = new HashSet<>();
		for (int[] neighbour: neighbours){
			if (accessibleCubes.contains(neighbour)){
				accessibleNeighbours.add(neighbour);
			}
		}
		return accessibleNeighbours;
	}
	
	public void findPath(int[] start, int[] goal){
    // The set of nodes already evaluated.
    closedSet := {}
    // The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
    openSet := {start}
    // For each node, which node it can most efficiently be reached from.
    // If a node can be reached from many nodes, cameFrom will eventually contain the
    // most efficient previous step.
    cameFrom := the empty map

    // For each node, the cost of getting from the start node to that node.
    gScore := map with default value of Infinity
    // The cost of going from start to start is zero.
    gScore[start] := 0 
    // For each node, the total cost of getting from the start node to the goal
    // by passing by that node. That value is partly known, partly heuristic.
    fScore := map with default value of Infinity
    // For the first node, that value is completely heuristic.
    fScore[start] := heuristic_cost_estimate(start, goal)

    while openSet is not empty
        current := the node in openSet having the lowest fScore[] value
        if current = goal
            return reconstruct_path(cameFrom, goal)

        openSet.Remove(current)
        closedSet.Add(current)
        for each neighbor of current
            if neighbor in closedSet
                continue		// Ignore the neighbor which is already evaluated.
            // The distance from start to a neighbor
            tentative_gScore := gScore[current] + dist_between(current, neighbor)
            if neighbor not in openSet	// Discover a new node
                openSet.Add(neighbor)
            else if tentative_gScore >= gScore[neighbor]
                continue		// This is not a better path.

            // This path is the best until now. Record it!
            cameFrom[neighbor] := current
            gScore[neighbor] := tentative_gScore
            fScore[neighbor] := gScore[neighbor] + heuristic_cost_estimate(neighbor, goal)

    return failure
	}	
    	

function reconstruct_path(cameFrom, current)
    total_path := [current]
    while current in cameFrom.Keys:
        current := cameFrom[current]
        total_path.append(current)
    return total_path
    		
    		

	
}


