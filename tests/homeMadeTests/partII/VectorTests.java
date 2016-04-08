package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class VectorTests {
	private Facade facade;
	private static Vector vector1;
	private static Vector vector2;
	private static Vector vector3;
	private static Vector vector4;
	private static Vector vector5;
	private static Vector vector6;
	
	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
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
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		assertEquals(true, vector1.equals(vector2));
		assertEquals(false, vector1.equals(vector3));
		assertEquals(true, vector1.getEnclosingCube(world).equals(vector2.getEnclosingCube(world)));
	}

}