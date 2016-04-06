package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ogp.framework.util.Util;

public class Vector {
	private double compX;
	private double compY;
	private double compZ;

	public Vector(double coordX, double coordY, double coordZ){
		setXcoord(coordX);
		setYcoord(coordY);
		setZcoord(coordZ);
		
	}
	
	public double[] getVector() {
		double[] vectorArray = new double[3];
		vectorArray[0] = this.getXCoord();
		vectorArray[1] = this.getYCoord();
		vectorArray[2] = this.getZCoord();
		return vectorArray;
	}
	
	private void setXcoord(double coordX) {
		this.compX = coordX;
	}
	
	private void setYcoord(double coordY) {
		this.compY = coordY;
	}
	
	private void setZcoord(double coordZ) {
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
	 * Return the cube that is enclosing the given vector.
	 * @param vector
	 * 		The vector of wish the enclosing cube will be returned.
	 * @return The cube enclosing the given vector.
	 */
	public Cube getEnclosingCube(World world){
		List<Integer> position = new ArrayList<>();
		position.add((int) getXCoord());
		position.add((int) getYCoord());
		position.add((int) getZCoord());		
		return world.getCube(position);
	}
	
	public static boolean isDirectlyAdjacentCube(Cube thisCube, Cube otherCube){
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(thisCube.getPosition().get(i) - otherCube.getPosition().get(i));
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
	
	/** 
	 * Check whether this vector is equal to the given object.
	 * @return True if and only if the given object is effective,
	 * 		   if this vector and the given object belong to the same class,
	 * 		   and if this vector and the given vector have the same coordinates.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			
			return false;
		if (other.getClass() != this.getClass())
			return false;
		Vector otherVector = (Vector)other; 
		return (Util.fuzzyGreaterThanOrEqualTo(getXCoord(),otherVector.getXCoord())
				&& Util.fuzzyGreaterThanOrEqualTo(getYCoord(),otherVector.getYCoord()) 
				&& Util.fuzzyGreaterThanOrEqualTo(getZCoord(),otherVector.getZCoord()) );
	}
	public static double distanceBetween(Cube cube1, Cube cube2){
		return Math.sqrt(Math.pow(cube1.getPosition().get(0)-cube2.getPosition().get(0),2) 
				+Math.pow(cube1.getPosition().get(1)-cube2.getPosition().get(1),2)
				+Math.pow(cube1.getPosition().get(2)-cube2.getPosition().get(2),2));
	}
	
	public static double distanceBetween(Vector vector1, Vector vector2){
		return getVectorFromTo(vector1, vector2).lenght();
	}
	
	public static double heightDifference(Vector it, Vector comparedTo){
		return (it.getYCoord() - comparedTo.getYCoord());
	}
	
	public static Vector sum(Vector vector1, Vector vector2){
		return new Vector(vector1.compX + vector2.compX, 
				vector1.compY + vector2.compY,
				vector1.compZ + vector2.compZ);
	}
	
	public static Integer[] sum(Integer[] thisCube, Integer[] otherCube){
		Integer[] result = {thisCube[0] + otherCube[0], 
						thisCube[1] + otherCube[1],
						thisCube[2] + otherCube[2]};
		return result;
	}
	
	public static Vector getVectorFromTo(Vector from, Vector to){
		return new Vector(to.compX - from.compX, 
				to.compY - from.compY,
				to.compZ - from.compZ);
	}
	
	public Vector scale(double scalar){
		return new Vector(this.compX * scalar,
				this.compY * scalar,
				this.compZ * scalar);
	}
	
	public double orientationInXZPlane(){
		return Math.atan2(this.getYCoord(), this.getXCoord());
	}
	
	private double lenght(){
		return Math.sqrt(Math.pow(this.getXCoord(),2) 
				+Math.pow(this.getYCoord(),2)
				+Math.pow(this.getZCoord(),2));
	}
	
	public boolean hasSupportOfSolid(World world){
		Set<Cube> directAdjenctCubes = this.getEnclosingCube(world).getDirectAdjenctCubes() ;
		if (directAdjenctCubes.size() == 6){
			if (World.filterPassableCubes(directAdjenctCubes).size() == 0){
				return false;
			}
		}
		return true;
	}
	
//	public static boolean hasSupportOfSolid(Cube cube, World world){
//		Set<Cube> directAdjenctCubes = Vector.getDirectAdjenctCubes(cube, world) ;
//		if (directAdjenctCubes.size() == 6){
//			if (Vector.filterPassableCubes(directAdjenctCubes, world).size() == 0){
//				return false;
//			}
//		}
//		return true;
//	}
	
	public boolean hasSupportOfSolidUnderneath(World world){
		Cube thisCube = this.getEnclosingCube(world);
		List<Integer> makeCube = new ArrayList<>();
		makeCube.add(thisCube.getPosition().get(0));
		makeCube.add(thisCube.getPosition().get(1));
		makeCube.add(thisCube.getPosition().get(2)-1);
		Cube cubeBenath = world.getCube(makeCube);
		return (cubeBenath == null || cubeBenath.isSolid());
	}	
	
	/**
	 * Return a textual representation of this vector.
	 * 
	 * @return A string consisting of a textual representation of the position
	 * 			enclosed in square brackets.
	 */
	@Override
	public String toString(){
		return "["+getXCoord()+", "
				  +getYCoord()+", "
				  +getZCoord()+"]";
	}
	
}
