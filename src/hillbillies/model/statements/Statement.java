package hillbillies.model.statements;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {
	
	public abstract double execute(Task task);
	
	public List<Statement> getAsList(){
		List<Statement> list = new ArrayList<>();
		list.add(this);
		return list;
	}
	
	public List<Statement> result(){
		return null;
	}	
	
	public final static double defaultExecutionTime = 0.001;

	public void setTask(Task task) {
		this.task = task;
	}

	private Task task;
	
	public Task getTask() {
		return this.task;
	}

	private SourceLocation sourceLocation;
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
}

