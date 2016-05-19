package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.task.Task;

public class WorkshopPosition extends CubeExpression{

	public WorkshopPosition() {
	}
	
	@Override
	public Cube getValue(Task task) {
		Unit thisUnit = task.getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Cube nearestWorkshop = thisWorld.getClosestWorkshop(thisCube);
		return nearestWorkshop;
	}
}
