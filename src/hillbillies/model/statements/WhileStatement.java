package hillbillies.model.statements;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.task.Task;

public class WhileStatement extends Statement{

	public WhileStatement(BooleanExpression condition, Statement body) {
		setCondition(condition);
		setBody(body);
	}
	
	private void setBody(Statement body) {
		this.body = body;
	}
	
	public Statement getBody() {
		return this.body;
	}
	
	private void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}
	
	private BooleanExpression getCondition() {
		return this.condition;
	}
	
	private BooleanExpression condition;
	private Statement body;
	
	@Override
	public double execute(Task task) {
		if (getCondition().getValue(task)){
			return -2;
		}	
		return 0;
	}
}
