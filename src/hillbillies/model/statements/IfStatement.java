package hillbillies.model.statements;


import java.util.ArrayList;
import java.util.List;

import hillbillies.model.expressions.Expression;

public class IfStatement extends Statement{
	
	public IfStatement(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}

	private Expression<Boolean> condition;
	private Statement ifBody;
	private Statement elseBody;
	private Statement resultBody;
	

	@Override
	public double execute() {
		if (condition.getValue()) {
			resultBody = ifBody;
		}
		else {
			resultBody = elseBody;
		}
		return defaultExecutionTime;		
	}
	
	@Override
	public List<Statement> result() {
		List<Statement> returnList = new ArrayList<>(); 
		returnList.add(resultBody);
		return returnList;
	}	
}
