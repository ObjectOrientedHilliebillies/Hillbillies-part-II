package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class FollowStatement extends ActivityStatement{
	
	public FollowStatement(Expression<Unit> unit, SourceLocation sourceLocation) {
		setUnit(unit);
		setSourceLocation(sourceLocation);
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
