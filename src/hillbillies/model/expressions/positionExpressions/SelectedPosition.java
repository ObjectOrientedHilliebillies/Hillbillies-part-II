package hillbillies.model.expressions.positionExpressions;

import hillbillies.model.Cube;

public class SelectedPosition extends CubeExpression{

	public SelectedPosition() {
		
	}
	
	@Override
	public Cube getValue() {
<<<<<<< HEAD
		System.out.print("cube selected");
		System.out.println(this.getStatement().toString());
		System.out.println(this.getStatement().getTask().toString());
		System.out.println(this.getStatement().getTask().getCube().toString());
=======
		System.out.println("cube selected");
		System.out.println(this.getStatement());
>>>>>>> refs/remotes/origin/master
		return this.getStatement().getTask().getCube();
	}
}
