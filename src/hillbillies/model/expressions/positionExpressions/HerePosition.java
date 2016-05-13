package hillbillies.model.expressions.positionExpressions;

public class HerePosition extends CubeExpression {

	public HerePosition() {
		this.setValue(this.getStatement().getTask().getExecutor().getCube());
	}

}
