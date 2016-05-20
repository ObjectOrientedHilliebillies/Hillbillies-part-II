package hillbillies.model.statements;

import java.util.List;
import hillbillies.model.task.SubTask;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {
	
	public final static double breakStatement = SubTask.finishedOnBreak;
	public final static double repeatingLong = SubTask.hasToBeExecutedAgain;
	public final static double singularLong = SubTask.singularLong;
	public final static double doWhileLoop = SubTask.startNewWhileLoop;
	public final static double defaultExecutionTime = 0.001;
	
	public abstract double execute(Task task);

	public List<Statement> result(){
		return null;
	}	
	
	

	private SourceLocation sourceLocation;
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	public void taskFailed(Task task){
		task.taskFailed();
	}
}

