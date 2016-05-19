package hillbillies.model.expressions.booleanExpressions.logicalExpressions;

import hillbillies.model.Task;
import hillbillies.model.expressions.booleanExpressions.BooleanExpression;

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
