package homeMadeTests.partIII;

import static org.junit.Assert.*;

import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.DebugStream;
import hillbillies.model.Cube;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.model.task.Task;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.programs.TaskParser;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class General {
	
	private Facade facade;
	
	Vector position = new Vector(5,0,5);
	
	public List<Integer> getPosition1() {
		List<Integer> cubeList = new ArrayList<>();
		 cubeList.add(5);
		 cubeList.add(0);
		 cubeList.add(5);
		 return cubeList;
	}

	public List<Integer> getPosition2() {
		List<Integer> cubeList = new ArrayList<>();
		 cubeList.add(5);
		 cubeList.add(0);
		 cubeList.add(6);
		 return cubeList;
	}
	
	@Before
	public void setup() {
		this.facade = new Facade();
		DebugStream.activate();
	}
	
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	
	/**
	 * Facade getCubeType test.
	 * Facade setCubeType test.
	 * Collapse if not connected to border test.
	 */
	@Test
	public void cubeTypeTest() throws ModelException{
		System.out.println("#################");
		System.out.println("# cube collapse #");
		System.out.println("#################");
		
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		
		// Illegal index test.
		try{
			facade.getCubeType(world, 10, 10, 10);
			assertTrue(false);
		}catch (ModelException e){
			assertTrue(true);
		}
		
		// Illegal index test.
		try{
			facade.setCubeType(world, 10, 10, 9, 3);
			assertTrue(false);
		}catch (ModelException e){
			assertTrue(true);
		}
		
		// Illegal terraintype test.
		try{
			facade.setCubeType(world, 9, 9, 9, 5);
			assertTrue("The terraintype was invalid", false);
		}catch (ModelException e){
			assertTrue(true);
		}
		
		
		// Set everything to air.
			for (int x=0 ; x!=10 ; x++){
				for (int y=0 ; y!=10 ; y++){
					for (int z=0 ; z!=10 ; z++){
							facade.setCubeType(world, x, y, z, 0);
					}
				}
			}
			
			// Check if 5,5,5 is air.
			assertTrue(facade.getCubeType(world, 5, 5, 5) == 0);
			
			// Check if solid collapses.
			facade.setCubeType(world, 5, 5, 5, 2);
			assertTrue(facade.getCubeType(world, 5, 5, 5) == 0);
			
			// Set up some solids.
			for (int y=0 ; y!=10; y++){
				facade.setCubeType(world, 5, y, 5, 2);
			}
			for (int z=6; z!=10; z++){
				facade.setCubeType(world, 5, 5, z, 2);
			}
			
			// Check if solids are there.
			for (int y=0 ; y!=10; y++){
				assertTrue(facade.getCubeType(world, 5, y, 5) == 2);
			}
			for (int z=6; z!=10; z++){
				assertTrue(facade.getCubeType(world, 5, 5, z) == 2);
			}
			
			// Collapse some cubes.
			facade.setCubeType(world, 5, 5, 5, 0);
			facade.setCubeType(world, 5, 1, 5, 0);
			facade.setCubeType(world, 5, 5, 9, 0);
			
			// Check if the correct cubes collapsed.
			for (int z=6; z!=10; z++){
				assertTrue(facade.getCubeType(world, 5, 5, z) == 0);
			}
			for (int y=1; y!=5; y++){
				assertTrue(facade.getCubeType(world, 5, y, 5) == 0);
			}
			for (int y=6; y!=10; y++){
				assertTrue(facade.getCubeType(world, 5, y, 5) == 2);
			}
			assertTrue(facade.getCubeType(world, 5, 0, 5) == 2);	
	}

	/**
	 * Test dig0.
	 */
	@Test
	public void testTaskDig0() throws ModelException {
		System.out.println("#############");
		System.out.println("# test dig0 #");
		System.out.println("#############");
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		// FIXME Given syntacs does not work.
		Unit unit = facade.createUnit("Dummy", new int[] { 0, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);

		Scheduler scheduler = facade.getScheduler(faction);

		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"dig\"\n"
				+ "priority : 8\n"
				+ "activities:\n"
				+ "moveTo (next_to selected);\n"
				+ "work selected;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 }));



		//Check if the unit's world and faction are correct.
		assertTrue(unit.getWorld() == world);
		assertTrue(world.getActiveFactions().contains(faction));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		// test name
		assertEquals("dig", facade.getName(task));
		// test priority
		assertEquals(8, facade.getPriority(task));

		facade.schedule(scheduler, task);
		advanceTimeFor(facade, world, 100, 0.02);

		// work task has been executed
		assertEquals(TYPE_AIR, facade.getCubeType(world, 1, 1, 1));
		// work task is removed from scheduler
		assertFalse(facade.areTasksPartOf(scheduler, Collections.singleton(task)));
	}
	
	/**
	 * Test dig.
	 */
	@Test
	public void testTaskDig() throws ModelException {
		System.out.println("############");
		System.out.println("# test dig #");
		System.out.println("############");
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		Unit unit = facade.createUnit("Dummy", new int[] { 0, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);

		Scheduler scheduler = facade.getScheduler(faction);
		
		List<int[]> selected = new ArrayList<>();
		selected.add(new int[] { 1, 1, 1 });
		selected.add(new int[] { 1, 1, 0 });

		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"dig\"\n"
				+ "priority : 8\n"
				+ "activities:if carries_item(this) then\n"
				+ "work here;\n"
				+ "fi\n"
				+ "if is_solid(selected) then\n"
				+ "moveTo (next_to selected);\n"
				+ "work selected;\n"
				+ "fi", facade.createTaskFactory(),
				selected);

		//Check if the unit's world and faction are correct.
		assertTrue(unit.getWorld() == world);
		assertTrue(world.getActiveFactions().contains(faction));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(2, tasks.size());
		Task task = tasks.get(0);
		
		// test name
		assertEquals("dig", facade.getName(task));
		// test priority
		assertEquals(8, facade.getPriority(task));

		facade.schedule(scheduler, tasks.get(0));
		facade.schedule(scheduler, tasks.get(1));
		advanceTimeFor(facade, world, 100, 0.02);

		// work task has been executed
		assertEquals(TYPE_AIR, facade.getCubeType(world, 1, 1, 1));
		assertEquals(TYPE_AIR, facade.getCubeType(world, 1, 1, 0));
		// work task is removed from scheduler
		assertFalse(facade.areTasksPartOf(scheduler, Collections.singleton(task)));
	}

	/**
	 * test impossible task
	 */
	@Test
	public void  testImpossibleTask() throws ModelException{
		System.out.println("##################");
		System.out.println("# impossible dig #");
		System.out.println("##################");
		int[][][] types = new int[10][10][10];
		for (int x=0; x!=5; x++){
			for (int y=0; y!=5; y++){
				for (int z=0; z!=5; z++){
					types[x][y][z] = TYPE_ROCK;
				}
			}
		}
		types[1][1][2] = TYPE_TREE;
		types[5][5][5] = TYPE_WORKSHOP;
	
		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		
		// FIXME Unit is also able to spauwn in 0,0,0!
		Unit unit = facade.createUnit("Dummy", new int[] { 7, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);
	
		Scheduler scheduler = facade.getScheduler(faction);
		
		List<int[]> selected = new ArrayList<>();
		selected.add(new int[] { 1, 1, 1 });
		selected.add(new int[] { 1, 1, 0 });
	
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"dig\"\n"
				+ "priority : 8\n"
				+ "activities:if carries_item(this) then\n"
				+ "work here;\n"
				+ "fi\n"
				+ "if is_solid(selected) then\n"
				+ "moveTo (next_to selected);\n"
				+ "work selected;\n"
				+ "fi", facade.createTaskFactory(),
				selected);
	
		//Check if the unit's world and faction are correct.
		assertTrue(unit.getWorld() == world);
		assertTrue(world.getActiveFactions().contains(faction));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(2, tasks.size());
		Task task = tasks.get(0);
		
		// test name
		assertEquals("dig", facade.getName(task));
		// test priority
		assertTrue(8 == task.getPriority());
	
		System.out.println(tasks.get(0).getPriority());
		System.out.println(tasks.get(1).getPriority());
		
		facade.schedule(scheduler, tasks.get(0));
		facade.schedule(scheduler, tasks.get(1));
		advanceTimeFor(facade, world, 100, 0.02);
	
		// The tasks priority should been reduced.
		assertTrue(8 > tasks.get(0).getPriority());
		System.out.println(tasks.get(0).getPriority());
		System.out.println(tasks.get(1).getPriority());
		assertTrue(8 > tasks.get(1).getPriority());
	
		// work task is removed from scheduler
		assertFalse(facade.areTasksPartOf(scheduler, Collections.singleton(task)));
	}
	
	/**
 	 * Helper method to advance time for the given world by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(IFacade facade, World world, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
	}
	
}
//	
//	@Test
//	public void getWorldTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit = new Unit("Name", position, false, world);
//		assertEquals(unit.getWorld(), world);
//	}
//	
//	@Test
//	public void setWorldTest() throws ModelException {
//		World world1 = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		World world2 = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit = new Unit("Name", position, false, world1);
//		unit.setWorld(world2);
//		assertEquals(unit.getWorld(), world2);
//	}
//	
//	@Test
//	public void getFactionTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit = new Unit("Name", position, false, world);
//		assertEquals( world.getActiveFactions().contains(unit.getFaction()), true);
//	}
//	
//	@Test
//	public void setFactionTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit = new Unit("Name", position, false, world);
//		Faction faction = new Faction(world);
//		unit.setFaction(faction);
//		assertEquals(unit.getFaction(), faction);
//	}
//	
//	@Test
//	public void getCubeTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit = new Unit("Name", position, false, world);
//		assertEquals(true, position.getEnclosingCube(world).equals(unit.getCube())); //FIXME
//	}
//	
////	@Test
////	public void getExperienceTest() throws ModelException {
////		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
////		int[] position = {5,5,5};
////		Unit unit = new Unit("Name", position, false, world);
////		unit.workAt(position);
////	}
//	
//	@Test
//	public void isAliveTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit1 = new Unit("Name", position, false, world);
//		Unit unit2 = new Unit("Name", position, false, world);
//		unit1.setStrength(200);
//		unit1.setAgility(200);
//		unit2.setStrength(1);
//		unit2.setAgility(1);
//		assertEquals(unit1.isAlive(), true); 
//		for (int i = 1; i < 10000; i++) {
//			unit2.advanceTime(0.2);
//			unit1.advanceTime(0.2);
//			unit2.attack(unit1); //FIXME geraakt niet voorbij de if in deze functie
//		}
//		assertEquals(unit1.isAlive(), false);
//	}
//	
//	@Test
//	public void getCarriedMaterialTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit1 = new Unit("Name", position, false, world);
//		new Log(position, world);
//		Cube position1 = new Cube(this.getPosition1(), world);
//		unit1.workAt(position1);
//		for (int i = 1; i<100;i++){
//			unit1.advanceTime(0.2);}
//		assertEquals(unit1.getCarriedMaterial(), 2);
//		assertEquals(unit1.isCarryingLog(), true);
//		assertEquals(unit1.isCarryingBoulder(), false);
//	}
//	
//	@Test
//	public void workAtTest() throws ModelException {
//		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
//		Unit unit1 = new Unit("Name", position, false, world);
//		Log log = new Log(position, world);
//		Cube position1 = new Cube(this.getPosition1(), world);
//		System.out.println(position1.toString());
//		System.out.println(position.toString());
//		unit1.workAt(position1);
//		for (int i = 1; i<100;i++){
//			unit1.advanceTime(0.2);} //unit1 is carrying a log now
//		assertEquals(unit1.isCarryingLog(), true);
//		Cube position2 = new Cube(this.getPosition2(), world);
//		unit1.workAt(position2);
//		for (int i = 1; i<100;i++){
//			unit1.advanceTime(0.2);} //unit1 has dropped the log now
//		assertEquals(world.getLogs().contains(log), true); //FIXME dropmaterial
//		assertEquals(log.getPosition(), position2.getCentreOfCube());
//		world.setTerrainType(position1, TYPE_ROCK);
//		unit1.workAt(position1);
//		for (int i = 1; i<100;i++){
//			unit1.advanceTime(0.2);} // unit1 has mined the rock
//		assertEquals(world.getBoulders().size(), 1);
//		assertEquals(position2.getTerrainType(), TYPE_AIR);
//	}
//}
