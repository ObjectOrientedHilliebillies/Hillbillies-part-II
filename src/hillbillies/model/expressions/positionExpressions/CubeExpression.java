package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;

public class CubeExpression extends Expression<Cube>{
	public CubeExpression(int[] position) {
		this.position = position;
	}
	
	public CubeExpression() {}
	
	private int[] position;
	
	@Override
	public Cube getValue() {
		return this.getStatement().getTask().getExecutor().getWorld().getCube(position);
	}
}
