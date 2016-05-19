package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;

public class HerePosition extends CubeExpression {

	public HerePosition(SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
	}
	
	@Override
	public Cube getValue() {
		return this.getStatement().getTask().getExecutor().getCube();
	}

}
