package hillbillies.model.expressions.isExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public class IsFriendExpression extends IsUnitExpression {

	public IsFriendExpression(Unit unit) {
		this.unit = unit;
	}
	
	private Unit unit;

	@Override
	public Expression eval() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
