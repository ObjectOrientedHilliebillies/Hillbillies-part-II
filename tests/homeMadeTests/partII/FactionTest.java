package homeMadeTests.partII;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Cube;
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
	
//	List<Integer> cubeList = new ArrayList<>();
//	cubeList.add(5);
//	cubeList.add(5);
//	cubeList.add(5);
//	Cube position = new Cube(cubeList, world);
	
	Vector position = new Vector(5,5,5);
	
//	@Test
//	public void MaxNbFactionsTest() throws ModelException {
//		int nbX = 10;
//		int nbY = 10;
//		int nbZ = 10;
//		//Part2.main(null);
//		World world = facade.createWorld(new int[nbX][nbY][nbZ], new DefaultTerrainChangeListener());
//		new Unit("Testeen", position, false, world);
//		new Unit("Testtwee", position, false, world);
//		assertEquals(world.getNbOffFactions(), 2);
//		new Unit("Testdrie", position, false, world);
//		new Unit("Testvier", position, false, world);
//		assertEquals(world.getNbOffFactions(), 4);
//		new Unit("Testvijf", position, false, world);
//		new Unit("Testzes", position, false, world);
//		new Unit("Testzeven", position, false, world);
//		assertEquals(world.getNbOffFactions(), 5);
//	}
//	
//	@Test
//	public void MaxNbUnitsInFactionTest() throws ModelException {
//		int nbX = 10;
//		int nbY = 10;
//		int nbZ = 10;
//		//Part2.main(null);
//		World world = facade.createWorld(new int[nbX][nbY][nbZ], new DefaultTerrainChangeListener());
//		Faction faction = new Faction(world);
//		for (int i = 1; i<100; i++)
//			faction.addUnit(new Unit("Test", position, false, world));
//		assertEquals(faction.getNbOffUnitsInFaction(), 50);
//	}
	
	@Test 
	public void setWorldTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		assertEquals(faction.getWorld(), world);
	}
	
	@Test
	public void getUnitsInFactionTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		Unit unit = new Unit("Name", position, false, world);
		faction.addUnit(unit);
		assertEquals(faction.getUnitsInFaction().contains(unit), true);
	}
	
	@Test
	public void getNbOfUnitsInFactionAndAddUnitTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		Unit unit1 = new Unit("Name", position, false, world);
		Unit unit2 = new Unit("Name", position, false, world);
		Unit unit3 = new Unit("Name", position, false, world);
		Unit unit4 = new Unit("Name", position, false, world);
		Unit unit5 = new Unit("Name", position, false, world);
		Unit unit6 = new Unit("Name", position, false, world);
		Unit unit7 = new Unit("Name", position, false, world);
		Unit unit8 = new Unit("Name", position, false, world);
		Set<Unit> units = new HashSet();
		units.add(unit8);
		units.add(unit7);
		units.add(unit6);
		units.add(unit5);
		units.add(unit4);
		units.add(unit3);
		units.add(unit2);
		units.add(unit1);
		faction.addUnit(unit8);
		faction.addUnit(unit7);
		faction.addUnit(unit6);
		faction.addUnit(unit5);
		faction.addUnit(unit4);
		faction.addUnit(unit3);
		faction.addUnit(unit2);
		faction.addUnit(unit1);
		assertEquals(faction.getNbOffUnitsInFaction(), 8);
		assertEquals(faction.getUnitsInFaction().containsAll(units), true);
		assertEquals(unit8.getFaction(), faction);
	}
	
	@Test
	public void removeUnitTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		Unit unit1 = new Unit("Name", position, false, world);
		Unit unit2 = new Unit("Name", position, false, world);
		Unit unit3 = new Unit("Name", position, false, world);
		Unit unit4 = new Unit("Name", position, false, world);
		Unit unit5 = new Unit("Name", position, false, world);
		Unit unit6 = new Unit("Name", position, false, world);
		Unit unit7 = new Unit("Name", position, false, world);
		Unit unit8 = new Unit("Name", position, false, world);
		Set<Unit> units = new HashSet();
		units.add(unit8);
		units.add(unit7);
		units.add(unit6);
		units.add(unit5);
		units.add(unit4);
		units.add(unit3);
		units.add(unit2);
		units.add(unit1);
		faction.addUnit(unit8);
		faction.addUnit(unit7);
		faction.addUnit(unit6);
		faction.addUnit(unit5);
		faction.addUnit(unit4);
		faction.addUnit(unit3);
		faction.addUnit(unit2);
		faction.addUnit(unit1);
		faction.removeUnit(unit3);
		assertEquals(faction.getNbOffUnitsInFaction(), 7);
		assertEquals(faction.getUnitsInFaction().containsAll(units), false);
		assertEquals(unit3.getFaction(), faction); //FIXME da's fout, unit 3 mag niet meer bestaan
	}
	

}
