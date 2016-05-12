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
	
	public double advance(double timeLeft){
		if (isSequence){
			if (subTask == null){
				subTask = new SubTask(statements.get(0), cube, task);
			}
			while (true){
				timeLeft = subTask.advance(timeLeft);
				if (timeLeft == notFinished){
					return notFinished;
				}
				if (timeLeft == finishedWithNoTimeLeft){
					if (index == statements.size()-1){
						// Each steatement done.
						return finishedWithNoTimeLeft;
					}
					return notFinished;
				}
								
				index++;
				if (index == statements.size()){
					// Each steatement done.
					return timeLeft;
				}
				SubTask subTask = new SubTask(statements.get(index), cube, task);
			}	
			
				
				
		}else{
			statement.execute(task);
			timeLeft = timeLeft-0.001;
			if (timeLeft <= 0){
				return finishedWithNoTimeLeft;
			}
			return timeLeft;
		}
	}
	
	private final static int finishedWithNoTimeLeft = 0;
	private final static int notFinished = -1;
	private Statement statement;
	private Cube cube;
	private double consumedTime;
	private List<Statement> statements;
	private boolean isSequence = false;
	private Task task;
	private int index = 0;	
	private SubTask subTask = null;
}
