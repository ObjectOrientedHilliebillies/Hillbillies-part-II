package hillbillies.model;

import java.util.List;

import hillbillies.model.statements.Statement;

public class SubTask {
	
	private Statement statement;
	private Cube cube;
	private Task task;
	private boolean inLoop;
	private boolean isLoop;
	

	public SubTask(Statement statement, Cube cube, Task task, boolean inLoop){
		this.statement = statement;
		this.cube = cube;
		this.task = task;
		this.inLoop = inLoop;
		System.out.println(statement);
		System.out.println(task);
		this.statement.setTask(task); //TODO Nakijken
	}
	
	private List<Statement> subStatements = null;
	private int index = 0;	
	private SubTask subTask = null;
	private double remainingTime;

	public double advance(double time){
		
		remainingTime = time;
		
		if (mustEvaluate){
			evaluationOfStatement();
			
			// If there are no substatements we're done here.
			if (!hasSubStatements){
				return remainingTime;
			}
		}
		
		if (hasSubStatements){}
		
		if (subTask == null){
			subTask = new SubTask(subStatements.get(index), cube, task, inLoop);
		}
	
		while (true){
			
			// Only execute a statement of the sequence if there is 
			// still time left. Else return -1 because this sequence is not finished yet.
			if (remainingTime <= 0){
				return -1;
			}
			
			remainingTime = subTask.advance(remainingTime);
			
			// If the statement we just executed was not able to finish we could continue
			// here next time.
			if (remainingTime == -1){
				return -1;
			}

			// Index is only incremented if the previous statement was able to finish.
			index = index+1;
			
			// If the processed statement was last in the sequence we return all is done here.
			if (index == subStatements.size()){
				return remainingTime;
			}

			// If the sequence in not completed yet, load the next substatement.
			subTask = new SubTask(subStatements.get(index), cube, task, inLoop);
		}	
	}
	

	private void decreaceRemainingTime(double amount){
		remainingTime = remainingTime - amount;
		
		// If the remaining time is 0 or less, we were just able to finish the execution of the
		// statement, thus 0 should be returned.
		if (remainingTime <= 0){
			remainingTime = 0;
		}
	}
	
	private boolean mustEvaluate = true;
	private boolean hasSubStatements;
	
	private void evaluationOfStatement(){
		// Execute the statement.
		//statement.execute(task);
		double executionTime = statement.execute(); //TODO nakijken 2
		
		// If the statement lasts -2 it's effects last longer than one tick. (follow, goto, work,..)
		if (executionTime == -2){
			remainingTime = 0;
		}
		
		// If the statement returns -1 the statement took longer than one tick. (loop)
		if (executionTime == -1){
			remainingTime = -1;
			return;
		}
			
		// If the statement returns 0 it a break.
		decreaceRemainingTime(statement.executionTime());
		subStatements = statement.result();
		
		// Set the substatements and check if there are any.
		subStatements = statement.result();
		hasSubStatements = (subStatements != null);

		this.mustEvaluate = false;
	}
}