package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class FriendExpression extends UnitExpression {

	public FriendExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		return task.getExecutor().getWorld().getUnits().stream()
				.filter(i -> i.getFaction() 
						== task.getExecutor().getFaction())
				.findAny().orElse(null);
	}

}
