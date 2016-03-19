package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

public class World {
	private final int cubesPerRib = 50;
	
	private ConnectedToBorder connectedToBorder;
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener){
		connectedToBorder = new ConnectedToBorder(
					terrainTypes.length, terrainTypes[0].length, terrainTypes[0][0].length);
		for (int x=0 ; x != terrainTypes.length ; x++){
			for (int y=0 ; y != terrainTypes[0].length; y++){
				for  (int z=0 ; z != terrainTypes[0][0].length; y++){
					if (terrainTypes[x][y][z] != 1 && terrainTypes[x][y][z] != 2){
						connectedToBorder.changeSolidToPassable(x, y, z);
						modelListener.notifyTerrainChanged(x, y, z);
					}
				}
			}
		}
		
	}
	
	
	public boolean inBorders(Vector vector){
		return (vector.getXCoord() <= this.cubesPerRib && vector.getXCoord() >= 0 &&
				vector.getYCoord() <= this.cubesPerRib && vector.getYCoord() >= 0 &&
				vector.getZCoord() <= this.cubesPerRib && vector.getZCoord() >= 0);
	}
}
