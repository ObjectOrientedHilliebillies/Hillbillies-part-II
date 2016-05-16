package hillbillies.model.statements;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;

public class WorkStatement extends ActivityStatement{
	public WorkStatement(Expression<Cube> position) {
		setPosition(position);
	}
	
	private void setPosition(Expression<Cube> position) {
		this.position = position;
	}
	
	private Expression<Cube> position;
	
	private Expression<Cube> getPosition() {
		return this.position;
	}
	
	@Override
	public double execute() {
		Cube cube = getPosition().getValue();
		this.getTask().getExecutor().workAt(cube);
		return -2;
	}

}
