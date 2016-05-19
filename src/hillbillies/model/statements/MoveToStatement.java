package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Cube;
import hillbillies.model.Task;
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
		if (cube == null){
			task.taskFailed();
			return -1;
		}
		Unit unit = task.getExecutor();
		if (unit.getCube().equals(cube)){
			System.out.println("MoveToStatement: "+ "De unit staat er al");
			return 0;
		}
		unit.moveTo(cube);
		return -1;
	}	
}
