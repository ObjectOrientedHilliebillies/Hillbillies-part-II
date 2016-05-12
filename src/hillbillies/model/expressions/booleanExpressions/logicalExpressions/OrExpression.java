package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;

public class OrExpression extends LogicalExpression{
	public OrExpression(BooleanExpression e1, BooleanExpression e2) {
		setValue(e1.getValue() || e2.getValue());
	}	
}
