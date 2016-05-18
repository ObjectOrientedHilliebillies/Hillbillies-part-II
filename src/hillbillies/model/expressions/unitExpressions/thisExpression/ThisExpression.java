package hillbillies.model.expressions.unitExpressions.thisExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class ThisExpression extends UnitExpression {

	public ThisExpression() {
	}
	
	@Override
	public Unit getValue() {
		return getExecutor();
	}
	

}
