package hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression;

import hillbillies.model.Cube;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.task.Task;

public class IsPassableExpression extends CubeIsExpression{
	
	public IsPassableExpression(CubeExpression<?> cube) {
		super(cube);
		this.cube = cube;
	}
	
	private CubeExpression<?> cube;
	
	@Override
	public Boolean getValue(Task task) {
		Cube cube = this.cube.getValue(task);
		return cube.isPassable();
	}
}
