package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;

public class EnemyExpression extends UnitExpression {

	public EnemyExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		return task.getExecutor().getWorld().getUnits().stream()
				.filter(i -> i.getFaction() 
						!= task.getExecutor().getFaction())
				.findAny().orElse(null);
	}

}
