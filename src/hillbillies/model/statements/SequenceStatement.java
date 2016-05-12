package hillbillies.model.statements;

import java.lang.Thread.State;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.range_return;

import hillbillies.model.Task;

public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements) {
		setStatements(statements);
	}
	
	private void setStatements(List<Statement> statements) {
		this.statements = statements;
	}
	
	@Override
	public List<Statement> getAsList() {
		return this.statements;
	}
	
	private List<Statement> statements;
	
	@Override
	public void execute(Task task) {
		task.setActivitys(statements);
	}

}
