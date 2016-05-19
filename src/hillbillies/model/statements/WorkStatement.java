package hillbillies.model.statements;

import hillbillies.model.Cube;
<<<<<<< HEAD
import hillbillies.model.Task;
import hillbillies.model.Unit;
=======
>>>>>>> refs/remotes/origin/Victor
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
		//if (!unit.isCarryingBoulder() && !unit.isCarryingLog() && cube.isPassable()) {
		//	return 0;
		//}
		if (!(cube.isNeighbourCube(unit.getCube()) || cube.equals(unit.getCube()))){
			taskFailed(task);
			return -1;
		}
		unit.workAt(cube);
		return -1;
	}

}
