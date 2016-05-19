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
<<<<<<< HEAD
	public double execute() {
		System.out.println(this.position.toString());
		Cube cube = getPosition().getValue();
		this.getTask().getExecutor().workAt(cube);
=======
	public double execute(Task task) {
		Cube cube = getPosition().getValue(task);
		task.getExecutor().workAt(cube);
>>>>>>> refs/remotes/origin/master
		return -2;
	}

}
