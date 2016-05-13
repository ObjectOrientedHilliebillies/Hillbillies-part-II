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
			timeCheck();
			
//			if (!stillTimeLeft){
//				return remainingTime, isFinished;
//			}
		}
		
		if (subTask == null){
			setSubTaksOfSubStatement(index);
		}
	
		while (true){
			remainingTime = subTask.advance(remainingTime);
			timeCheck();
//			if (!stillTimeLeft){
//				return remainingTime, isFinished;
//			}
			index = index+1;
//			if (index == statements.size()){
//				// Each steatement done.
//				return remainingTime;
//			}
			SubTask subTask = new SubTask(statements.get(index), cube, task);
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
	
	private int timeCheck(){
		if (remainingTime <= 0){
			stillTimeLeft = false;
		}
		if (!stillTimeLeft){
			if (isFinished==false){
				return 0, false;
			}else if (!hasSubStatements){
				return 0, true;
			}else if (index != subStatements.size()){
				return 0, false;
			}else if(){
				return 0, true
			}
		}
	}

	private void setSubTaksOfSubStatement(int index){
		subTask = new SubTask(subStatements.get(index), cube, task);
	}
}