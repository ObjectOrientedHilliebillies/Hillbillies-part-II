package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.ReadVariableExpression;
import hillbillies.model.expressions.booleanExpressions.BooleanExpression;
import hillbillies.model.expressions.booleanExpressions.BooleanReadVariableExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression.IsPassableExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression.IsSolidExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsAliveExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsCarryingItemExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsEnemyExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.IsFriendExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.unitIsExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.AndExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.NotExpression;
import hillbillies.model.expressions.booleanExpressions.logicalExpressions.OrExpression;
import hillbillies.model.expressions.positionExpressions.BoulderPosition;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.expressions.positionExpressions.HerePosition;
import hillbillies.model.expressions.positionExpressions.LiteralPosition;
import hillbillies.model.expressions.positionExpressions.LogPosition;
import hillbillies.model.expressions.positionExpressions.NextToPosition;
import hillbillies.model.expressions.positionExpressions.PositionOf;
import hillbillies.model.expressions.positionExpressions.PositionReadVariableExpression;
import hillbillies.model.expressions.positionExpressions.SelectedPosition;
import hillbillies.model.expressions.positionExpressions.WorkshopPosition;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.expressions.unitExpressions.UnitReadVariableExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.AnyExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.EnemyExpression;
import hillbillies.model.expressions.unitExpressions.anyExpression.FriendExpression;
import hillbillies.model.expressions.unitExpressions.thisExpression.ThisExpression;
import hillbillies.model.statements.AssignmentStatement;
import hillbillies.model.statements.AttackStatement;
import hillbillies.model.statements.BreakStatement;
import hillbillies.model.statements.FollowStatement;
import hillbillies.model.statements.IfStatement;
import hillbillies.model.statements.WhileStatement;
import hillbillies.model.statements.WorkStatement;
import hillbillies.model.task.Task;
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
public class TaskFactory implements ITaskFactory<Expression, Statement, Task> {

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		System.out.println("createTasks");
		if (activity.doYouHaveABreak()) {
			// TODO Of we zetten task.isWellFormed = false ofzo?
			throw new IllegalAccessError("Break outside of while");
		}
		List<Task> taskList = new ArrayList<>(); 
		if (selectedCubes.size() == 0){
			Task task = new Task(name, priority, activity);
			taskList.add(task);
		}else{
		for (int[] cube : selectedCubes) {
			Task task = new Task(name, priority, activity, cube);
			taskList.add(task);
		}}
		this.dictionary.clear();
		return taskList;
	}

	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
		System.out.println("createAssignment");
		dictionary.put(variableName, value);
		return new AssignmentStatement(variableName, value, sourceLocation);
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		System.out.println("createWhile");
		BooleanExpression boolCondition = (BooleanExpression) condition;
		return  new WhileStatement(boolCondition, body);
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		System.out.println("createIf");
		Statement ifStatement = new IfStatement(condition, ifBody, elseBody, sourceLocation);
		return ifStatement;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		System.out.println("createBreak");
		return new BreakStatement(sourceLocation);
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		System.out.println("createPrint");
		return new PrintStatement(value);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		System.out.println("createSequence");
		return new SequenceStatement(statements, sourceLocation);
	}

	@Override
	public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
		System.out.println("createMoveTo");
		CubeExpression cube = (CubeExpression) position;
		MoveToStatement moveToStatement = new MoveToStatement(cube, sourceLocation);
		return moveToStatement;
	}

	@Override
	public Statement createWork(Expression position, SourceLocation sourceLocation) {
		System.out.println("createWork");
		CubeExpression cube = (CubeExpression) position;
		WorkStatement workStatement = new WorkStatement(cube, sourceLocation);
		return workStatement;
	}

	@Override
	public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createFollow");
		UnitExpression unitE = (UnitExpression) unit;
		FollowStatement followStatement = new FollowStatement(unitE, sourceLocation);
		return followStatement;
	}

	@Override
	public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createAttack");
		return new AttackStatement(unit);
	}

	@Override
	public Expression createReadVariable(String variableName, SourceLocation sourceLocation) {
		System.out.println("createReadVariable");
		if (dictionary.get(variableName) instanceof UnitExpression) {
			return new UnitReadVariableExpression(variableName, sourceLocation);
		}
		
		if (dictionary.get(variableName) instanceof CubeExpression<?>) {
			return new PositionReadVariableExpression(variableName, sourceLocation);
		}
		
		if (dictionary.get(variableName) instanceof BooleanExpression) {
			return new BooleanReadVariableExpression(variableName, sourceLocation);
		}
		
		return null;
	
//		return dictionary.get(variableName);
//		return new ReadVariableExpression<>(variableName, dictionary.get(variableName), sourceLocation);
	}

	@Override
	public Expression createIsSolid(Expression position, SourceLocation sourceLocation) {
		System.out.println("createIsSolid");
		return new IsSolidExpression((CubeExpression) position);
	}

	@Override
	public Expression createIsPassable(Expression position, SourceLocation sourceLocation) {
		System.out.println("createIsPassable");
		CubeExpression cube = (CubeExpression) position;
		return new IsPassableExpression(cube);
	}

	@Override
	public Expression createIsFriend(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createIsFriend");
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsFriendExpression(givenUnit);			
	}

	@Override
	public Expression createIsEnemy(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createIsEnemy");
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsEnemyExpression(givenUnit);
	}

	@Override
	public Expression createIsAlive(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createIsAlive");
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsAliveExpression(givenUnit);
	}

	@Override
	public Expression createCarriesItem(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createCarriesItem");
		UnitExpression givenUnit = (UnitExpression) unit;
		return new IsCarryingItemExpression(givenUnit, sourceLocation);
	}

	@Override
	public Expression createNot(Expression expression, SourceLocation sourceLocation) {
		System.out.println("createNot");
		return new NotExpression((BooleanExpression) expression);
	}

	@Override
	public Expression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		System.out.println("createAnd");
		return new AndExpression((BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		System.out.println("createOr");
		return new OrExpression((BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression createHerePosition(SourceLocation sourceLocation) {
		System.out.println("createHerePosition");
		return new HerePosition(sourceLocation);
	}
	
	@Override
	public Expression createLogPosition(SourceLocation sourceLocation) {
		System.out.println("createLogPosition");
		return new LogPosition();
	}

	@Override
	public Expression createBoulderPosition(SourceLocation sourceLocation) {
		System.out.println("createBoulderPosition");
		return new BoulderPosition();
	}

	@Override
	public Expression createWorkshopPosition(SourceLocation sourceLocation) {
		System.out.println("createWorkshopPosition");
		return new WorkshopPosition();
	}

	@Override
	public Expression createSelectedPosition(SourceLocation sourceLocation) {
		System.out.println("createSelectedPosition");
		return new SelectedPosition();
	}

	@Override
	public Expression createNextToPosition(Expression position, SourceLocation sourceLocation) {
		System.out.println("createNextToPosition");
		return new NextToPosition((CubeExpression<?>) position);
	}

	@Override
	public Expression createPositionOf(Expression unit, SourceLocation sourceLocation) {
		System.out.println("createPositionOf");
		UnitExpression givenUnit = (UnitExpression) unit;
		return new PositionOf(givenUnit);
	}

	@Override
	public Expression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		System.out.println("createLiteralPosition");
		return new LiteralPosition(x, y, z);
	}

	@Override
	public Expression createThis(SourceLocation sourceLocation) {
		System.out.println("createThis");
		return new ThisExpression(sourceLocation);
	}

	@Override
	public Expression createFriend(SourceLocation sourceLocation) {
		System.out.println("createFriend");
		return new FriendExpression();
	}

	@Override
	public Expression createEnemy(SourceLocation sourceLocation) {
		System.out.println("createEnemy");
		return new EnemyExpression();
	}

	@Override
	public Expression createAny(SourceLocation sourceLocation) {
		System.out.println("createAny");
		return new AnyExpression();
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		System.out.println("createTrue");
		return new TrueExpression();
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		System.out.println("createFalse");
		return new FalseExpression();
	}
	
	private HashMap<String, Expression<?>> dictionary = new HashMap();

}
	