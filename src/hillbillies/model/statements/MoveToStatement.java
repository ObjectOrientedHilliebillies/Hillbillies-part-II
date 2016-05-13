package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.Cube;
import hillbillies.model.Task;

public class MoveToStatement extends ActivityStatement{
	//TODO position een Cube maken
	public MoveToStatement(Expression<Cube> position) {
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public void execute() {
		Cube cube = position.getValue();
		this.getTask().getExecutor().moveTo(cube);
	}
	
	
}
