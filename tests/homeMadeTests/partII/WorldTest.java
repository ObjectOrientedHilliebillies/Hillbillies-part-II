package homeMadeTests.partII;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class WorldTest {
	
	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}

	int[] position = {5,5,5};
	
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;

	@Test
	public void getNbCubesTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		assertEquals(world.getNbCubesX(), 10);
		assertEquals(world.getNbCubesY(), 10);
		assertEquals(world.getNbCubesZ(), 10);
	}
	
//	@Test
//	public void isCubeInsideWorldTest() throws ModelException {
//		int[] position2 = {10,10,10};
//		int[] position3 = {15,15,15};
//		List<Integer> position1 = new ArrayList<>();
//		position1.add(5);
//		position1.add(5);
//		position1.add(5);
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		assertEquals(world.isCubeInWorld(position1), true);
//		assertEquals(world.isCubeInWorld(position2), true); //FIXME
//		assertEquals(world.isCubeInWorld(position3), false);
//	}
	
	@Test
	public void isPositionInsideWorldTest() throws ModelException {
		Vector position1 = new Vector(5,5,5);
		Vector position2 = new Vector(10,10,10);
		Vector position3 = new Vector(15,15,15);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		assertEquals(world.isPositionInWorld(position1), true);
		assertEquals(world.isPositionInWorld(position2), true);
		assertEquals(world.isPositionInWorld(position3), false);
	}
	
	@Test
	public void isSolidOrPassableTest() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[2][2][2] = TYPE_AIR;
		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		//int[] position1 = {1,1,0};
		//int[] position2 = {1,1,1};
		//int[] position3 = {2,2,2};
		List<Integer> position1 = new ArrayList<>();
		position1.add(1);
		position1.add(1);
		position1.add(0);
		Cube cube1 = new Cube(position1, world);
		List<Integer> position2 = new ArrayList<>();
		position2.add(1);
		position2.add(1);
		position2.add(1);
		Cube cube2 = new Cube(position2, world);
		List<Integer> position3 = new ArrayList<>();
		position3.add(2);
		position3.add(2);
		position3.add(2);
		Cube cube3 = new Cube(position3, world);
		Vector position4 = new Vector(1,1,0);
		Vector position5 = new Vector(1,1,1);
		Vector position6 = new Vector(2,2,2);
		//assertEquals(cube1.isSolid(), true);
		//assertEquals(cube2.isSolid(), true);
		//assertEquals(cube3.isSolid(), false);
		assertEquals(position4.getEnclosingCube(world).isPassable(), false);
		assertEquals(position5.getEnclosingCube(world).isPassable(), false);
		assertEquals(position6.getEnclosingCube(world).isPassable(), true);
	}
	
	@Test
	public void terrainTypeTest() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[2][2][2] = TYPE_AIR;
		//int[] position1 = {1,1,0};
		//int[] position2 = {1,1,1};
		//int[] position3 = {2,2,2};
		Vector position1 = new Vector(1,1,0);
		Vector position2 = new Vector(1,1,1);
		Vector position3 = new Vector(2,2,2);
		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		assertEquals(position1.getEnclosingCube(world).getTerrainType(), TYPE_ROCK);
		assertEquals(position2.getEnclosingCube(world).getTerrainType(), TYPE_TREE);
		assertEquals(position3.getEnclosingCube(world).getTerrainType(), TYPE_AIR);
	}
	
	@Test
	public void setTerrainTypeTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		Vector position2 = new Vector(1,1,1);
		Vector position3 = new Vector(2,2,2);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		position1.getEnclosingCube(world).changeTerrainType(TYPE_ROCK);
		position2.getEnclosingCube(world).changeTerrainType(TYPE_TREE);
		position3.getEnclosingCube(world).changeTerrainType(TYPE_AIR);
		assertEquals(position1.getEnclosingCube(world).getTerrainType(), TYPE_ROCK);
		assertEquals(position2.getEnclosingCube(world).getTerrainType(), TYPE_TREE);
		assertEquals(position3.getEnclosingCube(world).getTerrainType(), TYPE_AIR);
	}
	
	@Test
	public void isSolidConnectedToBorderTest() throws ModelException {
		//already in Part2Test
	}
	
	@Test
	public void getMaterialTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		Vector position2 = new Vector(1,1,1);
		Vector position3 = new Vector(12,12,12);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Boulder boulder1 = new Boulder(position1, world);
		Boulder boulder2 = new Boulder(position1, world);
		Log log = new Log(position2, world);
		assertEquals(world.getBoulders().contains(boulder1), true);
		assertEquals(world.getBoulders().contains(boulder2), true);
		try {
			Boulder boulder3 = new Boulder(position3, world);
			assertEquals(world.getBoulders().contains(boulder3), false);
		} 
		catch (NullPointerException exc) {}
		assertEquals(world.getLogs().contains(log), true);
	}
	
	@Test
	public void isWorkshopWithLogAndBoulderTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		Vector position2 = new Vector(1,1,1);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		new Boulder(position1, world);
		new Log(position1, world);
		position1.getEnclosingCube(world).changeTerrainType(TYPE_WORKSHOP);
		assertEquals(world.isWorkshopWithLogAndBoulder(position1.getEnclosingCube(world)), true);
		assertEquals(world.isWorkshopWithLogAndBoulder(position2.getEnclosingCube(world)), false);
	}
	
	@Test
	public void materialToPickUpTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		Vector position2 = new Vector(1,1,1);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Boulder boulder1 = new Boulder(position1, world);
		Log log1 = new Log(position1, world);
		Log log2 = new Log(position2, world);
		assertEquals(world.materialToPickUp(position1.getEnclosingCube(world)), boulder1);
		assertEquals(world.materialToPickUp(position2.getEnclosingCube(world)), log2);
	}
	
	@Test
	public void removeMaterialTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Boulder boulder1 = new Boulder(position1, world);
		Log log1 = new Log(position1, world);
		Log log2 = new Log(position1, world);
		world.removeMaterial(log1);
		assertEquals(world.getLogs().contains(log1), false);
		assertEquals(world.getBoulders().contains(boulder1), true);
		assertEquals(world.getLogs().contains(log2), true);
	}
	
	@Test
	public void getActiveFactionsTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction1 = new Faction(world);
		assertEquals(world.getActiveFactions().contains(faction1), true);
		try {
			for (int i=1; i<10; i++) {
				new Faction(world);
			}
			assertEquals(world.getActiveFactions().size(), 5);
		}
		catch (IllegalArgumentException exc) {}
	}
	
	@Test
	public void removeFactionTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction1 = new Faction(world);
		Faction faction2 = new Faction(world);
		world.removeFaction(faction1);
		assertEquals(world.getActiveFactions().contains(faction2), true);
		assertEquals(world.getActiveFactions().contains(faction1), false);
	}
	
	@Test
	public void getUnitsTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Unit unit1 = new Unit("Name", position1, false, world);
		assertEquals(world.getUnits().contains(unit1), true); //FIXME unit wordt geïnitialiseerd met negatieve waarde hitpoints? Dus hij gaat dood voordat hij heeft geleefd...
	}
	
	@Test
	public void addUnitTest() throws ModelException {
		Vector position1 = new Vector(1,1,0);
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Unit unit1 = new Unit("Name", position1, false, world);
		assertEquals(world.getUnits().contains(unit1), true);
	}	

}
