package hillbillies.model.expressions;

import hillbillies.model.expressions.isExpressions.IsAliveExpression;
import hillbillies.model.expressions.isExpressions.IsCarryingItemExpression;
import hillbillies.model.expressions.logicalExpressions.AndExpression;

/**
 * Class for representing expressions
 * 
 * @author Jonas
 *
 */
public abstract class Expression {

	public Expression() {
	}
	
	public abstract Expression eval();

}