package hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.positionExpressions.CubeExpression;

public class IsSolidExpression extends CubeIsExpression{
	public IsSolidExpression(CubeExpression e) {
		super(e);
	}

	@Override
	public Boolean getValue(Task task) {
		Cube thisCube = getExpression().getValue(task);
		return thisCube.isSolid();
	}
}
