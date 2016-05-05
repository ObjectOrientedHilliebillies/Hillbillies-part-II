package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.Unit;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.BooleanValueExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.FalseExpression;
import hillbillies.model.expressions.booleanExpressions.booleanValueExpressions.TrueExpression;
import hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression.unitIsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsFriendExpression extends unitIsExpression {

	public IsFriendExpression(UnitExpression other) {
		super(other);
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Unit otherUnit = (Unit) other.eval();
		setValue(thisUnit.getFaction() == otherUnit.getFaction());
	}
}
