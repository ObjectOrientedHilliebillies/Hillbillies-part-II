package hillbillies.model.expressions.unitExpressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;

public abstract class UnitExpression extends Expression<Unit>{

	public UnitExpression() {
	World world = this.getStatement().getTask().getExecutor().getWorld();
	this.world = world;
}

private World world;

public World getWorld() {
return this.world;
}
}
