package homeMadeTests.partII;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Log;
import hillbillies.model.Material;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class MaterialTest {

	
	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}
	
	
	@Test
	public void getWeightTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Material material1 = new Log(position, world, 40);
		assertEquals(material1.getWeight(), 40);
		try {
			new Log(position, world, 400);
		}
		catch (IllegalArgumentException exc) {}
	}
	
	@Test
	public void getPositionTest() throws ModelException {
		World world = facade.createWorld(new int[10][10][10], new DefaultTerrainChangeListener());
		int[] position = {5,5,5};
		Vector vector = new Vector(5,5,5);
		Material material1 = new Log(position, world);
		assertEquals(material1.getPosition(), vector); //FIXME
	}

}
