package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.Part2;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

public class FactionTest {

	private static Faction faction;
	//private static Unit unit;
	private World world;
	int[] position = {1,1,0};
	int[][][] types = {{{1},{0}},{{1},{0}},{{1},{0}}};
	DefaultTerrainChangeListener defaultTerrainChangeListener;
	
	@Test
	public void MaxNbFactionsTest() {
		
		//Part2.main(null);
		this.world = new World(types , defaultTerrainChangeListener);
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
	public void MaxNbUnitsInFactionTest() {
		this.world = new World(types , defaultTerrainChangeListener);
		for (int i = 1; i<100; i++)
			faction.addUnit(new Unit("Test", position, false, world));
		assertEquals(faction.getNbOffUnitsInFaction(), 50);
	}

}
