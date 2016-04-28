package hillbillies.model.expressions.isExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.TrueExpression;

public class IsAliveExpression extends Expression{

	public IsAliveExpression(Unit unit) {
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
