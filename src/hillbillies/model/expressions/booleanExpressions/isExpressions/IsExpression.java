package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;

public abstract class IsExpression extends BooleanExpression{

	public IsExpression() {
		// TODO Auto-generated constructor stub
	}

	public abstract BooleanValueExpression eval();

}
