package homeMadeTests.partI;

import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import ogp.framework.util.ModelException;

public class PositionTest {
	
	private static Unit unit;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		unit = new Unit("Jatty", new int[] {0,0,0}, 100, 100, 100, 110, false);
		}

	// Test replacing the unit to a new valid position.
	@Test
	public void normalPosition() throws ModelException {
		double[] position = new double[3];
		position[0] = 15;
		position[1] = 20;
		position[2]= 35;
		unit.setPosition(position);
	}
	
	// Test replacing the unit to a new invalid position.
	@Test
	public void negativePosition() throws ModelException {
		double[] position = new double[3];
		position[0] = -15;
		position[1] = 20;
		position[2]= 35;
		unit.setPosition(position);
	}
	
	// Test the movement to certain valid cube of the unit
	@Test
	public void moveTest() throws ModelException {
		int[] cube = new int[3];
		cube[0] = 40;
		cube[1] = 40;
		cube[2] = 40;
		unit.moveTo(cube);
		//FIXME gaat deze test niet falen omdat de unit daar niet instant staat?
		assertEquals(unit.getCube(), cube);
	}
	
	// Test the movement to a valid adjacent cube.
	@Test
	public void moveToAdjacentTest() throws IllegalArgumentException, ModelException {
		int[] cube = new int[3];
		cube[0] = 20;
		cube[1] = 20;
		cube[2] = 20;
		unit.moveTo(cube);
		unit.moveToAdjacent(1, 0, 0);
		int[] newCube = new int[3];
		newCube[0] = 21;
		newCube[1] = 20;
		newCube[2] = 20;
		assertEquals(unit.getCube(), newCube);
	}
	
	// Test the datatype of the units position
	@Test
	public void dataTypePositionTest(){
		assertEquals(true, (unit.getPosition() instanceof double[]));
	}
	
	@Test
	public void moveToAdjacentTest2() throws IllegalArgumentException, ModelException {
		// Hier moet een exception gegooid worden //TODO
		int[] cube = new int[3];
		cube[0] = 0;
		cube[1] = 0;
		cube[2] = 0;
		unit.moveTo(cube);
		unit.moveToAdjacent(-1, 0, 0);
	}
}
