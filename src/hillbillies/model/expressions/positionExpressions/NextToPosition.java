package hillbillies.model.expressions.positionExpressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hillbillies.model.Cube;
import hillbillies.model.Vector;
import hillbillies.model.expressions.Expression;

public class NextToPosition extends CubeExpression{

	public NextToPosition(CubeExpression position) {
		this.setPosition(position.getValue());
	}
	
	private void setPosition(Cube position) {
		this.position = position;
	}
	
	private Cube position;
	
	@Override 
	public Cube getValue() {
		List<Cube> randomCubesList = new ArrayList<Cube>();
		randomCubesList.addAll(position.getNeighbourCubes());
		Collections.shuffle(randomCubesList);
		//FIXME
		for (Cube cube : randomCubesList){
				if (cube.isPassable()){
					this.setPosition(cube);
					break;
			} 
			
			}
	}
}
