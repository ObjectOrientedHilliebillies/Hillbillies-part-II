package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

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
	public Statement getStatement() {
		return this.statement;
	}
	
	@Override
	public void setStatement(Statement statement) {
		this.statement = statement;
		getExpression().setStatement(statement);
		System.out.println(getExpression().getStatement());
	};
	
	@Override
	public Boolean getValue(){
		System.out.println(getStatement().getTask());
		System.out.println(getExpression().getStatement());
		Unit unit = getExpression().getValue();
		System.out.println(unit);
		return (unit.isCarryingBoulder() || unit.isCarryingLog());
	}
}
