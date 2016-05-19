package hillbillies.model.expressions.positionExpressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.Cube;
import hillbillies.model.statements.Statement;

public class NextToPosition extends CubeExpression<CubeExpression<?>>{
	
	// FIXME Is it allowed to use the questionmark?
	public NextToPosition(CubeExpression<?> position) {
		setPosition(position);
	}
	
	// Set<Cube> accessibleNeigbours =	new HashSet<>(world.getAccessibleNeigbours(position));
	
	@Override 
	public Cube getValue() {
		Cube cube = getPosition().getValue();
<<<<<<< HEAD
		System.out.println(cube.toString());
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
=======
		Set<Cube> accessibleNeigbours =	new HashSet<>(getWorld().getAccessibleNeigbours(cube));
//		List<Cube> randomCubesList = new ArrayList<>();
//		randomCubesList.addAll(cube.getNeighbourCubes());
//		Collections.shuffle(randomCubesList);
		return accessibleNeigbours.stream().findAny().orElseThrow(null);
	}
	
	private Statement statement;
	
	@Override
	public void setStatement(Statement statement) {
		this.statement = statement;
		getPosition().setStatement(statement);
	}
	
	@Override
	public Statement getStatement() {
		return this.statement;
>>>>>>> refs/remotes/origin/master
	}
}
