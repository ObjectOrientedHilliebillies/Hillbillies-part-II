package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;

public class IsEnemyExpression extends unitIsExpression{
	public IsEnemyExpression(UnitExpression unit){
		super(unit);
		this.unit = unit;	
	}
	
	private UnitExpression unit;
	
	@Override
	public Boolean getValue(Task task) {
		Unit unit = this.unit.getValue(task);
		return (task.getExecutor().getFaction() != unit.getFaction());
	}
}
