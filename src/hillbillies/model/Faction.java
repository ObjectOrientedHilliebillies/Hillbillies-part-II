package hillbillies.model;

public class Faction {
	
	public Faction(World world) {
		this.setWorld(world);
		world.addFaction(this);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	private World world;
	
	
	
	
}
