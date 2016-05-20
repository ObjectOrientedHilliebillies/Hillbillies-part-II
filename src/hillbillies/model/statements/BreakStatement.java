package hillbillies.model.statements;

import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class BreakStatement extends Statement{

	public BreakStatement(SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
	}

	@Override
	public double execute(Task task) {
		return breakStatement;
	}

}
