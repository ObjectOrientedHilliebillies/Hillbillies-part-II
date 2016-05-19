package hillbillies.model.statements;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class WorkStatement extends ActivityStatement{
	public WorkStatement(Expression<Cube> position, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
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
		System.out.println(this.position.toString());
		Cube cube = getPosition().getValue();
		this.getTask().getExecutor().workAt(cube);
		return -2;
	}

}
