package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class HerePosition extends CubeExpression {

	public HerePosition() {
	}
	
	@Override
	public Cube getValue() {
		return this.getStatement().getTask().getExecutor().getCube();
	}

}
