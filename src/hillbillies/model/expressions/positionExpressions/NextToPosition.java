package hillbillies.model.expressions.positionExpressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.Cube;
import hillbillies.model.statements.Statement;
import hillbillies.model.task.Task;

public class NextToPosition extends CubeExpression<CubeExpression<?>>{
	
	// FIXME Is it allowed to use the questionmark?
	public NextToPosition(CubeExpression<?> position) {
		setPosition(position);
	}
	
	// Set<Cube> accessibleNeigbours =	new HashSet<>(world.getAccessibleNeigbours(position));
	
	@Override 
	public Cube getValue(Task task) {
		Cube cube = getPosition().getValue(task);
		Set<Cube> accessibleNeigbours =	new HashSet<>(cube.getWorld().getAccessibleNeigbours(cube));
//		List<Cube> randomCubesList = new ArrayList<>();
//		randomCubesList.addAll(cube.getNeighbourCubes());
//		Collections.shuffle(randomCubesList);
		return accessibleNeigbours.stream().findAny().orElse(null);
	}
}
