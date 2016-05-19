package hillbillies.model.expressions.positionExpressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hillbillies.model.Cube;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;
import junit.framework.Test;

public class NextToPosition extends CubeExpression<CubeExpression<?>>{
	
	// FIXME Is it allowed to use the questionmark?
	public NextToPosition(CubeExpression<?> position) {
		setPosition(position);
	}
	
	// Set<Cube> accessibleNeigbours =	new HashSet<>(world.getAccessibleNeigbours(position));
	
	@Override 
	public Cube getValue() {
		Cube cube = getPosition().getValue();
		//Set<Cube> accessibleNeigbours =	new HashSet<>(getWorld().getAccessibleNeigbours(cube));
		List<Cube> randomCubesList = new ArrayList<>();
		randomCubesList.addAll(cube.getNeighbourCubes());
		Collections.shuffle(randomCubesList);
		for (Cube neighbour : randomCubesList){
				if (neighbour.isPassable()){
					System.out.println(neighbour.toString());
					return neighbour;
			} 
			}
		throw new IllegalArgumentException();
	}
}
