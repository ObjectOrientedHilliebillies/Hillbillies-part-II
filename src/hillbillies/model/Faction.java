package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

public class Faction {
	public Faction(World world) {
		this.setWorld(world);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	private World world;
	
	private Set<Unit> unitsInFaction = new HashSet<>();
	
	public Set<Unit> getUnitsInFaction() {
		return this.unitsInFaction;
	}
	
	public int getNbOffUnitsInFaction() {
		return this.getUnitsInFaction().size();
	}
	
	public void addUnit(Unit unit) {
		if (this.getNbOffUnitsInFaction() != 50)
			this.unitsInFaction.add(unit);
			unit.setFaction(this);
	}

	public void removeUnit(Unit unit) {
		this.getUnitsInFaction().remove(unit);
		if (this.getNbOffUnitsInFaction() == 0)
				this.getWorld().removeFaction(this);
	}
}
