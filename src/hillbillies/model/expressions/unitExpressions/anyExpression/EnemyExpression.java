package hillbillies.model.expressions.unitExpressions.anyExpression;

import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class EnemyExpression extends UnitExpression {

	public EnemyExpression() {
		this.setValue(this.getWorld().getUnits().stream()
				.filter(i -> i.getFaction() 
						!= this.getStatement().getTask().getExecutor().getFaction())
				.findAny().orElse(null));
	}

}
