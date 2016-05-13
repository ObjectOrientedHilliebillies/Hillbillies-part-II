package hillbillies.model.statements;

import hillbillies.model.Task;
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
	public void execute() {
		this.getTask().getExecutor().follow(this.getUnit());
		
	}

}
