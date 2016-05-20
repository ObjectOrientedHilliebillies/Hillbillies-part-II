package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

import java.util.List;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import hillbillies.model.Cube;
import hillbillies.model.Pathfinding;
import hillbillies.model.Unit;

public class MoveToStatement extends ActivityStatement{
	public MoveToStatement(CubeExpression<?> position, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public double execute(Task task) {
		System.out.println("execute moveTo");
		Cube cube = position.getValue(task);
		if (cube == null) {
			task.taskFailed();
			return repeatingLong;
		}
		
		Unit unit = task.getExecutor();
		if (unit.getCube().equals(cube)){
			return 0;
		}
		
		List<Cube> path = Pathfinding.getPath(task.getExecutor().getCube(), 
				cube, cube.getWorld());
		
		if (path == null){
			task.taskFailed();
			return repeatingLong;
		}
		
		unit.moveToAdjacent(path.get(path.size()-2)); 

		return repeatingLong;
	}	
}
