package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.Task;
import hillbillies.model.expressions.booleanExpressions.BooleanExpression;

public class OrExpression extends LogicalExpression{
	public OrExpression(BooleanExpression e1, BooleanExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	private BooleanExpression e1;

	private BooleanExpression e2;
	
	@Override
	public Boolean getValue(Task task) {
		return e1.getValue(task) || e2.getValue(task);
	}
}
