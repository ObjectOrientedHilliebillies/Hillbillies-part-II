package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.antlr.v4.parse.ANTLRParser.ruleReturns_return;

import ogp.framework.util.Util;

/**
 * https://github.com/ObjectOrientedHilliebillies/Hillbillies-part-II.git
 * @version 1.0
 * @author Victor Van Eetvelt en Jonas Vantrappen
 *
 */
public class Vector {
	private double compX;
	private double compY;
	private double compZ;
	
	/**
	 * @param coordX
	 * 		The X coordinate for this new vector
	 * @param coordY
	 * 		The Y coordinate for this new vector
	 * @param coordZ
	 * 		The Z coordinate for this new vector
	 */
	public Vector(double coordX, double coordY, double coordZ){
		setXcoord(coordX);
		setYcoord(coordY);
		setZcoord(coordZ);
		
	}
	
	/**
	 * Returns a double[] corresponding with this vector
	 */
	public double[] getVector() {
		double[] vectorArray = new double[3];
		vectorArray[0] = this.getXCoord();
		vectorArray[1] = this.getYCoord();
		vectorArray[2] = this.getZCoord();
		return vectorArray;
	}
	
	/**
	 * Set the X coordinate of this vector to the given X coordinate
	 */
	private void setXcoord(double coordX) {
		this.compX = coordX;
	}
	
	/**
	 * Set the Y coordinate of this vector to the given Y coordinate
	 */
	private void setYcoord(double coordY) {
		this.compY = coordY;
	}
	
	/**
	 * Set the Z coordinate of this vector to the given Z coordinate
	 */
	private void setZcoord(double coordZ) {
		this.compZ = coordZ;
	}
	
	/**
	 * Return the X coordinate of this vector
	 */
	public double getXCoord() {
		return this.compX;
	}
	
	/**
	 * Return the Y coordinate of this vector
	 */
	public double getYCoord() {
		return this.compY;
	}
	
	/**
	 * Return the Z coordinate of this vector
	 */
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
		return world.getCube((int) getXCoord(), (int) getYCoord(), (int) getZCoord());
	}
	
	/**
	 * Return whether the cube thisCube is directly adjacent to 
	 * the cube otherCube
	 * 
	 * @param thisCube
	 * @param otherCube
	 * @return
	 */
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
	
	/**
	 * Returns the distance between the cube cube1 and the cube
	 *  cube2
	 */
	public static double distanceBetween(Cube cube1, Cube cube2){
		return Math.sqrt(Math.pow(cube1.getPosition().get(0)-cube2.getPosition().get(0),2) 
				+Math.pow(cube1.getPosition().get(1)-cube2.getPosition().get(1),2)
				+Math.pow(cube1.getPosition().get(2)-cube2.getPosition().get(2),2));
	}
	
	/**
	 * Returns the distance between the vector vector1 and the vector
	 *  vector2
	 */
	public static double distanceBetween(Vector vector1, Vector vector2){
		return getVectorFromTo(vector1, vector2).lenght();
	}
	
	/**
	 * Returns the height difference between the vector it and the
	 *  vector comparedTo
	 */
	public static double heightDifference(Vector it, Vector comparedTo){
		return (it.getYCoord() - comparedTo.getYCoord());
	}
	
	/**
	 * Returns a vector who is the sum of the vector vector1 and
	 *  the vector vector2
	 */
	public static Vector sum(Vector vector1, Vector vector2){
		return new Vector(vector1.compX + vector2.compX, 
				vector1.compY + vector2.compY,
				vector1.compZ + vector2.compZ);
	}
	
	/**
	 * 
	 * @param thisCube
	 * @param otherCube
	 * @return
	 */
	public static Integer[] sum(Integer[] thisCube, Integer[] otherCube){
		Integer[] result = {thisCube[0] + otherCube[0], 
						thisCube[1] + otherCube[1],
						thisCube[2] + otherCube[2]};
		return result;
	}
	
	/**
	 * Returns a vector starting from the vector from to the vector to
	 */
	public static Vector getVectorFromTo(Vector from, Vector to){
		return new Vector(to.compX - from.compX, 
				to.compY - from.compY,
				to.compZ - from.compZ);
	}
	
	/**
	 * Returns a vector who is this vector scaled with the scalar
	 */
	public Vector scale(double scalar){
		return new Vector(this.compX * scalar,
				this.compY * scalar,
				this.compZ * scalar);
	}
	
	/**
	 * Returns the orientation of this vector.
	 */
	public double orientationInXZPlane(){
		return Math.atan2(this.getYCoord(), this.getXCoord());
	}
	
	/**
	 * Returns the length of this vector.
	 */
	public double lenght(){
		return Math.sqrt(Math.pow(this.getXCoord(),2) 
				+Math.pow(this.getYCoord(),2)
				+Math.pow(this.getZCoord(),2));
	}
	
	/**
	 * Returns whether this vector is in a cube that has a direct
	 *  adjacent cube who is solid.
	 *  
	 * @param world
	 * 		The world this vector is in.
	 */
	public boolean hasSupportOfSolid(World world){
		Set<Cube> directAdjenctCubes = this.getEnclosingCube(world).getDirectAdjenctCubes() ;
		if (directAdjenctCubes.size() != 6){
			return true;
		}
		if (World.filterPassableCubes(directAdjenctCubes).size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns whether the cube this vector is part of has a solid cube
	 * 	beneath it. 
	 */
	public boolean hasSupportOfSolidUnderneath(World world){
		Cube cubeBenath = world.getCube((int) getXCoord(), 
				(int) getYCoord(), (int) getZCoord()-1);
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
