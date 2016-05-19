package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class PositionOf extends CubeExpression{

	public PositionOf(UnitExpression givenUnit) {
		this.givenUnit = givenUnit;
	}
	
	@Override
	public Cube getValue(Task task) {
		Unit unit = this.givenUnit.getValue(task);
		return unit.getCube();
	}
	
	private UnitExpression givenUnit;
}
