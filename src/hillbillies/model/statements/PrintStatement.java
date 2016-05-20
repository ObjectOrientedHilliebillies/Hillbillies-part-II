package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.task.Task;

public class PrintStatement extends Statement{

	public PrintStatement(Expression<?> value) {
		setValue(value);
	}
	
	private void setValue(Expression<?> value) {
		this.value = value;
	}
	
	private Expression<?> getValue() {
		return this.value;
	}
	
	private Expression<?> value;
	
	@Override
	public double execute(Task task) {
		System.out.print(this.getValue().toString());
		return defaultExecutionTime;
	}

}
