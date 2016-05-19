package hillbillies.model.statements;

import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;

public class WhileStatement extends Statement{

	public WhileStatement(Expression<Boolean> condition, Statement body) {
		setCondition(condition);
		setBody(body);
	}
	
	private void setBody(Statement body) {
		this.body = body;
	}
	
	private Statement getBody() {
		return this.body;
	}
	
	private void setCondition(Expression<Boolean> condition) {
		this.condition = condition;
	}
	
	private Expression<Boolean> getCondition() {
		return this.condition;
	}
	
	private Expression<Boolean> condition;
	private Statement body;
	
	@Override
	public double execute(Task task) {
		while (this.getCondition().getValue(task)) {
			this.getBody().execute(task);
		}
		return 0;
		
	}

}
