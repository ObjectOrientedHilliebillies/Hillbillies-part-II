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
			System.out.println("FOLLOW FAILED");
			return -1;
		}
		System.out.println(path.get(0).toString());
		task.getExecutor().moveTo(path.get(0));
		return -1;
	}

}
