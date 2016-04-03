package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

public class Faction {
	
	public Faction(World world) {
		this.setWorld(world);
	}
	
	/**
	 * Return the world of this faction.
	 */
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of this faction to the given world.
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Variable registering the world of this faction.
	 */
	private World world;
	
	/**
	 * Set registering all the units in this faction.
	 */
	private Set<Unit> unitsInFaction = new HashSet<>();
	
	/**
	 * Return all units in this faction.
	 */
	public Set<Unit> getUnitsInFaction() {
		return this.unitsInFaction;
	}
	
	/**
	 * Return the number of units in this faction.
	 */
	public int getNbOffUnitsInFaction() {
		return this.getUnitsInFaction().size();
	}
	
	/**
	 * Add the given unit to this faction.
	 * 
	 * @post if the number of units in this faction is smaller than 50, 
	 * 		the given unit is added to this faction.
	 */
	public void addUnit(Unit unit) {
		if (this.getNbOffUnitsInFaction() != 50)
			this.unitsInFaction.add(unit);
			unit.setFaction(this);
	}
	
	/**
	 * Remove the given unit from this faction.
	 * 
	 * @post the given unit is removed from this faction. 
	 * @post if the given unit was the last unit in this faction, this faction
	 * 		is removed.
	 */
	public void removeUnit(Unit unit) {
		this.getUnitsInFaction().remove(unit);
		if (this.getNbOffUnitsInFaction() == 0)
				this.getWorld().removeFaction(this);
	}
}
