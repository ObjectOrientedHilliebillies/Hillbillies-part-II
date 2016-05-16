package hillbillies.model.statements;

import java.util.List;

import hillbillies.model.Task;

public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements) {
		setStatements(statements);
	}
	
	private void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	private List<Statement> statements;
	

	@Override
	public void execute() {}
	
	@Override
	public List<Statement> result() {
		return this.statements;
	}
	
	@Override
	private 
	
	@Override
	public void setTask(Task task) {
		for(Statement statement : statements) {
			
		}
	}

}
