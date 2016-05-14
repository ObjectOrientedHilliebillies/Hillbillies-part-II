package hillbillies.model.expressions.positionExpressions;

public class LiteralPosition extends CubeExpression{

	public LiteralPosition(int x, int y, int z) {
		super(new int[] {x, y, z});
	}
}
