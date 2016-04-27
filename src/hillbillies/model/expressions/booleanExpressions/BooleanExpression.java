package hillbillies.model.expressions.booleanExpressions;

import hillbillies.model.expressions.Expression;

public abstract class BooleanExpression extends Expression {

	public BooleanExpression() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public abstract BooleanExpression returns();

}
