package hillbillies.model.task;

import java.util.List;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import hillbillies.model.Cube;
import hillbillies.model.statements.Statement;
import hillbillies.model.statements.WhileStatement;

public class SubTask {
	
	private Statement statement;
	private Cube cube;
	private boolean inLoop;
	private Task task;
	
	public SubTask(Statement statement, Cube cube, Task task, boolean inLoop){
		this.statement = statement;
		this.cube = cube;
		this.inLoop = inLoop;
		this.task = task;
	}
	
	private List<Statement> subStatements = null;
	private int index = 0;	
	private SubTask subTask = null;
	private double remainingTime;

	// If the given remaining time is smaller than -1 than this subtask is in a while loop.
	public double advance(double time){
		remainingTime = time;
		
		if (mustEvaluate){
			evaluationOfStatement();
		}
		
		// If the statement has a substatement (sequence, if)
		if (hasSubStatements){
			if (subTask == null){
				subTask = new SubTask(subStatements.get(index), cube, task, inLoop);
			}
		
			while (true){
				// If remainingTime is zero there is no time left
				// return -1 because this sequence is not finished yet.
				if (remainingTime == 0 || remainingTime == -1){
					return -1;
				}
				
				if (remainingTime == -4) {
					return -4;
				}
				
				
				remainingTime = subTask.advance(remainingTime);
				
				// If the statement we just executed was not able to finish we could continue
				// here next time.
				if (remainingTime == -1){
					return -1;
				}
				
				if (remainingTime == -4){
					return -4;
				}
	
				// Index is only incremented if the previous statement was able to finish.
				index = index+1;
				
				// If the processed statement was last in the sequence we return all is done here.
				if (index == subStatements.size()){
					return remainingTime;
				}
				
				System.out.println(subStatements);
				System.out.println(index);
				// If the sequence in not completed yet, load the next substatement.
				try {
					subTask = new SubTask(subStatements.get(index), cube, task, inLoop);
			
				}catch (IndexOutOfBoundsException e){
					task.taskFailed();
				}
			}
		}
		
		// If the statement is a while.
		if (subWhileTask != null) {
			remainingTime = subWhileTask.doWhile(remainingTime);
		}
		return remainingTime;
	}

	private boolean mustEvaluate = true;
	private boolean hasSubStatements;
	private SubWhileTask subWhileTask;
	
	private void evaluationOfStatement(){
//		System.out.println(statement.getSourceLocation().toString());

		// Execute the statement.
		double executionTime = statement.execute(task);
		
		if (executionTime == -4){
			remainingTime = -4;
			return;
		}
		
		// If the statement returns -3 it took longer than 1 tick but
		// it mustn't be executed again.
		if (executionTime == -3){
			remainingTime = 0;
			return;
		}
		
		// If the statement lasts -2 it is a while that does one loop.
		if (executionTime == -2){
			subWhileTask = new SubWhileTask((WhileStatement) statement, cube, task);
			mustEvaluate = false;
			return;
		}
		
		// If the statement returns -1 the statement took longer 
		// than one tick AND should be executed again. (follow, got, work)
		if (executionTime == -1){
			remainingTime = -1;
			return;
		}
			
		// If the statement returns it took no time. (example goto where you are)
			// No special treatment necessary.
		
		decreaceRemainingTime(executionTime);
		subStatements = statement.result();
		
		// Set the substatements and check if there are any.
		hasSubStatements = (subStatements != null);

		this.mustEvaluate = false;
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