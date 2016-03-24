package hillbillies.model;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.RETURN;


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
		
		connectedToBorder = new ConnectedToBorder(NbCubesX, NbCubesY, NbCubesZ);
		modelListener = givenModelListener;
		// TODO dit is nog slecht dit moet opgesplitst worden in verschillende methodes, en elke cube moet gezet worden niet enkel de solid ones!
		for (int x=0 ; x != NbCubesX ; x++){
			System.out.println(x);
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; z++){
					if (initialTerrainTypes[x][y][z] != 1 
							&& initialTerrainTypes[x][y][z] != 2){
						connectedToBorder.changeSolidToPassable(x, y, z);
						modelListener.notifyTerrainChanged(x, y, z);
					}
				}
			}
		}
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
	
	public void changeSolidToPassable(int[] cube, int terrainType){
		connectedToBorder.changeSolidToPassable(cube[0], cube[1], cube[2]);
		modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
		Set<int[]> neighbours = Vector.getNeighbourCubes(cube, this);
		for (int[] neighbour : neighbours){
			this.collapseIfFloating(neighbour);
		}
	}
	
	private void collapseIfFloating(int[] cube){
		if (!connectedToBorder.isSolidConnectedToBorder(cube[0], cube[1], cube[2])){
			this.changeSolidToPassable(cube, 0);
		}
	}
	
	public boolean isSolidConnectedToBorder(int[] cube){
		return connectedToBorder.isSolidConnectedToBorder(cube[0], cube[1], cube[2]);
	}
	
	
	/**
	 * 0: Air
	 * 1: Rock
	 * 2: Wood
	 * 3: Workshop
	 */
	private int[][][] terrainTypes; //FIXME in de facade is dat een set, zouden we beter ook doen
	
	public int getTerrainType(Vector cube){
		int[] cubeArray = cube.getIntCube();
		return terrainTypes[cubeArray[0]][cubeArray[1]][cubeArray[2]];
	}
	
	public void setTerrainType(Vector cube, int terrainType){
		if (!isValidTerrainType(terrainType)){
			throw new IllegalArgumentException();		
		}
		int[] coord = cube.getIntCube();
		terrainTypes[coord[0]][coord[1]][coord[2]] = terrainType;
	}
	
	private boolean isValidTerrainType (int terrainType){
		return (terrainType >=0 && terrainType <=3);
	}
	
	/**
	 * materialTypes:
	 * 1: boulder
	 * 2: log
	 */
	private Set<Material> materials;
	private Set<Log> logs;
	private Set<Boulder> boulders;
	
	
	public List<Material> getMaterialsAt(Vector position) { 
		Iterator<Material> iterator = materials.iterator();
		List<Material> foundMaterials = null;
	    while(iterator.hasNext()) {
	        Material material = iterator.next();
	        if(material.getPosition() == position) 
	        	foundMaterials.add(material); }
	    return foundMaterials;
	}
	
	public Set<Log> getLogs() {
		Iterator<Material> iterator = materials.iterator();
		Set<Log> logs = null;
	    while(iterator.hasNext()) {
	        Material material = iterator.next();
	        if(material instanceof Log) 
	        	logs.add((Log) material); 
	        }
	    return logs;
	}
	
	public Set<Boulder> getBoulders() {
		Iterator<Material> iterator = materials.iterator();
		Set<Boulder> boulders = null;
	    while(iterator.hasNext()) {
	        Material material = iterator.next();
	        if(material instanceof Boulder) 
	        	boulders.add((Boulder) material); 
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
	
	private Set<Faction> factions;
	
	public Set<Faction> getActiveFactions() {
		return this.factions;
	}
	
	private int getNbOffFactions() {
		return this.getActiveFactions().size();
	}
	
	private Faction makeFaction(){
		return new Faction(this);
	}
	
	private void addFaction(Faction faction) {
		if (!isValidNbOfFactions(this.getNbOffFactions()+1)){
			throw new IllegalArgumentException();
		}
		factions.add(faction);
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
	        	smallestFaction = faction;
		}
	    return smallestFaction;
	}
	
	/*Unit*/
	
	public Set<Unit> getUnits(){
		Set<Unit> unitsInWorld = Collections.emptySet();
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
	
	public void addUnit(Unit unit){
		if (this.getNbOfUnits()!=100){
			if (this.getNbOffFactions() != 5){
				Faction newFaction = this.makeFaction();
				this.addFaction(newFaction);
				newFaction.addUnit(unit);
			} else {
				this.getSmallestFaction().addUnit(unit);
			}
		}		
	}
}


