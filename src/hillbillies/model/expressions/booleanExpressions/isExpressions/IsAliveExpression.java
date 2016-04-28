package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsAliveExpression extends Expression{

	public IsAliveExpression(UnitExpression unit) {

		this.unit = unit;
	}
	
	private Unit unit;
	
	@Override
	public Expression eval() {

		if(unit.isAlive())
			return new TrueExpression();
		
		
		return new FalseExpression();
	}

}
