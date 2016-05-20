package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.task.Task;

public class AndExpression extends LogicalExpression{
	public AndExpression(BooleanExpression e1, BooleanExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	private BooleanExpression e1;

	private BooleanExpression e2;
	
	@Override
	public Boolean getValue(Task task) {
		return e1.getValue(task) && e2.getValue(task);
	}
}
