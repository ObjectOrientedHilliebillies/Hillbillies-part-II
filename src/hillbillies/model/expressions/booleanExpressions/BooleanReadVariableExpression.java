package hillbillies.model.expressions.booleanExpressions;

import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class BooleanReadVariableExpression extends BooleanExpression{
	public BooleanReadVariableExpression(String variableName, SourceLocation sourceLocation)  {
		this.variableName = variableName;
	}
	
	private String variableName;
	
	@Override
	public Boolean getValue(Task task) {
		return (Boolean) task.getValue(variableName);
	}
}
