package hillbillies.model;

import java.util.List;

import org.antlr.v4.parse.ANTLRParser.ruleReturns_return;

import hillbillies.model.statements.SequenceStatement;
import hillbillies.model.statements.Statement;

public class SubTask {
	
	private Statement statement;
	private Cube cube;
	private List<Statement> subStatements = null;
	private Task task;
	private int index = 0;	
	private SubTask subTask = null;
	private double remainingTime;
	private boolean firstEvaluation = true;
	private boolean hasSubStatements;
	private boolean stillTimeLeft;
	private boolean isFinished;
	private double returnTime;
	private boolean stillThingToDo;
	private boolean needToReturn = false;
	
	private final static int finishedWithNoTimeLeft = 0;
	private final static int notFinished = -1;
	
	public SubTask(Statement statement, Cube cube, Task task){
		this.statement = statement;
		this.cube = cube;
		this.task = task;
	}

	public double advance(double time){
		
		remainingTime = time;
		stillTimeLeft = true;
		
		if (firstEvaluation){
			firstEvaluationOfStatement();
			
			// als dit alles is gewoon terugkeren
			returnCheck();
			if (needToReturn){
				return returnTime;
			}
		}
		
		if (subTask == null){
			setSubTaksOfSubStatement(index);
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

			// Only increment the index if the precessing statement was able to finish.
			// 
			index = index+1;
			
			returnCheck();
			if (needToReturn){
				return returnTime;
			}

			// enkel uitvoeren als er een volgende is.
			setSubTaksOfSubStatement(index);
		}	
	}

	
	private void setRemainingTime(double time){
		remainingTime = time;
	}
	
	private void decreaceRemainingTime(double amount){
		remainingTime = remainingTime - amount;
	}
	
	private void firstEvaluationOfStatement(){
		statement.execute();
		decreaceRemainingTime(statement.executionTime());
		subStatements = statement.result();
		
		if (subStatements != null){
			hasSubStatements = true;
		}else{
			hasSubStatements = false;
		}
		
		this.firstEvaluation = false;
	}
	
	private void returnCheck(){
		
		if (!stillThingsToDo()){
			needToReturn = true;
			if (remainingTime > 0){
				returnTime = remainingTime;
			}else if (remainingTime == -1){
				returnTime = -1;
			}else{
				returnTime = 0;
			}
		}else if(remainingTime <= 0){
			needToReturn = true;
			returnTime = -1;
		}
	}
	
	private boolean stillThingsToDo(){
		if (firstEvaluation == true){
			return true;
		}
		if (hasSubStatements == false){
			return false;
		}
		if (subStatements.size() == index){
			return false;
		}
		return true;
	}

	private void setSubTaksOfSubStatement(int index){
		subTask = new SubTask(subStatements.get(index), cube, task);
	}
}