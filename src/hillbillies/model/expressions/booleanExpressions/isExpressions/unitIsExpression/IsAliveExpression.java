package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.IsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsAliveExpression extends IsExpression{

	public IsAliveExpression(UnitExpression unit) {
		this.unit = unit;
	}
	
	private UnitExpression unit;
	
	@Override
	public BooleanValueExpression eval() {

		if(unit.getUnit().isAlive()){
			return new TrueExpression();
		}
		return new FalseExpression();
	}

}
