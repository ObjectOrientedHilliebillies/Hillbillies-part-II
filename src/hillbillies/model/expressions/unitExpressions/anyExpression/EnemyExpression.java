package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class EnemyExpression extends UnitExpression {

	public EnemyExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		return this.getWorld(task).getUnits().stream()
				.filter(i -> i.getFaction() 
						!= task.getExecutor().getFaction())
				.findAny().orElse(null);
	}

}
