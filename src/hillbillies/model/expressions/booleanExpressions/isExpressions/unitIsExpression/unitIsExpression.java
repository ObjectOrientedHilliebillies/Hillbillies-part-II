package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.expressions.booleanExpressions.isExpressions.IsExpression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class UnitIsExpression extends IsExpression<UnitExpression> {
	public UnitIsExpression(UnitExpression e){
		setExpression(e);
	}
}
