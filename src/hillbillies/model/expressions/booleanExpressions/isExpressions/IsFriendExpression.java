package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsFriendExpression extends IsExpression {

	public IsFriendExpression(UnitExpression unit) {
		this.unit = unit;
	}
	
	private UnitExpression unit;

	@Override
	public BooleanValueExpression eval() {
		if (unit.getUnit().getFaction() == )
		return null;
	}
}
