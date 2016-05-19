package hillbillies.model.expressions.booleanExpressions.isExpressions.cubeIsExpression;

import hillbillies.model.Cube;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.statements.Statement;

public class IsSolidExpression extends CubeIsExpression{
	public IsSolidExpression(CubeExpression e) {
		super(e);
	}

	@Override
	public Boolean getValue() {
		Cube thisCube = getExpression().getValue();
		return thisCube.isSolid();
	}

	private Statement statement;
	
	@Override
	public void setStatement(Statement statement) {
		this.statement = statement;
		getExpression().setStatement(statement);
	}
	
	@Override
	public Statement getStatement() {
		return this.statement;
	}
}
