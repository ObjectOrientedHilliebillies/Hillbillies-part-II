package homeMadeTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.Vector;
import ogp.framework.util.ModelException;

public class VectorTests {
	
	private static Vector vector1;
	private static Vector vector2;
	private static Vector vector3;
	private static Vector vector4;
	private static Vector vector5;
	private static Vector vector6;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		vector1 = new Vector(2.5, 3.5, 4.5);
		vector2 = new Vector(2.5, 3.5, 4.5);
		vector3 = new Vector(2.3, 3.3, 4.3);
		vector4 = new Vector(2, 3, 4);
		vector5 = new Vector(3.5, 4.5, 5.5);
		vector6 = new Vector(3, 4, 5);
		
		}
	@Test
	public void equalsTest() throws ModelException {
		assertEquals(true, Vector.equals(vector1, vector2));
		assertEquals(false, Vector.equals(vector1, vector3));
		assertEquals(true, Vector.equals(vector1.getCube(), vector2.getCube());
	}

}