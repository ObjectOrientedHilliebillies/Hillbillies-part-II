package hillbillies.model.statements;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class FollowStatement extends ActivityStatement{
	
	public FollowStatement(Expression<Unit> unit, SourceLocation sourceLocation) {
		setUnit(unit);
		setSourceLocation(sourceLocation);
	}
	
	private void setUnit(Expression<Unit> unit) {
		this.unit = unit;
	}
	
	private Expression<Unit> getUnit() {
		return this.unit;
	}
	
	Expression<Unit> unit;
	
	@Override
	public double execute(Task task) {
		Unit followTheLeaderLeader = this.getUnit().getValue(task); //Follow me!
		if (followTheLeaderLeader == null){
			return 0;
		}
		task.getExecutor().follow(followTheLeaderLeader);
		return -1;
	}

}
