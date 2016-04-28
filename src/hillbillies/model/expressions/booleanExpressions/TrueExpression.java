package hillbillies.model.expressions.booleanExpressions;

import hillbillies.model.expressions.Expression;

public class TrueExpression extends BooleanExpression{

	@Override
	public Expression eval() {
		return this;
	}

}
