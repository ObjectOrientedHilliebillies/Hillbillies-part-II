package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.Unit;

public class MoveToStatement extends ActivityStatement{
	//TODO position een Cube maken
	public MoveToStatement(CubeExpression position) {
		this.position = position;
	}
	
	private Expression<Cube> position;

	@Override
	public double execute() {
		Cube cube = position.getValue();
		Unit unit = this.getTask().getExecutor();
		if (unit.getCube().equals(cube)){
			System.out.println("MoveToStatement: "+ "De unit staat er al");
			return 0;
		}
		unit.moveTo(cube);
		return -1;
	}	
}
