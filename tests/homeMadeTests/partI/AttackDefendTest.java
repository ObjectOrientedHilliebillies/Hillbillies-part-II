package homeMadeTests.partI;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;

public class AttackDefendTest {

	private static Unit unit;
	private static Unit unit2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unit = new Unit("Jatty", new int[] {0,0,0}, 100, 100, 100, 110, false);
		unit2 = new Unit("Noob", new int[] {0,0,0}, 100, 100, 100, 100, false);
		}
	
	@Test
	public void 
}
