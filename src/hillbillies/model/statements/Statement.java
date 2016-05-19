package hillbillies.model.statements;

import java.util.List;

import javax.swing.table.TableStringConverter;

import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {
	
	public abstract double execute(Task task);

	public List<Statement> result(){
		return null;
	}	
	
	public final static double defaultExecutionTime = 0.001;

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

