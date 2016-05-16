package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class WorkshopPosition extends CubeExpression{

	public WorkshopPosition() {
	}
	
	@Override
	public Cube getValue() {
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Cube nearestWorkshop = thisWorld.getClosestWorkshop(thisCube);
		return nearestWorkshop;
	}
}
