package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class SelectedPosition extends CubeExpression{

	public SelectedPosition() {
		
	}
	
	@Override
	public Cube getValue() {
		System.out.println("cube selected");
		System.out.println(this.getStatement());
		return this.getStatement().getTask().getCube();
	}
}
