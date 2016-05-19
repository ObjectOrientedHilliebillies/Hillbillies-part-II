package hillbillies.model.statements;

import java.util.List;

import hillbillies.model.Cube;
import hillbillies.model.Pathfinding;
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
		Unit follower = this.getUnit().getValue(task);
		List<Cube> path = Pathfinding.getPath(task.getExecutor().getCube(), 
				follower.getCube(), follower.getWorld());
		task.getExecutor().moveTo(path.get(0));
		return -1;
	}

}
