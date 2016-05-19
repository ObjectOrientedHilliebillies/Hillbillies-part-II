package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.task.Task;

public class LogPosition extends CubeExpression{
	public LogPosition() {
	}
	
	@Override 
	public Cube getValue(Task task) {
		Unit thisUnit = task.getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Log nearestLog = thisWorld.getClosestLog(thisCube);
		return nearestLog.getCube();
	}
}
