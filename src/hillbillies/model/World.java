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
	
	private int[][][] terrainTypes;
	
	public int getTerrainType(Vector cube){
		int[] cubeArray = cube.getIntCube();
		return terrainTypes[cubeArray[1]][cubeArray[2]][cubeArray[3]];
	}
}
