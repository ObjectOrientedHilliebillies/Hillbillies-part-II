package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class WorkshopPosition extends CubeExpression{

	public WorkshopPosition() {
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Cube nearestWorkshop = thisWorld.getNearestWorkshop(thisCube);
		setValue(nearestWorkshop);
	}
}
