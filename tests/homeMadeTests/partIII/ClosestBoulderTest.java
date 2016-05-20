package homeMadeTests.partIII;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.DebugStream;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.model.task.Task;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class ClosestBoulderTest {
	
	private Facade facade;
	
	@Before
	public void setup() {
		this.facade = new Facade();
		DebugStream.activate();
	}
	
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	@Test
	public void testTaskExecuted() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_AIR;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		Unit unit = facade.createUnit("Dummy", new int[] { 0, 0, 0 }, 50, 50, 50, 50, false);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);
		Boulder boulder = new Boulder(new Vector(1, 1, 1), world);
		Scheduler scheduler = facade.getScheduler(faction);
		

//		List<Task> tasks = TaskParser.parseTasksFromString(
//				"name: \"dig\"\n"
//				+ "priority : 8\n"
//				+ "activities:\n"
//				+ "moveTo (next_to selected);\n"
//				+ "work selected;", facade.createTaskFactory(),
//				Collections.singletonList(new int[] { 1, 1, 1 }));
		
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"pickUpBoulder\" \n "
				+ "priority : 8\n "
				+ "activities: \n "
				//+ "moveTo (next_to boulder); \n "
				//+ "work boulder;" 
				+ "work (1,1,1);"
				,facade.createTaskFactory(), 
				Collections.singletonList(new int[] {1,1,1}));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		
		task.setExecutor(unit);
		System.out.println(task.getExecutor());
		
		facade.schedule(scheduler, task);
//		for (int i = 0; i < 100; i++) {
//			unit.setDefaultBehavior(true);
//			System.out.println(unit.getTask());
//			if (unit.getTask() == null)
//				unit.setDefaultBehavior(false);
//		advanceTimeFor(facade, world, 100, 0.02);
//		}
		
		unit.setDefaultBehavior(true);
		advanceTimeFor(facade, world, 100, 0.02);
		unit.setDefaultBehavior(false);
		
		// work task has been executed
		assertTrue(unit.isCarryingBoulder());
		}
	
	
		
private static void advanceTimeFor(IFacade facade, World world, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
		}
		

}