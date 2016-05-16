package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class SelectedPosition extends CubeExpression{

	public SelectedPosition() {
		
	}
	
	@Override
	public Cube getValue() {
		System.out.print("cube selected");
		return this.getStatement().getTask().getCube();
	}
}
