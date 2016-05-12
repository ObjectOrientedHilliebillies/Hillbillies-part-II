package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class BoulderPosition extends CubeExpression{
	public BoulderPosition() {
		Unit thisUnit = this.getStatement().getTask().getUnit();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Boulder nearestBoulder = thisWorld.getNearestBoulder(thisCube);
		setValue(nearestBoulder.getCube());
	}
}