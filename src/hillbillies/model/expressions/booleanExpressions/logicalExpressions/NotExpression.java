package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.task.Task;

public class NotExpression extends BooleanExpression{
	public NotExpression(BooleanExpression e){
		this.e = e;
	}
	
	private BooleanExpression e;

	@Override
	public Boolean getValue(Task task) {
		return (!e.getValue(task));
	}
	
	
}
