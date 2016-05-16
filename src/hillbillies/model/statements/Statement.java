package hillbillies.model.statements;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.Task;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {
	
	public abstract double execute();
	
	public List<Statement> getAsList(){
		List<Statement> list = new ArrayList<>();
		list.add(this);
		return list;
	}
	
	public List<Statement> result(){
		return null;
	}	
	
	public double executionTime(){
		return defaultExecutionTime;
	}
	
	private final static double defaultExecutionTime = 0.001;

	public void setTask(Task task) {
		this.task = task;
	}

	private Task task;
	
	public Task getTask() {
		return this.task;
	}
}

