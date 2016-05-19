package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class HerePosition extends CubeExpression {

	public HerePosition(SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
	}
	
	@Override
	public Cube getValue(Task task) {
		return task.getExecutor().getCube();
	}

}
