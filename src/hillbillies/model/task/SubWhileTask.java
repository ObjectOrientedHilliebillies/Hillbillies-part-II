package hillbillies.model.task;

import hillbillies.model.Cube;
import hillbillies.model.statements.Statement;
import hillbillies.model.statements.WhileStatement;

public class SubWhileTask {
	
	private WhileStatement whileStatement;
	private Cube cube;
	private Task task;
	private Statement whileBody;

	public SubWhileTask(WhileStatement whileStatement, Cube cube, Task task){
		this.whileStatement = whileStatement;
		this.whileBody = whileStatement.getBody();
		this.cube = cube;
		this.task = task;
	}
	
	private SubTask subTask = null;
	private double remainingTime;
	
	public double doWhile(double remainingTime){
		// Set the remaining time.
		this.remainingTime = remainingTime;
		
		// Do this until there is no remaining time left.
		while (remainingTime != 0){
			
			// Do this if the previous iteration has completely finished.
			if (subTask == null){
				
				// If the condition is violated the while is terminated and 
				// the remaining time is returned.
				if (whileStatement.execute(task) != -2){
					return remainingTime;
				}
				
				// If the condition is met we create a subtask that will 
				// present the while body.
				subTask = new SubTask(whileBody, cube, task, true);
			}
			
			// Do the while body and if so return that this while body could not finish.
			// The remaining time is -2 because only 0 to -1 indicate there is no more time.
			if (-1 == subTask.advance(-2)){
				return -1;
			}
			
			// If the while body was able to finish the condition has to be met again.
			subTask = null;
			
			// This iteration of the while loop decreased the remaining time.
			decreaceRemainingTime(Statement.defaultExecutionTime);
			}
		
		return -1;
	}
	
	private void decreaceRemainingTime(double amount){
		if (remainingTime < -1){
			return;
		}
		remainingTime = remainingTime - amount;
		
		// If the remaining time is 0 or less, we were just able to finish the execution of the
		// statement, thus 0 should be returned.
		if (remainingTime <= 0){
			remainingTime = 0;
		}
	}
}