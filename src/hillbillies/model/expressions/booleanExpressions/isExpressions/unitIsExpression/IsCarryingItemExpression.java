package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.IsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.statements.Statement;
import hillbillies.part3.programs.SourceLocation;

public class IsCarryingItemExpression extends UnitIsExpression{

	public IsCarryingItemExpression(UnitExpression unit, SourceLocation sourceLocation){
		super(unit);
		setSourceLocation(sourceLocation);
//		unit.setStatement(getStatement());
	}
	
	private Statement statement;
	
	@Override
	public Boolean getValue(Task task){
		Unit unit = getExpression().getValue(task);
		return (unit.isCarryingBoulder() || unit.isCarryingLog());
	}
}
