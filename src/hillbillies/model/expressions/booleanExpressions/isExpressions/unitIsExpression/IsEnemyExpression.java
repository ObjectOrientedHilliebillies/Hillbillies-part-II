package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsEnemyExpression extends UnitIsExpression{
	public IsEnemyExpression(UnitExpression unit){
		super(unit);
		Unit thisUnit = this.getStatement().getTask().getExecutor();
		Unit otherUnit = (Unit) unit.getValue();
		setValue(thisUnit.getFaction() == otherUnit.getFaction());		
	}
}
