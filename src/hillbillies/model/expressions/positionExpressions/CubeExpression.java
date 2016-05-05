package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.expressions.Expression;

public abstract class CubeExpression extends Expression<Cube>{
	public CubeExpression(Cube cube) {
		setValue(object);
	}
}
