package hillbillies.model;

import java.util.Set;

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
	
<<<<<<< HEAD
	private Set<Unit> unitsOfFaction;
=======
	private Set<Unit> unitsInFaction;
	
	public Set<Unit> getUnitsInFaction() {
		return this.unitsInFaction;
	}
	
	public int getNbOffUnitsInFaction() {
		return this.getUnitsInFaction().size();
	}
	
	public void addUnit(Unit unit) {
		if (this.getNbOffUnitsInFaction() < 50)
			this.unitsInFaction.add(unit);
	}
	
>>>>>>> refs/remotes/origin/master
	
	public Set<Unit> getUnitsOfFaction(){
		return unitsOfFaction;
	}
}
