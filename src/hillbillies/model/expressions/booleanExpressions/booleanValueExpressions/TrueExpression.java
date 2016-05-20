package hillbillies.model.expressions.booleanExpressions.booleanValueExpressions;

import hillbillies.model.task.Task;

public class TrueExpression extends BooleanValueExpression{
	
	public TrueExpression(){
	}

	@Override
	public Boolean getValue(Task task) {
		return true;
	}
}
