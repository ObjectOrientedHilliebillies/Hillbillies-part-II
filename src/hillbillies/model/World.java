package hillbillies.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import jdk.nashorn.internal.ir.BreakableNode;


public class World {
	/**
	 * Initialize this new world with given terrain.
	 * 
	 * @param  terrainTypes
	 *         The terrain of this new world.
	 **/         
	private ConnectedToBorder connectedToBorder;
	public World(List<Integer>[][] initialTerrainTypes, TerrainChangeListener givenModelListener){
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
	
	boolean isCubeInWorld(List<Integer> cube){
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
	private List<Integer>[][] terrainTypes; 
	
	public boolean isSolid(List<Integer> cube){
		int terrainType = terrainTypes[cube[0]][cube[1]][cube[2]];
		if (terrainType != 1 && terrainType != 2){
			return false;
		}
		return true;
	}
	
	private int getTerrainType(Vector cube){
		List<Integer> cubeArray = cube.getIntCube();
		return terrainTypes[cubeArray[0]][cubeArray[1]][cubeArray[2]];
	}
	
	public int getTerrainType(List<Integer> cube){
		return terrainTypes[cube[0]][cube[1]][cube[2]];
	}
	
	private boolean isValidTerrainType (int terrainType){
		return (terrainType >=0 && terrainType <=3);
	}
	
	public void setTerrainType(List<Integer> cube, int terrainType){
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
			Set<List<Integer>> neighbours = Vector.getDirectAdjenctCubes(cube, this);
			Set<List<Integer>> solidNeighbours = Vector.filterPassableCubes(neighbours, this);
			neighbours.removeAll(solidNeighbours);
			for (List<Integer> solidNeighbour : solidNeighbours){
				this.collapseIfFloating(solidNeighbour);
			}
		}
		terrainTypes[cube[0]][cube[1]][cube[2]] = terrainType;
		modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
		
	}
	
	private void collapseIfFloating(List<Integer> cube){
		if (this.isSolid(cube)){
			if (!this.isSolidConnectedToBorder(cube)){
				this.setTerrainType(cube, 0);
			}
		}
	}
	
	public boolean isSolidConnectedToBorder(List<Integer> cube){
		if (this.isSolid(cube)){
			return connectedToBorder.isSolidConnectedToBorder(cube[0], cube[1], cube[2]);
		}
		return true;
	}
	
	private void collapseAllFloatingCubes(){
		for (int x=0 ; x != NbCubesX ; x++){
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; z++){
					 List<Integer> cube = {x,y,z};
					 this.collapseIfFloating(cube);
				}
			}
		}
	}

	/**
	 * materialTypes:
	 * 1: boulder
	 * 2: log
	 */
	private Set<Material> materials = new HashSet<>();
	private Set<Log> logs = new HashSet<>();
	private Set<Boulder> boulders = new HashSet<>();
	
	public boolean isWorkshopWithLogAndBoulder(List<Integer> cube){
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
	
	public Material materialToPickUp(List<Integer> cube){
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
	
	private List<Material> getMaterialsAt(List<Integer> cube) { 
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
		List<Integer> initialCube = {10,9,12}; 
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
	
	private void removeUnit(Unit unit) {
		unit.getFaction().removeUnit(unit);
	}
	

	/*Time*/
	
	public void advanceTime(double dt) {
		Set<Unit> unitsInWorld = this.getUnits();
		for (Unit unit : unitsInWorld){
			unit.advanceTime(dt);
		}
	}

	/*Pathfinding*/
	public List<List<Integer>> getPath(List<Integer> start, List<Integer> goal){
		
    // The set of nodes already evaluated.
		Set<List<Integer>> closedSet = new HashSet<>();
		//closedSet := {}
		
    // The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
		Set<List<Integer>> openSet = new HashSet<>();
		openSet.add(start);
		//openSet := {start}
		
    // For each node, which node it can most efficiently be reached from.
    // If a node can be reached from many nodes, cameFrom will eventually contain the
    // most efficient previous step.
		HashMap<List<Integer>, List<Integer>> cameFrom=new HashMap<>();
		//cameFrom := the empty map

    // For each node, the cost of getting from the start node to that node.
		HashMap<List<Integer>, Double> gScore=new HashMap<>();
		//gScore := map with default value of Infinity

		
    // For each node, the total cost of getting from the start node to the goal
    // by passing by that node. That value is partly known, partly heuristic.
		HashMap<List<Integer>, Double> fScore=new HashMap<List<Integer>, Double>();
		
	//gScore := map with default value of Infinity
	//fScore := map with default value of Infinity	
    // The cost of going from start to start is zero.
		gScore.put(start,  new Double(0));
		//gScore[start] := 0
    		
    // For the first node, that value is completely heuristic.
		fScore.put(start, this.heuristic_cost_estimate(start, goal));
		//fScore[start] := heuristic_cost_estimate(start, goal)

	System.out.println("Variables set commancing pathfinding while");
    //while openSet is not empty
		int stop = 0;
		while (openSet.size() != 0 && stop != 10){
			stop = stop+1;
//	        current := the node in openSet having the lowest fScore[] value
			List<Integer> currentNode = new ArrayList<>();
			Double lowestF = null;
			for (List<Integer> node : openSet){
				if (lowestF == null){
					currentNode = node;
					lowestF = fScore.get(node);
				} else if (lowestF > fScore.get(node)){
					currentNode = node;
					lowestF = fScore.get(node);
				}
			}
//	        if current = goal
//	            return reconstruct_path(cameFrom, goal)
			if (currentNode.equals(goal)){
				return this.reconstruct_path(cameFrom, goal);
			}
//	        openSet.Remove(current)
			openSet.remove(currentNode);
//	        closedSet.Add(current)
			closedSet.add(currentNode);
//	        for each neighbor of current
			Set<List<Integer>> accessibleNeigbours = this.getAccessibleNeigbours(currentNode);
			for (List<Integer> neighbour : accessibleNeigbours){
//	            if neighbor in closedSet
				if (closedSet.contains(neighbour)){
					System.out.println("neigbour skiped = " + Arrays.toString(neighbour));
	                continue;		// Ignore the neighbor which is already evaluated.
				}
	            // The distance from start to a neighbor
//	            tentative_gScore := gScore[current] + dist_between(current, neighbor)
				double tentative_gScore = gScore.get(currentNode) + Vector.distanceBetween(currentNode, neighbour);
//	            if neighbor not in openSet	// Discover a new node
				if (!openSet.contains(neighbour)){
//	                openSet.Add(neighbor)
					openSet.add(neighbour);
// 					else if tentative_gScore >= gScore[neighbor]
					
				}else if (tentative_gScore >= gScore.get(neighbour)){  
	                continue;		// This is not a better path.
				}
	            // This path is the best until now. Record it!
//	            cameFrom[neighbor] := current
				cameFrom.put(neighbour, currentNode);
//	            gScore[neighbor] := tentative_gScore
				gScore.put(neighbour, tentative_gScore);
//	            fScore[neighbor] := gScore[neighbor] + heuristic_cost_estimate(neighbor, goal)
				fScore.put(neighbour, tentative_gScore+this.heuristic_cost_estimate(neighbour, goal));
				this.setTerrainType(neighbour, 3);
			}
		}
		return null;
	}	
	
	private double heuristic_cost_estimate(List<Integer> start, List<Integer> goal){
		return Vector.distanceBetween(Vector.getCentreOfCube(start), Vector.getCentreOfCube(goal));
	}
	
	private List<List<Integer>> reconstruct_path(HashMap<List<Integer>, List<Integer>> cameFrom, List<Integer> current){
		List<List<Integer>> total_path = new ArrayList<>();
	    total_path.add(current);
	    while (cameFrom.containsKey(current)){
	    	current = cameFrom.get(current);
	    	total_path.add(current);
	    }
	    return total_path;
	}

	private Set<List<Integer>> getAccessibleNeigbours (List<Integer> cube){
		Set<List<Integer>> neighbours = Vector.getNeighbourCubes(cube, this);
		neighbours.removeAll(Vector.filterPassableCubes(neighbours, this));
		Set<List<Integer>> accessibleNeighbours = new HashSet<>();
		for (List<Integer> neighbour: neighbours){
			if (Vector.hasSupportOfSolid(neighbour, this)){
				accessibleNeighbours.add(neighbour);
			}
		}
		return accessibleNeighbours;
	}
	
	private List<Integer> asList(List<Integer> array){
		return Arrays.asList(array[0], array[1], array[2]);
	}
}


