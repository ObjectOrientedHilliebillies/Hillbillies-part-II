package hillbillies.model.statements;

import java.util.List;

import hillbillies.model.task.Task;
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
	public boolean doYouHaveABreak() {
		for (Statement statement:statements){
			if (statement.doYouHaveABreak()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double execute(Task task) {
		return defaultExecutionTime;
	}
	
	@Override
	public List<Statement> result() {
		return this.statements;
	}

}
