package hillbillies.model;

import java.util.List;
import hillbillies.model.statements.SequenceStatement;
import hillbillies.model.statements.Statement;

public class SubTask {
	public SubTask(Statement statement, Cube cube, Task task){
		this.statement = statement;
		this.cube = cube;
		this.task = task;
		if (this.statement instanceof SequenceStatement) {
			statements= statement.getAsList();
			isSequence = true;
		}
	}
	
	public boolean advance(double consumedTime){
		if (isSequence){
			for (Statement subStatement : statements){
				SubTask subTask = new SubTask(subStatement, cube, task);
				subTask.advance(consumedTime);
			}
		}else{
			// TODO de tijd nog controleren.
			statement.execute(task);
		}
	}
	
	private Statement statement;
	private Cube cube;
	private double consumedTime;
	private List<Statement> statements;
	private boolean isSequence = false;
	private Task task;
	
}
