package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.expressions.booleanExpressions.isExpressions.IsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class unitIsExpression extends IsExpression<UnitExpression> {
	public unitIsExpression(UnitExpression e){
		setExpression(e);
	}
}
