package hillbillies.model;

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
	private boolean isNeighbourCube(Vector otherCube){
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(this.getIntCube()[i] - otherCube.getIntCube()[i]);
		    if (difference == 1)
		    	neighbourForAtleastOneComponent = true;
		    else if (difference != 0)
		    	return false;
		}
		return neighbourForAtleastOneComponent;
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
	public static boolean equals(int[] cube1, int[] cube2){
		if (cube1[1] == cube2[1]
				&& cube1[2] == cube2[2]
				&& cube1[3] == cube2[3]){
			return true;
		}
		return false;
	}
	
	public static double distanceBetween(Vector vector1, Vector vector2){
		return lenght(getVectorFromTo(vector1, vector2));
	}
	
	public static double heightDifference(Vector it, Vector comparedTo){
		return (it.getYCoord() - comparedTo.getYCoord());
	}
	
	public static Vector getCentreOfCube(int[] cube){
		return new Vector(cube[0]+0.5, cube[1]+0.5, cube[2]+0.5);
	}
	
	public static Vector addVectors(Vector vector1, Vector vector2){
		return new Vector(vector1.compX + vector2.compX, 
				vector1.compY + vector2.compY,
				vector1.compZ + vector2.compZ);
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
	
	public static double orientationInXZPlane(Vector vector){
		return Math.atan2(vector.getYCoord(), vector.getXCoord());
	}
	
	public static double lenght(Vector vector){
		return Math.sqrt(Math.pow(vector.getXCoord(),2) 
				+Math.pow(vector.getYCoord(),2)
				+Math.pow(vector.getZCoord(),2));
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
	}
	
}
