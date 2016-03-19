package homeMadeTests;

import static org.junit.Assert.*;

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

	@Test
	public void normalPosition() throws ModelException {
		double[] position = new double[3];
		position[0] = 15;
		position[1] = 20;
		position[2]= 35;
		unit.setPosition(position);
	}
	
	@Test
	public void negativePosition() throws ModelException {
		double[] position = new double[3];
		position[0] = -15;
		position[1] = 20;
		position[2]= 35;
		unit.setPosition(position);
	}
	
	@Test
	public void moveTest() throws ModelException {
		int[] cube = new int[3];
		cube[0] = 20;
		cube[1] = 20;
		cube[2] = 20;
		unit.moveTo(cube);
		assertEquals(unit.getCube(), cube);
	}
	
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
}
