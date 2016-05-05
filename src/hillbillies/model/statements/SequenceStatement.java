package hillbillies.model.statements;

import java.util.List;

public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements) {
		setStatements(statements);
	}
	
	private void setStatements(List<Statement> statements) {
		this.statements = statements;
	}
	
	private List<Statement> getStatements() {
		return this.statements;
	}
	
	private List<Statement> statements;
	
	@Override
	public void execute() {
		for (Statement statement:getStatements()) {
			statement.execute();
		}
		
	}

}
