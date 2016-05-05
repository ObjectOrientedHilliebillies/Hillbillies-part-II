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
	public void execute(Task task) { //FIXME Hoe geraakt ge juist aan uw task?
		Cube cube = position.eval();
		task.getExecutor().moveTo(cube);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	
}
