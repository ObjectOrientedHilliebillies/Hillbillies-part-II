package hillbillies.model.expressions.positionExpressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hillbillies.model.Cube;
import hillbillies.model.Vector;
import hillbillies.model.expressions.Expression;

public class NextToPosition extends CubeExpression{

	public NextToPosition(Expression position) {
		this.position = position;
	}
	
	private Expression position;
	
	@Override 
	public Cube getValue() {
		List<Cube> randomCubesList = new ArrayList<Cube>();
		randomCubesList.addAll(position.getNeighbourCubes());
		Collections.shuffle(randomCubesList);
		
		for (Cube cube : randomCubesList){
			Vector newPosition = cube.getCentreOfCube();
			try {
				this.setPosition(newPosition);
				break;
			} catch (IllegalArgumentException e) {
			}
	}
}
