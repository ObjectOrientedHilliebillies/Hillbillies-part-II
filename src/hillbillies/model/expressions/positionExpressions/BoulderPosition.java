package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class BoulderPosition extends CubeExpression {
	public BoulderPosition() {
	}
	
	@Override
	public Cube getValue(Task task) {
		Unit thisUnit = task.getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Boulder nearestBoulder = thisWorld.getClosestBoulder(thisCube);
		System.out.println(nearestBoulder);
		return nearestBoulder.getCube();
	}
}