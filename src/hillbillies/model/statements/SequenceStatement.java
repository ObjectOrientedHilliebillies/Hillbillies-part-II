package hillbillies.model.statements;

import java.util.List;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
		setStatements(statements);
	}
	
	private void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	private List<Statement> statements;
	

	@Override
	public double execute(Task task) {
		return defaultExecutionTime;
	}
	
	@Override
	public List<Statement> result() {
		return this.statements;
	}
	
	@Override
	public void setTask(Task task) {
		for(Statement statement : statements) {
			statement.setTask(task);
		}
	}

}
