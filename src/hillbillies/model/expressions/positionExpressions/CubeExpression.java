package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;

public class CubeExpression extends Expression<Cube>{
	public CubeExpression(int[] position) {
		this.position = position;
	}
	
	private int[] position;
	
	@Override
	public Cube getValue(Task task) {
		System.out.println(position);
		System.out.println(task);
		task.getExecutor();
		task.getExecutor().getWorld();
		task.getExecutor().getWorld().getCube(position);
		return task.getExecutor().getWorld().getCube(position);
	}
}
