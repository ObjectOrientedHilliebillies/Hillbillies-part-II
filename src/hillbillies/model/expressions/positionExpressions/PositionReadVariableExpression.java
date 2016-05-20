package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class PositionReadVariableExpression extends CubeExpression<String> {
	public PositionReadVariableExpression(String variableName, SourceLocation sourceLocation)  {
		setPosition(variableName);
	}
	
	
	@Override
	public Cube getValue(Task task) {
		return (Cube) task.getValue(getPosition());
	}
}
