package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;

public class SelectedPosition extends CubeExpression{

	public SelectedPosition() {
		
	}
	
	@Override
	public Cube getValue(Task task) {
		return task.getCube();
	}
}
