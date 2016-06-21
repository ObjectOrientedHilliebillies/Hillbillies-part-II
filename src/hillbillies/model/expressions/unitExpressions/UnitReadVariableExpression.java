package hillbillies.model.expressions.unitExpressions;

import hillbillies.model.Unit;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class UnitReadVariableExpression extends UnitExpression{

	public UnitReadVariableExpression(String variableName, SourceLocation sourceLocation)  {
		this.variableName = variableName;
	}
	
	private String variableName;

	
	@Override
	public Unit getValue(Task task) {
		// return (Unit) ((UnitExpression) task.getValue(variableName)).getValue(task);
		return (Unit) task.getValue(variableName); 
	}
}
