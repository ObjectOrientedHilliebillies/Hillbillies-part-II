package hillbillies.model.statements;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public class AttackStatement extends ActivityStatement{

	public AttackStatement(Expression<Unit> unit) {
		setUnit(unit);
	}
	
	private void setUnit(Expression<Unit> unit) {
		this.unit = unit.getValue();
	}
	
	private Unit getUnit() {
		return this.unit;
	}
	
	private Unit unit;
	
	@Override
	public double execute() {
		this.getTask().getExecutor().attack(this.getUnit());
		return -2;
	}
}
