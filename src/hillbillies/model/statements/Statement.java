package hillbillies.model.statements;

import hillbillies.model.expressions.Expression;

/**
 * Class for representing statements
 * 
 * @author Jonas
 *
 */
public abstract class Statement {

	public Statement() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract <Statement, Expression> void evaluate();

	public Statement evaluate(String name, Expression value) {
		// TODO Auto-generated method stub
		return null;
	}

}
