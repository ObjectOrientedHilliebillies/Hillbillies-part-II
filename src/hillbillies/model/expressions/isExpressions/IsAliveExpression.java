package hillbillies.model.expressions.isExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.booleanExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.TrueExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsAliveExpression extends IsExpression{

	public IsAliveExpression(UnitExpression unit) {
		this.unit = unit;
	}
	
	private UnitExpression unit;
	
	private Unit thisUnit = (Unit) unit;
	
	@Override
	public Expression eval() {
		if(unit.isAlive())
			return new TrueExpression();
		
		
		return new FalseExpression();
	}

}
