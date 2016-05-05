package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;

public class IfStatement extends Statement{
	
	public IfStatement(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public IfStatement(Expression<Boolean> condition, Statement ifBody, Statement elseBody, 
			SourceLocation sourceLocation){}

	private Expression<Boolean> condition;
	private Statement ifBody;
	private Statement elseBody;
	
	@Override
	public void execute() {
		if (condition.eval()) {
			ifBody.execute();
		}
		else {
			elseBody.execute();
		}
		
	}
	
}
