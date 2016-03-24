package hillbillies.model;

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
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener){
		connectedToBorder = new ConnectedToBorder(
					terrainTypes.length, terrainTypes[0].length, terrainTypes[0][0].length);
		NbCubesX = terrainTypes.length;
		NbCubesY = terrainTypes[0].length;
		NbCubesZ = terrainTypes[0][0].length;
		// TODO dit is nog slecht dit moet opgesplitst worden in verschillende methodes, en elke cube moet gezet worden niet enkel de solid ones!
		for (int x=0 ; x != NbCubesX ; x++){
			for (int y=0 ; y != NbCubesY; y++){
				for  (int z=0 ; z != NbCubesZ; y++){
					if (terrainTypes[x][y][z] != 1 && terrainTypes[x][y][z] != 2){
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
	private Material[][][] materialTypes;
	
	//FIXME meerdere materialTypes op 1 positie mogelijk!
	
	public Material getMaterialType(Vector position) { 
		int[] positionArray = position.getIntCube();
		return materialTypes[positionArray[0]][positionArray[1]][positionArray[2]];				
	}
	
	public void setMaterialType(Vector position, Material materialType){
		//if (!isValidMaterialType(materialType)){ //TODO
		//	throw new IllegalArgumentException();		
		//}
		int[] coord = position.getIntCube();
		materialTypes[coord[0]][coord[1]][coord[2]] = materialType;
	}
	
	private boolean isValidMaterialType (int materialType){
		return (materialType >=1 && materialType <=2);
	}
}
