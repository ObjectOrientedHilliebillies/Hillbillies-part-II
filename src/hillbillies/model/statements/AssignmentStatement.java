package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;

public class AssignmentStatement extends Statement{

	public AssignmentStatement(String variableName, Expression<?> value) {
		
	}
	
	private void setValue(Expression value) {
		this.value = value;
	}
	
	private Expression getValue() {
		return this.value;
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
	public void execute() {
		getVariableName() = getValue;
		//FIXME 
	}

}
