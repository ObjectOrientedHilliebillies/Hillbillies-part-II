package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.Cube;
import hillbillies.model.Task;

public class MoveToStatement extends ActivityStatement{
	//TODO position een Cube maken
	public MoveToStatement(CubeExpression position) {
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public void execute() {
		Cube cube = position.getValue();
		this.getTask().getExecutor().moveTo(cube);
	}
	
	@Override
	public double executionTime() {
		return 0;
	}
	
}
