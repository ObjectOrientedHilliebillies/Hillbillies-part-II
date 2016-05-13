package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class LogPosition extends CubeExpression{
	public LogPosition() {
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Log nearestLog = thisWorld.getNearestLog(thisCube);
		setValue(nearestLog.getCube());
	}
}
