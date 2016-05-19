package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class IsAliveExpression extends UnitIsExpression{
	public IsAliveExpression(UnitExpression unit){
		super(unit);
		this.unit = unit;
	}
	
	private UnitExpression unit;
	
	@Override
	public Boolean getValue(Task task) {
		Unit unit = this.unit.getValue(task);
		return unit.isAlive();
	}
}
