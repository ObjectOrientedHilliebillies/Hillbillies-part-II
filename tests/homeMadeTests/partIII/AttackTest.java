package homeMadeTests.partIII;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import hillbillies.DebugStream;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class AttackTest {
	
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
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		// FIXME Given syntacs does not work.
		Unit unit = facade.createUnit("Dummy", new int[] { 0, 0, 0 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);

		Scheduler scheduler = facade.getScheduler(faction);

//		List<Task> tasks = TaskParser.parseTasksFromString(
//				"name: \"dig\"\n"
//				+ "priority : 8\n"
//				+ "activities:\n"
//				+ "moveTo (next_to selected);\n"
//				+ "work selected;", facade.createTaskFactory(),
//				Collections.singletonList(new int[] { 1, 1, 1 }));
		
		List<Task> tasks = TaskParser.parseTasksFromString(
				"", 
				facade.createTaskFactory(), 
				Collections.singletonList(new int[] {1,1,1}));
		


}}
