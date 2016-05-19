package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;

public class AnyExpression extends UnitExpression{

	public AnyExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		return task.getExecutor().getWorld().getUnits().stream().findAny().orElse(null);
	}
	
}
