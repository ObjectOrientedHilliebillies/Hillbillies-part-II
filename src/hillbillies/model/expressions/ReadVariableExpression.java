package hillbillies.model.expressions;

import hillbillies.part3.programs.SourceLocation;

public class ReadVariableExpression<T> extends Expression<Object> {

	public ReadVariableExpression(String variableName, SourceLocation sourceLocation)  {
		this.variableName = variableName;
	}
	
	private String variableName;
	
	@Override
	public Object getValue() {
		return this.getStatement().getTask().getValue(variableName);
	}

}
