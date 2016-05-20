package hillbillies.model.statements;


import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.model.task.Task;
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
	public double execute(Task task) {
		Cube cube = getPosition().getValue(task);
		Unit unit = task.getExecutor();
		
		if (!(cube.isNeighbourCube(unit.getCube()) || cube.equals(unit.getCube()))){
			taskFailed(task);
			return repeatingLong;
		}
		unit.workAt(cube);
		System.out.println(cube.toString());
		return singularLong;
	}

}
