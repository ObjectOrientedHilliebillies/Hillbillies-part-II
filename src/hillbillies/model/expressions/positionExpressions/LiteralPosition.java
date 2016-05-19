package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;

public class LiteralPosition extends CubeExpression<int[]>{

	public LiteralPosition(int x, int y, int z) {
		this.setPosition(new int[] {x, y, z});
	}
	
	@Override
	public Cube getValue(Task task) {
		Unit unit = task.getExecutor();
		World world = unit.getWorld();
		return world.getCube(getPosition());
	}
}
