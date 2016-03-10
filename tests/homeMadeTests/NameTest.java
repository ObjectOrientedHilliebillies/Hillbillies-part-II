package homeMadeTests;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import ogp.framework.util.ModelException;

public class NameTest {

	// In the current version, a whole bunch of rationale numbers are defined.
	// This stems from the fact that in a previous version, the tests for all
	// the methods applicable to rationale numbers were worked out in separate
	// classes.
	private static Unit unit;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unit = new Unit("Jatty", new int[] {0,0,0}, 100, 50, 100, 110, false);
	}
	
	@Test
	public void testSetValidName() throws IllegalArgumentException {
		unit.setName("Victor");
		assertEquals("Victor", unit.getName());
	}
	
	@Test
	public void testSetValidNameWithSpaces() throws IllegalArgumentException {
		unit.setName("Victor Van Eetvelt");
		assertEquals("Victor Van Eetvelt", unit.getName());
	}
	
	@Test
	public void testSetValidNameWithQuote() throws IllegalArgumentException {
		unit.setName("Victor'vaneetvelt");
		assertEquals("Victor'vaneetvelt", unit.getName());
	}
	
	@Test
	public void testSetValidNameWithDoubleQuote() throws IllegalArgumentException {
		unit.setName("John \"Johnnie\" O'Hare the first");
		assertEquals("John \"Johnnie\" O'Hare the first", unit.getName());
	}
	
	// If this test ends with an exception, the JUnit framework will
	// signal it as a failure.
	//@Test
	//public final void extendedConstructor_LegalCase() throws Exception {
		
		// In using the method assertEquals, care must be taken that
		// both objects have exactly the same type. In this case, we must
		// see to it that both objects are values of type long.
		
	//}
}