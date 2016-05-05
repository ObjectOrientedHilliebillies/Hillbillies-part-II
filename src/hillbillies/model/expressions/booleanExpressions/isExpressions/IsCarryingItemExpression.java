package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsCarryingItemExpression extends IsExpression{

	public IsCarryingItemExpression(UnitExpression unit) {
		this.unit = unit;
	}
	
	private UnitExpression unit;

	@Override
	public BooleanValueExpression eval() {
		if (unit.getUnit().isCarryingBoulder() || unit.getUnit().isCarryingLog()){
			return new TrueExpression();
		}
		return new FalseExpression();
	}
	
	

}
