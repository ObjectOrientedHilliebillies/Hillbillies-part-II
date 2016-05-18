package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.expressions.Expression;

public abstract class CubeExpression<T> extends Expression<Cube>{
	private T position;

	public T getPosition() {
		return position;
	}

	public void setPosition(T position) {
		this.position = position;
	}
	
	public abstract Cube getValue();
	
}
