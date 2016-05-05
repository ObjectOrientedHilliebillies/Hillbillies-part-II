package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.Cube;
import hillbillies.model.Task;

public class MoveToStatement extends ActivityStatement{

	public MoveToStatement(Expression<Cube> position) {
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public void execute(Task task) { //FIXME Hoe geraakt ge juist aan uw task?
		task.getExecutor().moveTo(position);
	}

	@Override
	public void execute() {	
	}
	
	
}
