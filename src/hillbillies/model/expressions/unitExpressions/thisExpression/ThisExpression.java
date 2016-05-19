package hillbillies.model.expressions.unitExpressions.thisExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class ThisExpression extends UnitExpression {

	public ThisExpression(SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
	}
	
	@Override
	public Unit getValue(Task task) {
		return task.getExecutor();
	}
	

}
