package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;

public class AssignmentStatement extends Statement{

	public AssignmentStatement(String variableName, Expression<?> value) {
		
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
		getVariableName() = getValue;
		return defaultExecutionTime;
	}

}
