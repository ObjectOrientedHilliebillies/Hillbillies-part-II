package homeMadeTests.partIII;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.DebugStream;
import hillbillies.model.Faction;
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
	
	private World createWorld1() throws ModelException{
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
	
		return facade.createWorld(types, new DefaultTerrainChangeListener());
	}
	
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
		System.out.println("#################################################");
		System.out.println("# impossible dig #");
		System.out.println("##################");
		
		World world = createWorld1();
		
		Unit unit = facade.createUnit("Dummy", new int[] { 7, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);
	
		Scheduler scheduler = facade.getScheduler(faction);
		
		//List<int[]> selected = new ArrayList<>();
		int [] pos1 = new int[] { 1, 1, 1 };
		int [] pos2 = new int[] { 5, 5, 5 };

		List<Task> tasks1 = TaskParser.parseTasksFromString(
				"name: \"dig\"\n"
				+ "priority : 8\n"
				+ "activities:if carries_item(this) then\n"
				+ "work here;\n"
				+ "fi\n"
				+ "if is_solid(selected) then\n"
				+ "moveTo (next_to selected);\n"
				+ "work selected;\n"
				+ "fi", facade.createTaskFactory(),
				Collections.singletonList(pos1));
		
		List<Task> tasks2 = TaskParser.parseTasksFromString(
				"name: \"dig\"\n"
				+ "priority : 7\n"
				+ "activities:if carries_item(this) then\n"
				+ "work here;\n"
				+ "fi\n"
				+ "if is_solid(selected) then\n"
				+ "moveTo (next_to selected);\n"
				+ "work selected;\n"
				+ "fi", facade.createTaskFactory(),
				Collections.singletonList(pos2));
	
		System.out.println(tasks1.get(0).getPriority());
		System.out.println(tasks2.get(0).getPriority());
		
		facade.schedule(scheduler, tasks1.get(0));
		facade.schedule(scheduler, tasks2.get(0));
		advanceTimeFor(facade, world, 100, 0.02);
	
		// The tasks priority should been reduced.
		
		assertTrue(facade.areTasksPartOf(scheduler, tasks1));
		assertFalse(facade.areTasksPartOf(scheduler, tasks2));
		System.out.println(tasks1.get(0).getPriority());
		System.out.println(tasks2.get(0).getPriority());
		assertTrue(world.getCube(pos2).getTerrainType() == TYPE_AIR);
	}
	
	/**
	 * Test Banaan.
	 */
	@Test
	public void Bannaan() throws ModelException{
		System.out.println("#################################################");
		System.out.println("# Kill everybody #");
		System.out.println("##################");
		
	
		World world = createWorld1();
		
		Unit unit = facade.createUnit("Dummy", new int[] { 7, 0, 0 }, 200, 200, 200, 200, true);
		Unit unit1 = facade.createUnit("Dummy", new int[] { 7, 0, 0 }, 50, 50, 50, 50, true);
		Unit unit2 = facade.createUnit("Dummy", new int[] { 7, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		facade.addUnit(unit1, world);
		facade.addUnit(unit2, world);
	
		Faction faction = facade.getFaction(unit);
		Scheduler scheduler = facade.getScheduler(faction);

		List<Task> tasks1 = TaskParser.parseTasksFromString(
				"name: \"Banaan\"\n"
				+ "priority : 22\n"
				+ "activities :\n"
				+ "a := any;\n"
				+ "while is_alive a do\n"
				+ "if is_friend a then\n"
				+ "break;\n"
				+ "fi\n"
				+ "while is_alive a do\n"				
				+ "moveTo (position_of a);\n"
				+ "attack (a);\n"
				+ "done\n"
				+ "a := any;\n"
				+ "done", facade.createTaskFactory(),
				new ArrayList<>());
		
		facade.schedule(scheduler, tasks1.get(0));
		advanceTimeFor(facade, world, 180, 0.2);
		
		assertTrue(facade.getUnits(world).size()==1);

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

