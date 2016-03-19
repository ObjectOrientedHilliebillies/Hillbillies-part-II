package hillbillies.model;

public class World {
	private final int cubesPerRib = 50;
	
	public World(){
		
	}
	
	public boolean inBorders(Vector vector){
		return (vector.getXCoord() <= this.cubesPerRib && vector.getXCoord() >= 0 &&
				vector.getYCoord() <= this.cubesPerRib && vector.getYCoord() >= 0 &&
				vector.getZCoord() <= this.cubesPerRib && vector.getZCoord() >= 0);
	}
}
