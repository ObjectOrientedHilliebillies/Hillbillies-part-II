package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;

public class IsFriendExpression extends unitIsExpression {

	public IsFriendExpression(UnitExpression other) {
		super(other);
		this.other = other;	
	}
	
	private UnitExpression other;
	
	@Override
	public Boolean getValue(Task task) {
		Unit unit = this.other.getValue(task);
		return (task.getExecutor().getFaction() == unit.getFaction());
	}
}
