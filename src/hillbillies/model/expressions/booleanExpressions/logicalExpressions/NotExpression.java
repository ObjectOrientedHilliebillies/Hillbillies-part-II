package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;

public class NotExpression extends BooleanExpression{
	public NotExpression(BooleanExpression e){
		setValue(!e.getValue());
	}
}
