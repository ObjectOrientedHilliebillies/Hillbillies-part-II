package hillbillies.model;

import java.util.ArrayList;

public class Cube {
	private int compX;
	private int compY;
	private int compZ;
	
	public Cube(Vector vector){
		setXcoord((int) (vector.getXCoord()));
		setYcoord((int) (vector.getYCoord()));
		setZcoord((int) (vector.getZCoord()));
	}
	
	public void setCube(Vector vector){
		setXcoord((int) (vector.getXCoord()));
		setYcoord((int) (vector.getYCoord()));
		setZcoord((int) (vector.getZCoord()));
	}
	
	public ArrayList<Integer> getCubeArray() {
		ArrayList<Integer> cubeArray = new ArrayList<>();
		cubeArray.add(this.getXCoord());
		cubeArray.add(this.getYCoord());
		cubeArray.add(this.getZCoord());
		return cubeArray;
	}
	
	public int[] getIntCube() {
		int[] cubeArray = new int[3];
		cubeArray[0] = this.getXCoord();
		cubeArray[1] = this.getYCoord();
		cubeArray[2] = this.getZCoord();
		return cubeArray;
	}
	
	public void setXcoord(int coordX) {
		this.compX = coordX;
	}
	public void setYcoord(int coordY) {
		this.compY = coordY;
	}
	public void setZcoord(int coordZ) {
		this.compZ = coordZ;
	}
	
	public int getXCoord() {
		return this.compX;
	}
	public int getYCoord() {
		return this.compY;
	}
	public int getZCoord() {
		return this.compZ;
	}
}