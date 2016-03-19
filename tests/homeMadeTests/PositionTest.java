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
	
	

}
