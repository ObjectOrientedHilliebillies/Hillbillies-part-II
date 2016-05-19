package hillbillies.model.statements;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public class AttackStatement extends ActivityStatement{

	public AttackStatement(Expression<Unit> unit) {
		setUnit(unit);
	}
	
	private void setUnit(Expression<Unit> unit) {
		this.unit = unit;
	}
	
	private Expression<Unit> getUnit() {
		return this.unit;
	}
	
	private Expression<Unit> unit;
	
	@Override
	public double execute(Task task) {
		Unit defender = this.getUnit().getValue(task);
		task.getExecutor().attack(defender);
		return -2;
	}
}
