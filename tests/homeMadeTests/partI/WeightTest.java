package homeMadeTests.partI;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;

public class WeightTest {

	private static Unit unit;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unit = new Unit("Jatty", new int[] {0,0,0}, 100, 100, 100, 110, false);
	}

	@Test
	public void testSetWeight() {
		unit.setWeight(160);
		assertEquals(160,unit.getWeight());
	}
	
	@Test
	public void testWeightTooHigh() {
		unit.setWeight(10000);
		assertEquals(unit.getMinWeight(),unit.getWeight());
	}
	
	@Test
	public void testWeightTooLow() {
		unit.setWeight(-100);
		assertEquals(unit.getMinWeight(), unit.getWeight());
	}
	
	@Test
	public void testWeightTooLow2() {
		unit.setWeight(1);
		assertEquals(unit.getMinWeight(), unit.getWeight());
	}
	
	@Test
	public void testStrengthInfluencesWeight() {
		unit.setWeight(110);
		unit.setStrength(200);
		assertEquals(unit.getMinWeight(), unit.getWeight());
	}
}
