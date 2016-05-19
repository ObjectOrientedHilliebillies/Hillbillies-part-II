package hillbillies.model.expressions.booleanExpressions.booleanValueExpressions;

import hillbillies.model.Task;

public class FalseExpression extends BooleanValueExpression {
	public FalseExpression(){
		setValue(false);
	}

	@Override
	public Boolean getValue(Task task) {
		return false;
	}

}