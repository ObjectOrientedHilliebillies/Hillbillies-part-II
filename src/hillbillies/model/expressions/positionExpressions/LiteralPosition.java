package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class LiteralPosition extends CubeExpression<int[]>{

	public LiteralPosition(int x, int y, int z) {
		this.setPosition(new int[] {x, y, z});
	}
	
	@Override
	public Cube getValue() {
		return this.getWorld().getCube(getPosition());
	}
}
