package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.expressions.unitExpressions.UnitExpression;

public class PositionOf extends CubeExpression{

	public PositionOf(UnitExpression givenUnit) {
		this.givenUnit = givenUnit;
	}
	
	@Override
	public Cube getValue(Task task) {
		//givenUnit.setStatement(this.getStatement());
		return givenUnit.getStatement().getTask().getExecutor().getCube();
	}
	
	private UnitExpression givenUnit;
}
