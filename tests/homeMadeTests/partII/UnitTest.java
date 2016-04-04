package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class UnitTest {
	
	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	@Test
	public void getWorldTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit = new Unit("Name", position, false, world);
		assertEquals(unit.getWorld(), world);
	}
	
	@Test
	public void setWorldTest() throws ModelException {
		World world1 = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		World world2 = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit = new Unit("Name", position, false, world1);
		unit.setWorld(world2);
		assertEquals(unit.getWorld(), world2);
	}
	
	@Test
	public void getFactionTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit = new Unit("Name", position, false, world);
		assertEquals( world.getActiveFactions().contains(unit.getFaction()), true);
	}
	
	@Test
	public void setFactionTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit = new Unit("Name", position, false, world);
		Faction faction = new Faction(world);
		unit.setFaction(faction);
		assertEquals(unit.getFaction(), faction);
	}
	
	@Test
	public void getCubeTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit = new Unit("Name", position, false, world);
		assertEquals(unit.getCube(), position); //FIXME
	}
	
//	@Test
//	public void getExperienceTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		int[] position = {5,5,5};
//		Unit unit = new Unit("Name", position, false, world);
//		unit.workAt(position);
//	}
	
	@Test
	public void isAliveTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit1 = new Unit("Name", position, false, world);
		Unit unit2 = new Unit("Name", position, false, world);
		unit1.setStrength(200);
		unit1.setAgility(200);
		unit2.setStrength(1);
		unit2.setAgility(1);
		assertEquals(unit1.isAlive(), true); 
		for (int i = 1; i < 10000; i++) {
			unit2.advanceTime(0.2);
			unit1.advanceTime(0.2);
			unit2.attack(unit1); //FIXME geraakt niet voorbij de if in deze functie
		}
		assertEquals(unit1.isAlive(), false);
	}
	
	@Test
	public void getCarriedMaterialTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Unit unit1 = new Unit("Name", position, false, world);
		new Log(position, world);
		unit1.workAt(position);
		for (int i = 1; i<100;i++){
			unit1.advanceTime(0.2);}
		assertEquals(unit1.getCarriedMaterial(), 2);
		assertEquals(unit1.isCarryingLog(), true);
		assertEquals(unit1.isCarryingBoulder(), false);
	}
	
	@Test
	public void workAtTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position1 = {5,5,5};
		int[] position2 = {5,5,6};
		Unit unit1 = new Unit("Name", position1, false, world);
		Log log = new Log(position1, world);
		unit1.workAt(position1);
		for (int i = 1; i<100;i++){
			unit1.advanceTime(0.2);} //unit1 is carrying a log now
		assertEquals(unit1.isCarryingLog(), true);
		unit1.workAt(position2);
		for (int i = 1; i<100;i++){
			unit1.advanceTime(0.2);} //unit1 has dropped the log now
		assertEquals(world.getLogs().contains(log), true); //FIXME dropmaterial
		assertEquals(log.getPosition(), Vector.getCentreOfCube(position2));
		world.setTerrainType(position1, TYPE_ROCK);
		unit1.workAt(position1);
		for (int i = 1; i<100;i++){
			unit1.advanceTime(0.2);} // unit1 has mined the rock
		assertEquals(world.getBoulders().size(), 1);
		assertEquals(world.getTerrainType(position1), TYPE_AIR);
	}
}
