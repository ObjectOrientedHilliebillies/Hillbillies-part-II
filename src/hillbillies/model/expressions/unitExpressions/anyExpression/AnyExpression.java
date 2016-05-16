package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class AnyExpression extends UnitExpression{

	public AnyExpression() {
	}
	
	@Override
	public Unit getValue() {
		return this.getWorld().getUnits().stream().findAny().orElse(null);
	}
	
}
