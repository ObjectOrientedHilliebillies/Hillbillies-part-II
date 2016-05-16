package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression.IsPassableExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression.IsSolidExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsAliveExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsCarryingItemExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsEnemyExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsFriendExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.AndExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.NotExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.OrExpression;
import hillbillies.model.expressions.positionExpressions.BoulderPosition;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.expressions.positionExpressions.HerePosition;
import hillbillies.model.expressions.positionExpressions.LiteralPosition;
import hillbillies.model.expressions.positionExpressions.LogPosition;
import hillbillies.model.expressions.positionExpressions.PositionOf;
import hillbillies.model.expressions.positionExpressions.SelectedPosition;
import hillbillies.model.expressions.positionExpressions.WorkshopPosition;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.AnyExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.EnemyExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.FriendExpression;
import hillbillies.model.expressions.unitExpressions.thisExpression.ThisExpression;
import hillbillies.model.statements.AssignmentStatement;
import hillbillies.model.statements.AttackStatement;
import hillbillies.model.statements.FollowStatement;
import hillbillies.model.statements.IfStatement;
import hillbillies.model.statements.WhileStatement;
import hillbillies.model.statements.WorkStatement;
import hillbillies.model.statements.MoveToStatement;
import hillbillies.model.statements.PrintStatement;
import hillbillies.model.statements.SequenceStatement;
import hillbillies.model.statements.Statement;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

/**
 * 
 * @author Victor
 * @param <E>
 * @param <S>
 * @param <T>
 *
 */
public class TaskFactory implements ITaskFactory<Expression, Statement, Task>{

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		List<Task> taskList = new ArrayList<>(); 
		if (selectedCubes.size() == 0){
			Task task = new Task(name, priority, activity);
			taskList.add(task);
			activity.setTask(task);
		}else{
		for (int[] cube : selectedCubes) {
			Task task = new Task(name, priority, activity, cube);
			taskList.add(task);
			activity.setTask(task);
		}}
		return taskList;
	}

	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
		return new AssignmentStatement(variableName, value);
		//FIXME
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		Statement whileStatement = new WhileStatement(condition, body);
		condition.setStatement(whileStatement); //TODO nakijken 3
		return whileStatement; 
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfStatement(condition, ifBody, elseBody);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		return new PrintStatement(value);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		return new SequenceStatement(statements);
	}

	@Override
	public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
		CubeExpression cube = (CubeExpression) position;
		MoveToStatement moveToStatement = new MoveToStatement(cube);
		position.setStatement(moveToStatement);
		return moveToStatement;
	}

	@Override
	public Statement createWork(Expression position, SourceLocation sourceLocation) {
		CubeExpression cube = (CubeExpression) position;
		WorkStatement workStatement = new WorkStatement(cube);
		position.setStatement(workStatement);
		return workStatement;
	}

	@Override
	public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
		return new FollowStatement(unit);
	}

	@Override
	public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
		return new AttackStatement(unit);
	}

	@Override
	public Expression createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsSolid(Expression position, SourceLocation sourceLocation) {
		CubeExpression cube = (CubeExpression) position;
		return new IsSolidExpression(cube);
	}

	@Override
	public Expression createIsPassable(Expression position, SourceLocation sourceLocation) {
		CubeExpression cube = (CubeExpression) position;
		return new IsPassableExpression(cube);
	}

	@Override
	public Expression createIsFriend(Expression unit, SourceLocation sourceLocation) {
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsFriendExpression(givenUnit);			
	}

	@Override
	public Expression createIsEnemy(Expression unit, SourceLocation sourceLocation) {
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsEnemyExpression(givenUnit);
	}

	@Override
	public Expression createIsAlive(Expression unit, SourceLocation sourceLocation) {
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsAliveExpression(givenUnit);
	}

	@Override
	public Expression createCarriesItem(Expression unit, SourceLocation sourceLocation) {
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsCarryingItemExpression(givenUnit);
	}

	@Override
	public Expression createNot(Expression expression, SourceLocation sourceLocation) {
		return new NotExpression((BooleanExpression) expression);
	}

	@Override
	public Expression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		return new AndExpression((BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		return new OrExpression((BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression createHerePosition(SourceLocation sourceLocation) {
		return new HerePosition();
	}
	
	@Override
	public Expression createLogPosition(SourceLocation sourceLocation) {
		return new LogPosition();
	}

	@Override
	public Expression createBoulderPosition(SourceLocation sourceLocation) {
		return new BoulderPosition();
	}

	@Override
	public Expression createWorkshopPosition(SourceLocation sourceLocation) {
		return new WorkshopPosition();
	}

	@Override
	public Expression createSelectedPosition(SourceLocation sourceLocation) {
		return new SelectedPosition();
	}

	@Override
	public Expression createNextToPosition(Expression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createPositionOf(Expression unit, SourceLocation sourceLocation) {
		UnitExpression givenUnit = (UnitExpression) unit;
		return new PositionOf(givenUnit);
	}

	@Override
	public Expression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		System.out.println("TaskFactory createLiteralPosition");
		return new LiteralPosition(x, y, z);
	}

	@Override
	public Expression createThis(SourceLocation sourceLocation) {
		return new ThisExpression();
	}

	@Override
	public Expression createFriend(SourceLocation sourceLocation) {
		return new FriendExpression();
	}

	@Override
	public Expression createEnemy(SourceLocation sourceLocation) {
		return new EnemyExpression();
	}

	@Override
	public Expression createAny(SourceLocation sourceLocation) {
		return new AnyExpression();
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		return new TrueExpression();
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		return new FalseExpression();
	}
}
	