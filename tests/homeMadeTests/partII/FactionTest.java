package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.Part2;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class FactionTest {
	
	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}
	private static Faction faction;
	//private static Unit unit;
	//private World world;
	int[] position = {1,1,0};
	int[][][] types = {{{1},{0}},{{1},{0}},{{1},{0}}};
	DefaultTerrainChangeListener defaultTerrainChangeListener;
	
	@Test
	public void MaxNbFactionsTest() throws ModelException {
		int nbX = 10;
		int nbY = 10;
		int nbZ = 10;
		//Part2.main(null);
		World world = facade.createWorld(new int[nbX][nbY][nbZ], defaultTerrainChangeListener);
		new Unit("Testeen", position, false, world);
		new Unit("Testtwee", position, false, world);
		assertEquals(world.getNbOffFactions(), 2);
		new Unit("Testdrie", position, false, world);
		new Unit("Testvier", position, false, world);
		assertEquals(world.getNbOffFactions(), 4);
		new Unit("Testvijf", position, false, world);
		new Unit("Testzes", position, false, world);
		new Unit("Testzeven", position, false, world);
		assertEquals(world.getNbOffFactions(), 5);
	}
	
	@Test
	public void MaxNbUnitsInFactionTest() throws ModelException {
		int nbX = 10;
		int nbY = 10;
		int nbZ = 10;
		//Part2.main(null);
		World world = facade.createWorld(new int[nbX][nbY][nbZ], defaultTerrainChangeListener);
		for (int i = 1; i<100; i++)
			faction.addUnit(new Unit("Test", position, false, world));
		assertEquals(faction.getNbOffUnitsInFaction(), 50);
	}

}
