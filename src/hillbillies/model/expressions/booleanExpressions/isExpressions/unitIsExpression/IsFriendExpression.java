package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsFriendExpression extends UnitIsExpression {

	public IsFriendExpression(UnitExpression other) {
		super(other);
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Unit otherUnit = (Unit) other.getValue();
		setValue(thisUnit.getFaction() == otherUnit.getFaction());
	}
}
