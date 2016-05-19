package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

import java.util.List;

import hillbillies.model.Cube;
<<<<<<< HEAD
import hillbillies.model.Pathfinding;
import hillbillies.model.Task;
=======
>>>>>>> refs/remotes/origin/Victor
import hillbillies.model.Unit;

public class MoveToStatement extends ActivityStatement{
	public MoveToStatement(CubeExpression<?> position, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public double execute(Task task) {
		Cube cube = position.getValue(task);
		List<Cube> path = Pathfinding.getPath(task.getExecutor().getCube(), 
				cube, cube.getWorld());
		if (path == null){
			task.taskFailed();
			return -1;
		}
		Unit unit = task.getExecutor();
		if (unit.getCube().equals(cube)){
			System.out.println("MoveToStatement: "+ "De unit staat er al");
			return 0;
		}
		unit.moveTo(path.get(0));
		return -1;
	}	
}
