package hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.positionExpressions.CubeExpression;

public class IsSolidExpression extends CubeIsExpression{
	public IsSolidExpression(CubeExpression<?> cube) {
		super(cube);
		this.cube = cube;
	}
	
	private CubeExpression<?> cube;
	
	@Override
	public Boolean getValue(Task task) {
		Cube thisCube = cube.getValue(task);
		return thisCube.isSolid();
	}
}
