package homeMadeTests;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;

public class NameTest {

	// In the current version, a whole bunch of rationale numbers are defined.
	// This stems from the fact that in a previous version, the tests for all
	// the methods applicable to rationale numbers were worked out in separate
	// classes.
	private static Unit unit1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	// If this test ends with an exception, the JUnit framework will
	// signal it as a failure.
	@Test
	public final void extendedConstructor_LegalCase() throws Exception {
		
		// In using the method assertEquals, care must be taken that
		// both objects have exactly the same type. In this case, we must
		// see to it that both objects are values of type long.
		
	}
}