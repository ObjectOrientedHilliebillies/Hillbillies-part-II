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
		System.out.println(position);
		System.out.println();
		this.getStatement().getTask().getExecutor();
		this.getStatement().getTask().getExecutor().getWorld();
		this.getStatement().getTask().getExecutor().getWorld().getCube(position);
		return this.getStatement().getTask().getExecutor().getWorld().getCube(position);
	}
}
