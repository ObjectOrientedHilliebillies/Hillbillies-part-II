package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class PositionOf extends CubeExpression{

	public PositionOf(UnitExpression givenUnit) {
		this.setValue(givenUnit.getStatement().getTask().getExecutor().getCube());
	}

}
