package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Vector;

public class LiteralPosition extends CubeExpression{

	public LiteralPosition(int x, int y, int z) {
		Vector vector = new Vector(x, y, z);
		this.setValue(
				vector.getEnclosingCube(this.getStatement().getTask().
						getExecutor().getWorld()));
	}

}
