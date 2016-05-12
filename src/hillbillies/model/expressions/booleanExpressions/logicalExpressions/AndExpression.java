package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;

public class AndExpression extends LogicalExpression{
	public AndExpression(BooleanExpression e1, BooleanExpression e2) {
		setValue(e1.getValue() && e2.getValue());
	}
}
