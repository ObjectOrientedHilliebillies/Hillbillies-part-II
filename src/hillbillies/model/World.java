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
		// TODO dit is nog slecht dit moet opgesplitst worden in verschillende methodes, en elke cube moet gezet worden niet enkel de solid ones!
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
			return false;
		}
		return true;
	}
	
	public boolean isCubeInWorld(int[] cube){
		if (cube[0] < 0 || cube[0] >= NbCubesX
			|| cube[1] < 0 || cube[1] >= NbCubesY
			|| cube[2] < 0 || cube[2] >= NbCubesZ){
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
	
	private boolean isSolid(int[] cube){
		int terrainType = terrainTypes[cube[0]][cube[1]][cube[2]];
		if (terrainType != 1 && terrainType != 2){
			return false;
		}
		return true;
	}
	
	public int getTerrainType(Vector cube){
		int[] cubeArray = cube.getIntCube();
		return terrainTypes[cubeArray[0]][cubeArray[1]][cubeArray[2]];
	}
	
	public int getTerrainType(int[] cube){
		return terrainTypes[cube[0]][cube[1]][cube[2]];
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
			this.changeSolidToPassable(cube);
		}
		terrainTypes[cube[0]][cube[1]][cube[2]] = terrainType;
		modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
		
	}
	
	private boolean isValidTerrainType (int terrainType){
		return (terrainType >=0 && terrainType <=3);
	}
	
	private void changeSolidToPassable(int[] cube){
		connectedToBorder.changeSolidToPassable(cube[0], cube[1], cube[2]);
		Set<int[]> neighbours = Vector.getNeighbourCubes(cube, this);
		for (int[] neighbour : neighbours){
			this.collapseIfFloating(neighbour);
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
	
	public List<Material> getMaterialsAt(Vector position) { 
		List<Material> foundMaterials = new ArrayList<>();
		for (Material material : materials){
			if(material.getPosition() == position){
	        	foundMaterials.add(material); 
			}
        }
	    return foundMaterials;
	}
	
	public List<Material> getMaterialsAt(int[] cube) { 
		List<Material> foundMaterials = new ArrayList<>();
		for (Material material : materials){
			if(material.getPosition().getIntCube() == cube){
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
	
//	public void setMaterial(Vector position, Material material){
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
	
	public int getNbOffFactions() {
		return this.getActiveFactions().size();
	}
	
	private Faction makeFaction(){
		return new Faction(this);
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
	
	public void removeFaction(Faction faction) {
		factions.remove(faction);
	}

	public Faction getSmallestFaction() {
		Faction smallestFaction = null;
		for (Faction faction : factions){
			if (faction.getNbOffUnitsInFaction() 
	        		< smallestFaction.getNbOffUnitsInFaction())
				System.out.println(smallestFaction.getNbOffUnitsInFaction());
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
			unit.advanceTime(dt);
		}
	}

	public void removeUnit(Unit unit) {
		unit.getFaction().removeUnit(unit);
	}
	
	
}


