package hillbillies.model.statements;

import hillbillies.model.Task;

public abstract class ActivityStatement extends Statement{

	public ActivityStatement() {
		// TODO Auto-generated constructor stub
	}

	public abstract void execute(Task task);

}
