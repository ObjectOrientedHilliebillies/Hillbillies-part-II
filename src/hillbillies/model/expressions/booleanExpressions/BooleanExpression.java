package hillbillies.model.expressions.booleanExpressions;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;

public abstract class BooleanExpression extends Expression {

	public BooleanExpression() {
		
	}
	
	@Override
	public abstract BooleanValueExpression eval();
	
}
