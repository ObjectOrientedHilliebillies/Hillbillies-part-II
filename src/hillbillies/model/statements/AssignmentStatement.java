package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class AssignmentStatement extends Statement{

	public AssignmentStatement(String variableName, Expression<?> value, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
		setVariableName(variableName);
		setValue(value);
	}
	
	private void setValue(Expression value) {
		this.value = value;
	}
	
	private void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	private String getVariableName() {
		return this.variableName;
	}
	
	private Expression value;
	private String variableName;
	
	@Override
	public double execute() {
		System.out.println(this.getTask());
		System.out.println(this.variableName.toString());
		System.out.println(this.value.toString());
		System.out.println(this.value.getStatement());
		this.getTask().setVariable(this.variableName, this.value);
		return defaultExecutionTime;
	}

}
