package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class BoulderPosition extends CubeExpression{
	public BoulderPosition() {
	}
	
	@Override
	public Cube getValue() {
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Cube thisCube = thisUnit.getCube();
		World thisWorld = thisUnit.getWorld();
		Boulder nearestBoulder = thisWorld.getClosestBoulder(thisCube);
		System.out.println(nearestBoulder);
		return nearestBoulder.getCube();
	}
}