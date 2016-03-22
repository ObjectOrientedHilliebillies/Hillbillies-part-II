package hillbillies.model;

public class Vector {
	private final static int cubesPerRib = 50;
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
	
	public Cube getCube(){
		
		return 
	}
	
	public int[] getIntCube(){
//		int[] cubeArray = new int[3];
//		cubeArray[0] = (int) this.getXCoord();
//		cubeArray[1] = (int) this.getYCoord();
//		cubeArray[2] = (int) this.getZCoord();
		return this.getCube().getIntCube();
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
	 * Check whether the given position is a position inside of the game world.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return 
	 *       | result == 
	 *       // FIXME Deze check aanvullen.
	*/
	public static boolean isPositionInsideWorld(Vector position) {
		return (isComponentInsideWorld(position.getXCoord())&&
				isComponentInsideWorld(position.getYCoord())&&
				isComponentInsideWorld(position.getZCoord()));
	}
	
	public static boolean isPositionInsideWorld(double[] position){
		for (int i=0; i != 3 ; i++){
			if (!isComponentInsideWorld(position[i])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check whether the given component is in the borders of the game world.
	 * 
	 * @param component
	 * 		  The component to check
	 * @return
	 * 		| result == (0 < component < 50)
	 */
	public static boolean isComponentInsideWorld(double component){
		if ((component < 0) || (component >= Vector.cubesPerRib))
			return false;
		return true;
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
	
	
}
