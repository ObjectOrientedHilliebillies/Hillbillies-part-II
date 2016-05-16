package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public class FollowStatement extends ActivityStatement{
	
	public FollowStatement(Expression<Unit> unit) {
		setUnit(unit);
	}
	
	private void setUnit(Expression<Unit> unit) {
		this.unit = unit.getValue();
	}
	
	private Unit getUnit() {
		return this.unit;
	}
	
	Unit unit;
	
	@Override
	public double execute() {
		this.getTask().getExecutor().follow(this.getUnit());
		return -1;
	}

}
