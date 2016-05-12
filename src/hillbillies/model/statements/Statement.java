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
	
	public void execute(Task task){
		this.parentTask = task;
	}
	
	public List<Statement> getAsList(){
		List<Statement> list = new ArrayList<>();
		list.add(this);
		return list;
	}
	
	private Task parentTask;
}

