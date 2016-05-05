package hillbillies.model.expressions.unitExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public abstract class UnitExpression extends Expression{

	public UnitExpression(Unit unit) {
		this.unit = unit;
	}
	
	private Unit unit;
	
	public Unit getUnit() {
		return this.unit;
	}

}
