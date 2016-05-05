package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.IsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsCarryingItemExpression extends UnitIsExpression{
	public IsCarryingItemExpression(UnitExpression unit){
		super(unit);
		Unit thisUnit = (Unit) unit.getValue();
		setValue(thisUnit.isCarryingBoulder() || thisUnit.isCarryingLog());
	}
}
