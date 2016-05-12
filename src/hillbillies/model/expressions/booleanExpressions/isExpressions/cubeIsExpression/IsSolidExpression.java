package hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression;

import hillbillies.model.Cube;
import hillbillies.model.expressions.positionExpressions.CubeExpression;

public class IsSolidExpression extends CubeIsExpression{
	public IsSolidExpression(CubeExpression e) {
		super(e);
		Cube thisCube = (Cube) e.getValue();
		setValue(thisCube.isSolid());
	}
}
