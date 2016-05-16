package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class SelectedPosition extends CubeExpression{

	public SelectedPosition() {
		
	}
	
	@Override
	public Cube getValue() {
		return this.getStatement().getTask().getCube();
	}
}
