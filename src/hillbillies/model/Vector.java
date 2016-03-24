package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

public class Vector {
	private double compX;
	private double compY;
	private double compZ;

	public Vector(double coordX, double coordY, double coordZ){
		setXcoord(coordX);
		setYcoord(coordY);
		setZcoord(coordZ);
	}
	
	public Vector(double[] position){
		this.setXcoord(position[0]);
		this.setYcoord(position[1]);
		this.setZcoord(position[2]);
	}
	
	public Vector(int[] cube){
		this.setXcoord(cube[0]);
		this.setYcoord(cube[1]);
		this.setZcoord(cube[2]);
	}
	
	
	public void setVector(double coordX, double coordY, double coordZ) {
		setXcoord(coordX);
		setYcoord(coordY);
		setZcoord(coordZ);
	}
	
	public void setVector(double[] position){
		this.setXcoord(position[0]);
		this.setYcoord(position[1]);
		this.setZcoord(position[2]);
	}
	
	public double[] getVector() {
		double[] vectorArray = new double[3];
		vectorArray[0] = this.getXCoord();
		vectorArray[1] = this.getYCoord();
		vectorArray[2] = this.getZCoord();
		return vectorArray;
	}
	
	public int[] getIntCube(){
		int[] cubeArray = new int[3];
		cubeArray[0] = (int) this.getXCoord();
		cubeArray[1] = (int) this.getYCoord();
		cubeArray[2] = (int) this.getZCoord();
		return cubeArray;
	}
	
	public void setXcoord(double coordX) {
		this.compX = coordX;
	}
	
	public void setYcoord(double coordY) {
		this.compY = coordY;
	}
	
	public void setZcoord(double coordZ) {
		this.compZ = coordZ;
	}
	
	public double getXCoord() {
		return this.compX;
	}
	
	public double getYCoord() {
		return this.compY;
	}
	
	public double getZCoord() {
		return this.compZ;
	}
	
	/**
	 * Check whether the given cube is a neighbour cube of this cube
	 *  
	 * @param  otherCube
	 *         The Cube to check.
	 * @return 
	 *       | result == //FIXME
	*/
	public boolean isNeighbourCube(int[] otherCube){
		int[] thisCube = this.getIntCube();
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(thisCube[i] - otherCube[i]);
		    if (difference == 1)
		    	neighbourForAtleastOneComponent = true;
		    else if (difference != 0)
		    	return false;
		}
		return neighbourForAtleastOneComponent;
	}
	
	public  boolean isDirectlyAdjacentCube(int[] otherCube){
		int[] thisCube = this.getIntCube();
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(thisCube[i] - otherCube[i]);
		    if (difference == 1){
		    	if (neighbourForAtleastOneComponent){
		    		return false;
		    	}
		    	neighbourForAtleastOneComponent = true;
		    }
		    else if (difference != 0)
		    	return false;
		}
		return neighbourForAtleastOneComponent;
	}
	
	public static Set<int[]> getNeighbourCubes(int[] thisCube){
		Set<int[]> neighbourCubes = new HashSet<int[]>();
		for (int x=-1; x!=2; x++){
			for (int y=-1; y!=2; y++){
				for (int z=-1; z!=2; z++){
					int[] offset = {x,y,z};
					int[] neighbourCube = Vector.sum(thisCube, offset);
					if ()
					neighbourCubes.add(Vector.sum(thisCube, offset));
				}
			}
		}
		return neighbourCubes;
	}
	
	/**
	 * Check whether the given vectors are the same vector.
	 *  
	 * @param  vector1, vector2  //FIXME moet dat met comma tussen of moet je hier een nieuwe param opstellen?
	 *         The vectors to compare.
	 * @return 
	 *       | result == (vector1 = vector1)
	*/
	public static boolean equals(Vector vector1, Vector vector2){
		if (vector1.getXCoord() == vector2.getXCoord()
				&& vector1.getYCoord() == vector2.getYCoord()
				&& vector1.getZCoord() == vector2.getZCoord()){
			return true;
		}
		return false;
	}
	
	/**
	 * Check whether the given cubes are the same cubes.
	 *  
	 * @param  cube1, cube2  //FIXME moet dat met comma tussen of moet je hier een nieuwe param opstellen?
	 *         The cubes to compare.
	 * @return 
	 *       | result == (cube1 = cube2)
	*/
	public static boolean equals(int[] thisCube, int[] otherCube){
//		int[] thisCube = this.getIntCube();
		if (thisCube[1] == otherCube[1]
				&& thisCube[2] == otherCube[2]
				&& thisCube[3] == otherCube[3]){
			return true;
		}
		return false;
	}
	
	public static double distanceBetween(Vector vector1, Vector vector2){
		return getVectorFromTo(vector1, vector2).lenght();
	}
	
	public static double heightDifference(Vector it, Vector comparedTo){
		return (it.getYCoord() - comparedTo.getYCoord());
	}
	
	public static Vector getCentreOfCube(int[] cube){
		return new Vector(cube[0]+0.5, cube[1]+0.5, cube[2]+0.5);
	}
	
	public Vector sum(Vector otherVector){
		return new Vector(this.compX + otherVector.compX, 
				this.compY + otherVector.compY,
				this.compZ + otherVector.compZ);
	}
	
	public static int[] sum(int[] thisCube, int[] otherCube){
		int[] result = {thisCube[1] + otherCube[1], 
						thisCube[2] + otherCube[2],
						thisCube[3] + otherCube[3]};
		return result;
	}
	
	public static Vector getVectorFromTo(Vector from, Vector to){
		return new Vector(to.compX - from.compX, 
				to.compY - from.compY,
				to.compZ - from.compZ);
	}
	
	public static Vector multiply(Vector vector, double scalar){
		return new Vector(vector.compX * scalar,
				vector.compY * scalar,
				vector.compZ * scalar);
	}
	
	public double orientationInXZPlane(){
		return Math.atan2(this.getYCoord(), this.getXCoord());
	}
	
	public double lenght(){
		return Math.sqrt(Math.pow(this.getXCoord(),2) 
				+Math.pow(this.getYCoord(),2)
				+Math.pow(this.getZCoord(),2));
	}
	
	public static Vector getOneCubeCloserToCube(Vector currentPosition, int[] target){
		int[] currentCube = currentPosition.getIntCube();
		int[] difference = new int[3];
		for (int i=0; i != 3; i++){
			if (currentCube[i] == target[i])
				difference[i] = 0;
			else if (currentCube[i] < target[i])
				difference[i] = 1;
			else {
				difference[i] = -1;
			}	
		}
		return new Vector(difference[0], difference[1], difference[2]);
	}
	
	public Vector getRandomAdjacentDodge(World world){
		int[] thisCube = this.getIntCube();
		int[] newCube = new int[3];		
		do {
		for (int i=0; i != 3; i++){
				newCube[i] = thisCube[i] + (int) (Math.random() * 3) - 1;
			}
		} while (equals(thisCube, newCube) || !world.isCubeInWorld(newCube));
		return new Vector(newCube);
	}
	
}
