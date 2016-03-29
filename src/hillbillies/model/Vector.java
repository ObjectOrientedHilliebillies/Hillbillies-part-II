package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.experimental.theories.Theories;

import jdk.internal.dynalink.beans.StaticClass;
import sun.net.www.content.audio.wav;

public class Vector {
	private double compX;
	private double compY;
	private double compZ;

	public Vector(double coordX, double coordY, double coordZ){
		setXcoord(coordX); //TODO Is hetzelfde als setVector
		setYcoord(coordY);
		setZcoord(coordZ);
	}
	
	private Vector(double[] position){
		this.setXcoord(position[0]);
		this.setYcoord(position[1]);
		this.setZcoord(position[2]);
	}
	
	private Vector(List<Integer> cube){
		this.setXcoord(cube.get(0));
		this.setYcoord(cube.get(1));
		this.setZcoord(cube.get(2));
	}
	
	
	private void setVector(double coordX, double coordY, double coordZ) {
		setXcoord(coordX);
		setYcoord(coordY);
		setZcoord(coordZ);
	}
	
	private void setVector(double[] position){
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
	
	public List<Integer> getIntCube(){
		List<Integer> cubeArray = new ArrayList<>();
		cubeArray.add((int) this.getXCoord());
		cubeArray.add((int) this.getYCoord());
		cubeArray.add((int) this.getZCoord());
		return cubeArray;
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
	 * Check whether the given cube is a neighbour cube of this cube
	 *  
	 * @param  otherCube
	 *         The Cube to check.
	 * @return 
	 *       | result == //FIXME
	*/
	public boolean isNeighbourCube(List<Integer> otherCube){
		List<Integer> thisCube = this.getIntCube();
		boolean neighbourForAtleastOneComponent = false;
		for (int i = 0; i != 3; i++) {
			int difference = Math.abs(thisCube.get(i) - otherCube.get(i));
		    if (difference == 1)
		    	neighbourForAtleastOneComponent = true;
		    else if (difference != 0)
		    	return false;
		}
		return neighbourForAtleastOneComponent;
	}
	
	private final int[][] directAdjacentOffsets = new int[][] { { -1, 0, 0 }, { +1, 0, 0 }, 
		{ 0, -1, 0 }, { 0, +1, 0 },{ 0, 0, -1 }, { 0, 0, +1 } };
		
		List<int[]> shuffledOffsets = new ArrayList<>(Arrays.asList(directAdjacentOffsets));
		
	private Set<List<Integer>> getDirectAdjenctCubes(List<Integer> coord) {
		List<int[]> shuffledOffsets = new ArrayList<>(Arrays.asList(directAdjacentOffsets));
		Collections.shuffle(shuffledOffsets);
		return shuffledOffsets.stream().map(
				offset -> Arrays.asList(coord.get(0) + offset[0], 
						coord.get(1) + offset[1], coord.get(2) + offset[2])).collect(Collectors.toList()));
	}
	
	public static Set<List<Integer>> getDirectAdjenctCubes(List<Integer> thisCube, World world){
		Set<List<Integer>> directAdjectCubes = new HashSet<>();
		for (List<Integer> offset : directAdjacentOffsets){
			List<Integer> directAdject = Vector.sum(thisCube, offset);
			if (world.isCubeInWorld(directAdject)){
				directAdjectCubes.add(directAdject);
			}
		}
		return directAdjectCubes;
	}
		
	public static Set<List<Integer>> filterPassableCubes(Set<List<Integer>> unfilterdCubes, World world){
		Set<List<Integer>> remainingCubes = new HashSet<>();
		for (List<Integer> cube : unfilterdCubes){
			if (world.isSolid(cube)){
				remainingCubes.add(cube);
			}
		}
		return remainingCubes;
	}
	
	public static boolean isDirectlyAdjacentCube(List<Integer> thisCube, List<Integer> otherCube){
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
	
	public static Set<List<Integer>> getNeighbourCubes(List<Integer> thisCube, World world){
		Set<List<Integer>> neighbourCubes = new HashSet<List<Integer>>();
		for (int x=-1; x!=2; x++){
			for (int y=-1; y!=2; y++){
				for (int z=-1; z!=2; z++){
					List<Integer> offset = {x,y,z};
					List<Integer> neighbourCube = Vector.sum(thisCube, offset);
					if (world.isCubeInWorld(neighbourCube)){
						neighbourCubes.add(Vector.sum(thisCube, offset));
					}
				}
			}
		}
		neighbourCubes.remove(thisCube);
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
	private static boolean equals(Vector vector1, Vector vector2){
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
	public static boolean equals(List<Integer> thisCube, List<Integer> otherCube){
//		List<Integer> thisCube = this.getIntCube();
		if (thisCube.get(0) == otherCube.get(0)
				&& thisCube.get(1) == otherCube.get(1)
				&& thisCube.get(2) == otherCube.get(2)){
			return true;
		}
		return false;
	}
	
	public static double distanceBetween(List<Integer> cube1, List<Integer> cube2){
		return Math.sqrt(Math.pow(cube1.get(0)-cube2.get(0),2) 
				+Math.pow(cube1.get(1)-cube2.get(1),2)
				+Math.pow(cube1.get(2)-cube2.get(2),2));
	}
	
	public static double distanceBetween(Vector vector1, Vector vector2){
		return getVectorFromTo(vector1, vector2).lenght();
	}
	
	public static double heightDifference(Vector it, Vector comparedTo){
		return (it.getYCoord() - comparedTo.getYCoord());
	}
	
	public static Vector getCentreOfCube(List<Integer> cube){
		return new Vector(cube.get(0)+0.5, cube.get(1)+0.5, cube.get(2)+0.5);
	}
	
	public static Vector sum(Vector vector1, Vector vector2){
		return new Vector(vector1.compX + vector2.compX, 
				vector1.compY + vector2.compY,
				vector1.compZ + vector2.compZ);
	}
	
	private static List<Integer> sum(List<Integer> thisCube, List<Integer> otherCube){
		List<Integer> result = {thisCube.get(0) + otherCube.get(0), 
						thisCube.get(1) + otherCube.get(1),
						thisCube.get(2) + otherCube.get(2)};
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
	
	public static Vector getOneCubeCloserToCube(Vector currentPosition, List<Integer> target){
		List<Integer> currentCube = currentPosition.getIntCube();
		List<Integer> difference = new int[3];
		for (int i=0; i != 3; i++){
			if (currentCube[i] == target[i])
				difference[i] = 0;
			else if (currentCube[i] < target[i])
				difference[i] = 1;
			else {
				difference[i] = -1;
			}	
		}
		return new Vector(difference.get(0), difference.get(1), difference.get(2));
	}
	
	public List<Integer> getRandomAdjacentCubeInWorld(World world){
		List<Integer> thisCube = this.getIntCube();
		List<Integer> newCube = new int[3];		
		do {
		for (int i=0; i != 3; i++){
				newCube[i] = thisCube[i] + (int) (Math.random() * 3) - 1;
			}
		} while (Vector.equals(thisCube, newCube) || !world.isCubeInWorld(newCube));
		return newCube;
	}
	
	public boolean hasSupportOfSolid(World world){
		Set<List<Integer>> directAdjenctCubes = this.getDirectAdjenctCubes(world) ;
		if (directAdjenctCubes.size() == 6){
			if (Vector.filterPassableCubes(directAdjenctCubes, world).size() == 0){
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasSupportOfSolid(List<Integer> cube, World world){
		Set<List<Integer>> directAdjenctCubes = Vector.getDirectAdjenctCubes(cube, world) ;
		if (directAdjenctCubes.size() == 6){
			if (Vector.filterPassableCubes(directAdjenctCubes, world).size() == 0){
				return false;
			}
		}
		return true;
	}
	
	public boolean hasSupportOfSolidUnderneath(World world){
		List<Integer> thisCube = this.getIntCube();
		List<Integer> cubeBenath = {thisCube.get(0), thisCube.get(1), thisCube.get(2)-1};
		return (!world.isCubeInWorld(cubeBenath) || world.isSolid(cubeBenath));
	}
	
	
	
}
