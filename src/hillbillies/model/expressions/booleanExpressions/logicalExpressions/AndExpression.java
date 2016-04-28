package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;

public class AndExpression extends BooleanExpression{

	public AndExpression(BooleanExpression left, BooleanExpression right) {
		this.left = left;
		this.right = right;
	}
	
	private BooleanExpression left;
	private BooleanExpression right;

	
	@Override
	public BooleanValueExpression eval() {
		if (left.eval() instanceof TrueExpression && right.eval() instanceof TrueExpression)
			return new TrueExpression();
		return new FalseExpression();
	}

}
