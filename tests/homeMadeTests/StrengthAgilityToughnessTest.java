package homeMadeTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;

public class StrengthAgilityToughnessTest {
	
	private static Unit unit;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unit = new Unit("Jatty", new int[] {0,0,0}, 100, 100, 100, 110, false);
	}

	@Test
	public void normalTest() {
		unit.setAgility(160);
		unit.setToughness(160);
		unit.setStrength(160);
		assertEquals(160, unit.getStrength());
		assertEquals(160, unit.getAgility());
		assertEquals(160, unit.getToughness());
	}
	
	@Test
	public void tooLowOrHighTest() {
		unit.setAgility(-1);
		unit.setToughness(600);
		unit.setStrength(-10);
		assertEquals(160, unit.getStrength());
		assertEquals(160, unit.getAgility());
		assertEquals(160, unit.getToughness());
	}

}
