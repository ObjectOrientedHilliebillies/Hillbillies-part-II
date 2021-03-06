package hillbillies.model.statements;

import java.util.List;

import hillbillies.model.Cube;
import hillbillies.model.Pathfinding;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.model.task.Task;
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
		List<Cube> path = Pathfinding.getPath(task.getExecutor().getCube(), 
				followTheLeaderLeader.getCube(), followTheLeaderLeader.getWorld());
		if (path == null){
			taskFailed(task);
			return repeatingLong;
		}
		if (!(path.size() == 1)){
			task.getExecutor().moveToAdjacent(path.get(path.size() - 2)); 
		}
		return repeatingLong;
	}
}
