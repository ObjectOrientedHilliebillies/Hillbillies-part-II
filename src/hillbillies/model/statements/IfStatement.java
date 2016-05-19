package hillbillies.model.statements;


import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.IRObject;

import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;
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
	public double execute() {
		if (condition.getValue()) {
			resultBody = ifBody;
//			ifBody.setTask(task);
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
		
	private Task task;
	
	@Override
	public Task getTask() {
		System.out.println(this.task);
		return this.task;
	};
	
	@Override 
	public void setTask(Task task){
		this.task = task;
		if (ifBody != null){
			ifBody.setTask(task);
		}
		if (elseBody != null){
			elseBody.setTask(task);
		}
		condition.setStatement(this);
	}
}
