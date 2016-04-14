package homeMadeTests.partII;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;
import ogp.framework.util.Util;

public class VectorTests {
	private Facade facade;
	private static Vector vector1;
	private static Vector vector2;
	private static Vector vector3;
	private static Vector vector4;
	private static Vector vector5;
	private static Vector vector6;
	
	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vector1 = new Vector(2.5, 3.5, 4.5);
		vector2 = new Vector(2.5, 3.5, 4.5);
		vector3 = new Vector(2.2, 3.2, 4.2);
		vector4 = new Vector(2, 3, 4);
		vector5 = new Vector(3.5, 4.5, 5.5);
		vector6 = new Vector(3, 4, 5);
		
		}
	@Test
	public void equalsTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		assertEquals(true, vector1.equals(vector2));
		//assertEquals(false, vector1.equals(vector3));
		assertEquals(true, vector1.getEnclosingCube(world).equals(vector2.getEnclosingCube(world)));
	}
	
	@Test
	public void getVectorTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		double[] doub = {1,1,1};
		Vector vector = new Vector(1,1,1);
		assertTrue(Util.fuzzyGreaterThanOrEqualTo(vector.getXCoord(), doub[0]));
		assertTrue(Util.fuzzyGreaterThanOrEqualTo(vector.getYCoord(), doub[1]));
		assertTrue(Util.fuzzyGreaterThanOrEqualTo(vector.getZCoord(), doub[2]));
	}
	
	@Test
	public void getEnclosingCubeTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Vector vector = new Vector(5,5,5);
		System.out.println(vector.getEnclosingCube(world).toString());
		System.out.println(vector.getEnclosingCube(world).getPosition());
		List<Integer> position1 = new ArrayList<>();
		position1.add(5);
		position1.add(5);
		position1.add(5);
		Cube cube1 = new Cube(position1, world);
		System.out.println(cube1.getPosition());
		assertEquals(vector.getEnclosingCube(world).getClass(), cube1.getClass());
		assertEquals(vector.getEnclosingCube(world).getPosition(), cube1.getPosition());
		assertEquals(vector.getEnclosingCube(world).getWorld(), cube1.getWorld());	
		//assertEquals(true, vector.getEnclosingCube(world).equals(cube1));
	}
	
	@Test
	public void isDirectlyAdjacentCubeTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Vector.isDirectlyAdjacentCube(vector4.getEnclosingCube(world), vector6.getEnclosingCube(world));
	}
	
	@Test
	public void distanceBetweenTest() throws ModelException {
		assertEquals(Vector.distanceBetween(vector4, vector6), Math.sqrt(3), 0.01);
	}
	
	@Test
	public void heightDifferenceTest() {
		assertEquals(Vector.heightDifference(vector4, vector6), -1, 0.01);
	}
	
	@Test
	public void hasSupportOfSolidTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Vector vector1 = new Vector(5,5,5);
		Vector vector2 = new Vector(5,6,5);
		Vector vector3 = new Vector(4,5,5);
		vector1.getEnclosingCube(world).changeTerrainType(1);
		System.out.println(vector1.getEnclosingCube(world).getTerrainType());
		assertTrue(vector3.hasSupportOfSolid(world));
		assertTrue(vector2.hasSupportOfSolidUnderneath(world));
	}
	
	

}