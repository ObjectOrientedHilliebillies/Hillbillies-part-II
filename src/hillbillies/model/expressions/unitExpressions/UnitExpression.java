package hillbillies.model.expressions.unitExpressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;

public abstract class UnitExpression extends Expression<Unit>{

	public UnitExpression(Unit unit) {
		setValue(unit);
//		World world = this.getStatement().getTask().getExecutor().getWorld();
//		this.world = world;
	}
	
	public UnitExpression(){
		}
	
//	public World getWorld() {
//		return this.world;
//	}
}
