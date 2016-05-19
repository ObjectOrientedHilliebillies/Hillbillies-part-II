package hillbillies.model.statements;


import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.IRObject;

import hillbillies.model.expressions.Expression;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class IfStatement extends Statement{
	
	public IfStatement(Expression<Boolean> condition, Statement ifBody
			,Statement elseBody, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}

	private Expression<Boolean> condition;
	private Statement ifBody;
	private Statement elseBody;
	private Statement resultBody;
	

	@Override
	public double execute(Task task) {
		if (condition.getValue(task)) {
			resultBody = ifBody;
		}
		else {
			resultBody = elseBody;
		}
		return defaultExecutionTime;		
	}
	
	@Override
	public List<Statement> result() {
		if (resultBody == null){
			return null;
		}
		List<Statement> returnList = new ArrayList<>(); 
		returnList.add(resultBody);
		return returnList;
	}	
}
