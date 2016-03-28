package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.part2.facade.Facade;
import ogp.framework.util.ModelException;

public class AliveTest {

	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
	@Test
	public void testInitialAgilityTooLow() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 24, 50, 50, false);
		assertTrue("An attribute value of 24 should be replaced with a valid value",
				25 <= facade.getAgility(unit) && facade.getAgility(unit) <= 100);
	}

}
