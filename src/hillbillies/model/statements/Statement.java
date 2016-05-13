package hillbillies.model.statements;

import java.util.List;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {
	
	public abstract void execute();
	
	public List<Statement> result(){
		return null;
	}
	
	public double executionTime(){
		return defaultExecutionTime;
	}
	
	
	
	private final static double defaultExecutionTime = 0.001;
	
}

