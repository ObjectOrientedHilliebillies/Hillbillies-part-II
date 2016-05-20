package hillbillies.model;

import java.util.ArrayList;

//New classes: boulder, log, world
//New attributes: faction, experience
//New methods: fall, die, carry
//Adapted methods: moveTo, work, dodge

//Boulder: Has a position, weight between 10 and 50, (unit carrying? checken in facade)

//Log: same as boulder

//World: default = air, checken in facade, na world creation: checken of naar 
//			elke blok een pad loopt vanaf de rand van de wereld. anders collapsen.

//Faction: unit cannot attack units with same faction

//Experience: every 10 exp points strength, agility, or toughness increases with
//			1 point. 1 point per executed step, no points if movement is
//			interuptted. 20 points per succeeded dodge, block or attack.

//Fall: Fall when no neighbouring cube is solid. A unit loses 10 hitpoints
//			per Z-level they fall.

//Die: If hitpoints <= 0. Drop carried items.

//Carry: checken in facade. Als ge iets dropt dan komt dat terug

//moveTo: the block the unit is standing in must be non-solid, 
//			at least one neighbouring cube must be solid.

//Work: checken in facade. Rock gaat kapot => unit draagt rock

//Dodge: dodge to passable terrain.

import java.util.Collections;
import java.util.List;

import javax.crypto.IllegalBlockSizeException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.task.Task;
import ogp.framework.util.Util;

/**
 * @invar The position of each unit must be a valid position for any unit. |
 *        isValidPosition(getPosition())
 * @invar The weight of each unit must be a valid weight for any unit. |
 *        isValidWeight(getWeight())
 * 
 * @invar The strength of each unit must be a valid strength for any unit. |
 *        isValidStrength(getStrength())
 * 
 * @invar The agility of each unit must be a valid agility for any unit. |
 *        isValidAgility(getAgility())
 * 
 * @invar The toughness of each unit must be a valid toughness for any unit. |
 *        isValidToughness(getToughness())
 * 
 * @invar The stamina of each unit must be a valid stamina for any unit. |
 *        isValidStamina(getStamina())
 * 
 * @invar The hitpoints of each Unit must be a valid hitpoints for any Unit. |
 *        isValidHitpoints(getHitpoints())
 * @invar The orientation of each unit must be a valid orientation for any unit.
 *        | isValidOrientation(getOrientation())
 * 
 *        https://github.com/ObjectOrientedHilliebillies/Hillbillies-part-II.git
 * @version 1.0
 * @author Jonas Vantrappen & Victor Van Eetvelt
 */

public class Unit {

	/* Unit creation */
	/**
	 * Initialize this new unit with given name, given initialPosition, given
	 * weight, given agility, given strength, given toughness, given
	 * enableDefaultBehavior, maximal hitpoints, maximal stamina, default
	 * orientation.
	 * 
	 * @param name
	 *            The name for this new unit.
	 * @param initialPosition
	 *            The initial position for this new unit.
	 * @param weight
	 *            The weight for this new unit.
	 * @param agility
	 *            The agility for this new unit.
	 * @param strength
	 *            The strength for this new unit.
	 * @param toughness
	 *            The toughness for this new unit.
	 * @param enableDefaultBehavior
	 *            Enables default behavior for this new unit.
	 * @throws IllegalArgumentException
	 * @pre The maximal hitpoints must be a valid hitpoints for any Unit. |
	 *      isValidHitpoints(this.getMaxHitpoints)
	 * @pre The given stamina must be a valid stamina for any unit. |
	 *      isValidStamina(stamina)
	 * @effect The position of this new unit is set to the given position. |
	 *         this.setPosition(position)
	 * @post If the given weight is a valid weight for any unit, the weight of
	 *       this new unit is equal to the given weight. Otherwise, the weight
	 *       of this new unit is equal to (strength+agility)/2. | if
	 *       (isValidWeight(weight)) | then new.getWeight() == weight | else
	 *       new.getWeight() == (strength+agility)/2
	 * @post If the given agility is a valid agility for any unit, the agility
	 *       of this new unit is equal to the given agility. Otherwise, the
	 *       agility of this new unit is equal to 25. | if
	 *       (isValidAgility(agility)) | then new.getAgility() == agility | else
	 *       new.getAgility() == 25
	 * @post If the given strength is a valid strength for any unit, the
	 *       strength of this new unit is equal to the given strength.
	 *       Otherwise, the strength of this new unit is equal to 25. | if
	 *       (isValidStrength(strength)) | then new.getStrength() == strength |
	 *       else new.getStrength() == 25
	 * @post If the given toughness is a valid toughness for any unit, the
	 *       toughness of this new unit is equal to the given toughness.
	 *       Otherwise, the toughness of this new unit is equal to 25. | if
	 *       (isValidToughness(toughness)) | then new.getToughness() ==
	 *       toughness | else new.getToughness() == 25
	 * @post The hitpoints of this new Unit is equal to the maximal hitpoints. |
	 *       new.getHitpoints() == this.getMaxHitpoints
	 * @post The stamina of this new unit is equal to the given stamina. |
	 *       new.getStamina() == stamina
	 * @post The orientation of this new unit is equal to PI/2. | if
	 *       (isValidOrientation(orientation)) | new.getOrientation() == PI/2
	 */
	public Unit(String name, int[] initialCube, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) {
		this.setName(name);

		try {
			Vector position = new Vector(initialCube[0] + 0.5, initialCube[1] + 0.5, initialCube[2] + 0.5);
			this.setPosition(position);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		if (!isValidAgility(agility))
			agility = 25;
		else
			this.setAgility(agility);
		if (!isValidStrength(strength))
			strength = 25;
		else
			this.setStrength(strength);
		if (!isValidToughness(toughness))
			toughness = 25;
		else
			this.setToughness(toughness);
		if (!isValidWeight(weight))
			this.weight = this.getMinWeight();
		else
			this.setWeight(weight);
		this.setDefaultBehavior(enableDefaultBehavior);

		setHitpoints(getMaxHitpoints() - 5);
		setStamina(getMaxStamina() - 5);

		this.orientation = (Math.PI / 2);
	}

	/**
	 * Initialize this unit with the given name, position and world.
	 * 
	 * @param name
	 *            The name for this new unit.
	 * @param initialPosition
	 *            The initial position for this new unit.
	 * @param enableDefaultBehavior
	 *            The behavior for this new unit.
	 * @param world
	 *            The world for this new unit.
	 * @effect The position of this new unit is set to the given position. |
	 *         this.setPosition(position)
	 * @post The world of this unit is the world world.
	 * @effect The strength, agility, toughness and weight of this unit are a
	 *         random integer between 0 and 200.
	 */
	public Unit(String name, Vector initialPosition, boolean enableDefaultBehavior, World world) {
		this.world = world;
		this.setName("Name");

		try {
			this.setPosition(initialPosition);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		this.setAgility((int) (Math.random() * 75) + 25);
		this.setStrength((int) (Math.random() * 75) + 25);
		this.setToughness((int) (Math.random() * 75) + 25);

		this.weight = this.getMinWeight();
		this.setWeight((int) (Math.random() * 200));

		setHitpoints(getMaxHitpoints());
		setStamina(getMaxStamina());

		this.orientation = (Math.PI / 2);

		this.setDefaultBehavior(enableDefaultBehavior);
	}

	/**
	 * Variable registering the world of this unit.
	 */
	private World world;

	/**
	 * Return the world of this unit.
	 */
	@Basic
	@Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * Set the world of this unit to the given world.
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * Variable registering the faction of this unit.
	 */
	private Faction faction;

	/**
	 * Set the faction of this unit to the given faction
	 */
	@Raw
	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	/**
	 * Return the faction of this unit.
	 */
	@Basic
	@Raw
	public Faction getFaction() {
		return this.faction;
	}

	////////////////////////////////////////////////////
	//////////////////// * POSITION *////////////////////
	////////////////////////////////////////////////////

	/**
	 * Variable registering the position of this unit.
	 */
	private Vector position;

	/**
	 * Variable registering the target cube of this Unit.
	 */
	private Cube targetCube;

	/**
	 * Variable registering the target position of this Unit.
	 */
	private Vector targetPosition;

	/**
	 * Return the position of this unit.
	 */
	@Basic
	@Raw
	public Vector getPosition() {
		return this.position;
	}

	/**
	 * Return the position of this unit.
	 */
	@Basic
	@Raw
	public double[] getDoublePosition() {
		return this.position.getVector();
	}

	/**
	 * Set the position of this unit to the given position.
	 * 
	 * @param position
	 *            The new position for this unit.
	 * @post The position of this new unit is equal to the given position. |
	 *       new.getPosition() == position
	 * @throws IllegalArgumentException
	 *             The given position is not a valid position for any unit. | !
	 *             isValidPosition(this.getPosition())
	 */
	@Raw
	private void setPosition(Vector position) {
		if (this.world == null) {
			this.position = position;
			return;
		}
		if (!this.world.isPositionInWorld(position))
			throw new IllegalArgumentException();
		this.position = position;
	}

	/**
	 * Return the target position of this unit.
	 */
	@Basic
	@Raw
	private Vector getTargetPosition() {
		return this.targetPosition;
	}

	/**
	 * Set the target position of this unit to the given target position.
	 * 
	 * @param position
	 *            The new position for this unit.
	 * @post The target position of this unit is equal to the given target
	 *       position. | new.getTargetPosition() == targetPosition
	 * @throws IllegalArgumentExeption
	 *             The given target position is not a valid target position for
	 *             any unit. | ! isValidPosition(this.getPosition())
	 */
	@Raw
	private void setTargetPosition(Vector targetPosition) {
		if (!this.world.isPositionInWorld(targetPosition))
			throw new IllegalArgumentException();
		this.targetPosition = targetPosition;
	}

	/**
	 * Return the cube of this unit.
	 */
	@Basic
	@Raw
	public Cube getCube() {
		List<Integer> cubeCoord = new ArrayList<>();
		cubeCoord.add((int) getPosition().getXCoord());
		cubeCoord.add((int) getPosition().getYCoord());
		cubeCoord.add((int) getPosition().getZCoord());
		return getWorld().getCube(cubeCoord);
	}

	/**
	 * Return the target cube of this unit.
	 */
	@Basic
	@Raw
	private Cube getTargetCube() {
		return this.targetCube;
	}

	/**
	 * Set the cube of this unit to the given cube.
	 * 
	 * @param cube
	 *            The new cube for this unit.
	 * @post The cube of this new unit is equal to the given cube. |
	 *       new.getPosition() == cube
	 * @throws IllegalPositionException
	 *             The given cube is not a valid position for any unit. | !
	 *             isValidPosition(getCube())
	 */
	@Raw
	private void setTargetCube(Cube cube) {
		if (cube == null)
			throw new ClassCastException();
		this.targetCube = cube;
	}

	////////////////////////////////////////////////
	//////////////////// * NAME *////////////////////
	////////////////////////////////////////////////

	/**
	 * Return the name of this unit.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.unitName;
	}

	/**
	 * Check whether the given name is a valid name for any unit.
	 * 
	 * @param name
	 *            The name to check.
	 * @return | result == Character.isUpperCase(name.charAt(0)) &&
	 *         name.length() >= 2 && name.matches("[a-zA-Z ']")
	 */
	private static boolean isValidName(String unitName) {
		return Character.isUpperCase(unitName.charAt(0)) && unitName.length() >= 2 && unitName.matches("[a-zA-Z '\"]+");
	}

	/**
	 * Set the name of this unit to the given name.
	 * 
	 * @param unitName
	 *            The new name for this unit.
	 * @post The name of this new unit is equal to the given name. |
	 *       new.getName() == unitName
	 * @throws IllegalArgumentException
	 *             The given name is not a valid name for any unit. | !
	 *             isValidName(getName())
	 */
	@Raw
	public void setName(String unitName) throws IllegalArgumentException {
		if (!isValidName(unitName))
			throw new IllegalArgumentException();
		this.unitName = unitName;
	}

	/**
	 * Variable registering the name of this unit.
	 */
	private String unitName;

	////////////////////////////////////////////////////////
	///////////////////// * ATTRIBUTES */////////////////////
	////////////////////////////////////////////////////////
	/**
	 * Return the weight of this unit.
	 */
	@Basic
	@Raw
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Check whether the given weight is a valid weight for any unit.
	 * 
	 * @param weight
	 *            The weight to check.
	 * @return | result == maxWeight > weight >= (strength+agility)/2
	 */
	private boolean isValidWeight(int weight) {
		return (weight >= this.getMinWeight() && weight <= maxWeight);
	}

	/**
	 * Set the weight of this unit to the given weight.
	 * 
	 * @param weight
	 *            The new weight for this unit.
	 * @post If the given weight is a valid weight for any unit, the weight of
	 *       this new unit is equal to the given weight. | if
	 *       (isValidWeight(weight)) | then new.getWeight() == weight | else |
	 *       new.getWeight() == minWeight
	 */
	@Raw
	public void setWeight(int weight) {
		if (isValidWeight(weight))
			this.weight = weight;
		else
			this.weight = this.getMinWeight();
	}

	/**
	 * Return the additional weight (the weight of a carried material) of this
	 * unit.
	 */
	private int getAdditionalWeight() {
		return this.additionalWeight;
	}

	/**
	 * Set the additional weight of this unit to the given weight
	 * 
	 * @param weight
	 *            The weight of a carried material
	 * 
	 * @post if the given weight is a valid weight for every unit, additional
	 *       weight equals the given weight.
	 */
	private void setAdditionalWeight(int weight) {
		if (isValidAdditionalWeight(weight))
			this.additionalWeight = weight;
	}

	/**
	 * Check whether the given weight is a valid additional weight for every
	 * unit.
	 * 
	 * @param weight
	 *            The weight to check
	 * 
	 * @return | 10 <= weight && 50 >= weight;
	 */
	private boolean isValidAdditionalWeight(int weight) {
		return 10 <= weight && 50 >= weight;
	}

	/**
	 * Return the total weight (own weight + weight of a carried material) of
	 * this unit.
	 */
	private int getTotalWeight() {
		return this.getWeight() + this.getAdditionalWeight();
	}

	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight;
	private int additionalWeight;

	/**
	 * Variable registering the maximum weight of this unit.
	 */
	private static int maxWeight = 200;

	/**
	 * Return the minimum weight of this unit.
	 */
	private int getMinWeight() {
		return (this.getStrength() + this.getAgility()) / 2;
	}

	/**
	 * Return the strength of this unit.
	 */
	@Basic
	@Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Check whether the given strength is a valid strength for any unit.
	 * 
	 * @param strength
	 *            The strength to check.
	 * @return | result == 0 < unitStrength <= maxStrength
	 */
	private static boolean isValidStrength(int strength) {
		return (0 < strength && strength <= maxStrength);
	}

	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param strength
	 *            The new strength for this unit.
	 * @post If the given strength is a valid strength for any unit, the
	 *       strength of this new unit is equal to the given strength. | if
	 *       (isValidStrength(strength)) | then new.getStrength() == strength
	 */
	@Raw
	public void setStrength(int strength) {
		if (isValidStrength(strength)) {
			this.strength = strength;
			setWeight(getWeight());
		}
	}

	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength = 25;

	/**
	 * Variable registering the maximum strength of this unit.
	 */
	private static int maxStrength = 200;

	/**
	 * Return the agility of this unit.
	 */
	@Basic
	@Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Check whether the given agility is a valid agility for any unit.
	 * 
	 * @param agility
	 *            The agility to check.
	 * @return | result == 0 < agility <= maxAgility
	 */
	private static boolean isValidAgility(int agility) {
		return (0 < agility && agility <= maxAgility);
	}

	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param agility
	 *            The new agility for this unit.
	 * @post If the given agility is a valid agility for any unit, the agility
	 *       of this new unit is equal to the given agility. | if
	 *       (isValidAgility(agility)) | then new.getAgility() == agility
	 */
	@Raw
	public void setAgility(int agility) {
		if (isValidAgility(agility)) {
			this.agility = agility;
			this.setWeight(getWeight());
		}
	}

	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility = 25;

	/**
	 * Variable registering the maximum agility of this unit.
	 */
	private static int maxAgility = 200;

	/**
	 * Return the toughness of this unit.
	 */
	@Basic
	@Raw
	public int getToughness() {
		return this.toughness;
	}

	/**
	 * Check whether the given toughness is a valid toughness for any unit.
	 * 
	 * @param toughness
	 *            The toughness to check.
	 * @return | result == 0 < toughness <= maxToughness
	 */
	private static boolean isValidToughness(int toughness) {
		return (0 < toughness && toughness <= maxToughness);
	}

	/**
	 * Set the toughness of this unit to the given toughness.
	 * 
	 * @param toughness
	 *            The new toughness for this unit.
	 * @post If the given toughness is a valid toughness for any unit, the
	 *       toughness of this new unit is equal to the given toughness. | if
	 *       (isValidToughness(toughness)) | then new.getToughness() ==
	 *       toughness
	 */
	@Raw
	public void setToughness(int toughness) {
		if (isValidToughness(toughness))
			this.toughness = toughness;
	}

	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;

	/**
	 * Variable registering the maximum toughness of this unit.
	 */
	private static int maxToughness = 200;

	/**
	 * Return the experience of this unit.
	 */
	public int getExperience() {
		return this.experience;
	}

	/**
	 * Check whether the given experience is a valid experience for any unit.
	 * 
	 * @param experience
	 *            The experience to check.
	 * @return | result == 0 < experience
	 */
	private static boolean isValidExperience(int experience) {
		if (experience >= 0)
			return true;
		return false;
	}

	/**
	 * Set the experience of this unit to the given experience.
	 * 
	 * @param experience
	 *            The new experience for this unit.
	 * @post If the given experience is a valid experience for any unit, the
	 *       experience of this new unit is equal to the given experience. If
	 *       the experience is higher than 10, strength, agility or toughness
	 *       will be increased with a point. | if
	 *       (isValidExperience(experience)) | then new.getExperience() ==
	 *       experience
	 */
	@Raw
	private void setExperience(int experience) {
		if (isValidExperience(experience))
			if (!(experience >= 10))
				this.experience = experience;
			else {
				this.experience = experience;
				int points;
				points = this.getExperience() / 10;
				this.setExperience(this.getExperience() % 10);
				double random = Math.random();
				if (random < 0.33)
					this.setStrength(this.getStrength() + points);
				else if (random < 0.66)
					this.setToughness(this.getToughness() + points);
				else
					this.setAgility(this.getAgility() + points);
			}
	}

	/**
	 * Increase the experience of this unit with the given experience.
	 * 
	 * @param experience
	 *            The experience to increase with.
	 * 
	 * @post experience is increased with the given experience, or if the
	 *       current experience is greater than 10, strength, agility or
	 *       toughness will be increased
	 */
	private void increaseExperience(int experience) {
		this.setExperience(this.getExperience() + experience);
	}

	/**
	 * Variable registering the experience of this unit.
	 */
	private int experience;

	/* Points */
	/**
	 * Return the stamina of this unit.
	 */
	@Basic
	@Raw
	public int getStamina() {
		return this.stamina;
	}

	/**
	 * Check whether the given stamina is a valid stamina for any unit.
	 * 
	 * @param stamina
	 *            The stamina to check.
	 * @return | result == 0 < stamina < this.getMaxStamina()
	 */
	private boolean isValidStamina(int stamina) {
		return (0 < stamina && stamina < this.getMaxStamina());
	}

	/**
	 * Set the stamina of this unit to the given stamina.
	 * 
	 * @param stamina
	 *            The new stamina for this unit.
	 * @pre The given stamina must be a valid stamina for any unit. |
	 *      isValidStamina(stamina)
	 * @post The stamina of this unit is equal to the given stamina. |
	 *       new.getStamina() == stamina
	 */
	@Raw
	private void setStamina(int stamina) {
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}

	/**
	 * Return the maximal stamina of this unit.
	 */
	@Basic
	@Raw
	public int getMaxStamina() {
		int maxStamina = this.getWeight() * this.getToughness() / 50;
		if ((this.getWeight() * this.getToughness()) % 50 == 0)
			return maxStamina;
		return maxStamina + 1;
	}

	/**
	 * Variable registering the stamina of this unit.
	 */
	private int stamina;

	/**
	 * Return the hitpoints of this Unit.
	 */
	@Basic
	@Raw
	public int getHitpoints() {
		return this.hitpoints;
	}

	/**
	 * Check whether the given hitpoints is a valid hitpoints for any Unit.
	 * 
	 * @param hitpoints
	 *            The hitpoints to check.
	 * @return | result == 0 < hitpoints <= getMaxHitpoints()
	 */
	private boolean isValidHitpoints(int hitpoints) {
		return ((0 < hitpoints) && (hitpoints <= this.getMaxHitpoints()));
	}

	/**
	 * Set the hitpoints of this Unit to the given hitpoints.
	 * 
	 * @param hitpoints
	 *            The new hitpoints for this Unit.
	 * @pre The given hitpoints must be a valid hitpoints for any Unit. |
	 *      isValidHitpoints(hitpoints)
	 * @post The hitpoints of this Unit is equal to the given hitpoints. |
	 *       new.getHitpoints() == hitpoints
	 */
	@Raw
	private void setHitpoints(int hitpoints) {
		if (hitpoints <= 0)
			this.die();
		else {
			assert isValidHitpoints(hitpoints);
			this.hitpoints = hitpoints;
		}
	}

	/**
	 * Return whether this unit is alive or not.
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * Variable registering whether this unit is alive or not. True if alive,
	 * false else.
	 */
	private boolean alive = true;

	/**
	 * Make this unit die.
	 * 
	 * @post if this unit is carrying any material, this material will be
	 *       dropped. this unit is removed from its faction.
	 */
	private void die() {
		if (this.isCarryingMaterial())
			this.dropMaterial(this.getPosition());
		if (this.getTask() != null) {
			this.getTask().taskFailed();
		}
		this.alive = false;
		this.getFaction().removeUnit(this);
	}

	/**
	 * Variable registering the hitpoints of this Unit.
	 */
	private int hitpoints;

	/**
	 * Return the maximum hitpoints of a unit.
	 */
	@Basic
	@Raw
	public int getMaxHitpoints() {
		int maxHitpoint = this.getWeight() * this.getToughness() / 50;
		if ((this.getWeight() * this.getToughness()) % 50 == 0)
			return maxHitpoint;
		return maxHitpoint + 1;
	}

	////////////////////////////////////////////////////
	///////////////////// * MOVING */////////////////////
	////////////////////////////////////////////////////

	/**
	 * Return the current speed of this unit.
	 */
	@Basic
	@Raw
	private double getCurrentSpeed() {
		return 3 * (this.getStrength() + this.getAgility()) / (4 * this.getTotalWeight());
	}

	//////////////////////////////////////////////////
	///////////////////// * TIME */////////////////////
	//////////////////////////////////////////////////

	/**
	 * Variable registering the time since the last rest.
	 */
	private double timeSinceLastRested = 0;

	// No documentation required for advanceTime
	public void advanceTime(double tickTime) {
		// Update time since last rested.
		this.timeSinceLastRested = this.timeSinceLastRested + tickTime;

		// Do everything around falling.
		this.falling(tickTime);

		if (this.timeSinceLastRested >= 180 && this.isValidActivity(4)) {
			this.rest();
		}

		if (this.activeActivity == 0 && this.nextActivity != 0) {
			this.startNextActivity();
		}

		if (this.activeActivity == 0 && (this.targetCube != null) && !getCube().equals(this.targetCube)) {
			doMoveTo();
		}

		if (isWorking())
			doWork(tickTime);
		else if (isResting())
			doRest(tickTime);
		else if (this.isAttacking())
			this.doAttack(tickTime);
		else if (this.isMoving())
			doMove(tickTime);
		else if (this.getDefaultBehavior())
			doDefaultBehavior(tickTime);
	}

	/**
	 * Check whether the given activity is a valid activity for this unit.
	 * 
	 * @param activity
	 *            The activity to check.
	 * @return | result == !(this.isResting() && recoverdPoints<1)
	 */
	private boolean isValidActivity(int activity) {
		if (this.activeActivity == 2)
			return false;
		if (this.isResting() && recoverdPoints < 1)
			return false;
		if (this.activeActivity == 3)
			return false;
		if (this.activeActivity == 5)
			return false;
		return true;
	}

	/**
	 * Start a new activity.
	 * 
	 * @post The new activity is started. | if nextActivity == "null"
	 *       activeActivity == "null" | else if nextActivity == "work" isWorking
	 *       == true | else if nextActivity == "rest" isResting == true
	 */
	private void startNextActivity() {
		if (nextActivity == 1) {
			activeActivity = 1;
			this.remainingTimeToFinishWork = 500 / (double) (this.getStrength());
			this.face(this.cubeWorkingOn.getCenterOfCube());
		} else if (nextActivity == 4)
			this.rest();
		else if (nextActivity == 0)
			this.activeActivity = 0;
	}

	/**
	 * Variable registering the current activity
	 * <ul>
	 * <li>0: nothing</li>
	 * <li>1: working</li>
	 * <li>2: falling</li>
	 * <li>3: moving</li>
	 * <li>4: resting</li>
	 * <li>5: attacking</li>
	 * <li>6: defending</li>
	 * </ul>
	 */
	private int activeActivity = 0;

	/**
	 * Variable registering the next activity
	 * <ul>
	 * <li>0: nothing</li>
	 * <li>1: working</li>
	 * <li>2: falling</li>
	 * <li>3: moving</li>
	 * <li>4: resting</li>
	 * <li>5: attacking</li>
	 * <li>6: defending</li>
	 * </ul>
	 */
	private int nextActivity = 0;

	/**
	 * Variable registering the end time
	 */
	private double remainingTimeToFinishWork;

	/**
	 * Variable registering the remaining time the unit is busy attacking.
	 */
	private double remainingTimeToFinishAttack;

	////////////////////////////////////////////////////////////////
	/////////////////////// * BASIC MOVEMENT *///////////////////////
	////////////////////////////////////////////////////////////////

	/**
	 * Variable registering the base speed of this unit.
	 */
	private double baseSpeed;

	/**
	 * Variable registering the speed of this unit
	 */
	private double speed;

	/**
	 * Variable registering whether this unit is sprinting or not.
	 */
	private boolean sprinting;

	/**
	 * Return the base speed of this unit.
	 */
	@Basic
	@Raw
	private double getBaseSpeed() {
		return this.baseSpeed;
	}

	private void setBaseSpeed() {
		this.baseSpeed = 3 * (this.getStrength() + this.getAgility()) / (double) (4 * this.getTotalWeight());
	}

	/**
	 * Return the speed of this unit.
	 */
	@Basic
	@Raw
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * Initiates a sprint from this unit.
	 * 
	 * @post | if (this.stamina > 0 && (this.activeActivity == "move" ||
	 *       this.targetCube != null)) | then this.isSprinting() == true
	 */
	public void startSprinting() {
		if (this.stamina > 0 && (this.activeActivity == 3 || this.targetCube != null))
			this.sprinting = true;
	}

	/**
	 * End the sprint of this unit
	 */
	public void stopSprinting() {
		this.sprinting = false;
	}

	/**
	 * Return whether this unit is sprinting or not.
	 */
	public boolean isSprinting() {
		return this.sprinting;
	}

	/**
	 * Set the speed of this unit.
	 * 
	 * @param targetPosition
	 *            The position the unit is going to
	 * @post | if (activeActivity != "move") | then this.speed = 0; | else | if
	 *       zDifference == -1 | then new.getSpeed() == this.getBaseSpeed()/2 |
	 *       else if zDifference == 1 | then new.getSpeed() ==
	 *       this.getBaseSpeed()*1.2 | else | then new.getSpeed() ==
	 *       this.getBaseSpeed() | if this.isSprinting() | new.getSpeed() ==
	 *       this.getSpeed()*2
	 * 
	 */
	private void setSpeed(Vector targetPosition) {
		// if (activeActivity != "move")
		// this.speed = 0;
		// else{
		double heightDifference = Vector.heightDifference(this.position, targetPosition);
		if (Util.fuzzyGreaterThanOrEqualTo(heightDifference,-1))
			this.speed = this.getBaseSpeed() / 2;
		else if (Util.fuzzyGreaterThanOrEqualTo(heightDifference, 1))
			this.speed = this.getBaseSpeed() * 1.2;
		else {
			this.speed = this.getBaseSpeed();
		}
		if (this.sprinting)
			this.speed = this.speed * 2;
		// }
	}

	/**
	 * Move this unit to an adjacent cube
	 * 
	 * @param dx
	 *            difference between adjacent cube and current cube in x
	 *            direction
	 * @param dy
	 *            difference between adjacent cube and current cube in y
	 *            direction
	 * @param dz
	 *            difference between adjacent cube and current cube in z
	 *            direction
	 * @post The new units occupation is moving to an adjacent cube |
	 *       new.getCube()[0] == this.getCube()[0] + dx + .5 | new.getCube()[1]
	 *       == this.getCube()[1] + dy + .5 | new.getCube()[2] ==
	 *       this.getCube()[2] + dz + .5
	 * 
	 * @throws IllegalArgumentException
	 *             "move" is not a valid activity for this unit or
	 *             targetPosition is not a valid position for this unit |
	 *             !isValidActivity("move") || !isValidPosition(targetPosition)
	 * @throws IllegalArgumentExeption
	 *             targetPosition is not a valid position |
	 *             !isValidPosition(targetPosition)
	 */
	public void moveToAdjacent(Cube target) {
		Vector targetPosition = target.getCenterOfCube();
		if (!isValidActivity(3) || !this.world.isPositionInWorld(targetPosition)
				|| !targetPosition.getEnclosingCube(getWorld()).isPassable()) {
			throw new IllegalArgumentException();
		}
		if (activeActivity != 3) {
			activeActivity = 3;
			this.setTargetPosition(targetPosition);
			this.setBaseSpeed();
		}
		if (this.getTargetCube() == null)
			this.increaseExperience(1);
	}

	/**
	 * Variable registering how many stamina the unit has burned.
	 */

	private double exhaustedPoints;

	/**
	 * Move this unit a step to its target position.
	 * 
	 * @param tickTime
	 *            The time the tick lasts.
	 * @throws IllegalBlockSizeException 
	 * 
	 * @post The unit has moved a step to its target position. |
	 *       new.getPosition() == this.getPosition + (this.speed *
	 *       this.getTargetPosition - this.getPosition) / distance
	 * @throws IllegalArgumentExeption
	 *             The new position is not a valid position |
	 *             !isValidPosition(new.getPosition)
	 */
	private void doMove(double tickTime) {
		if (sprinting) {
			double oldExhaustedPoints = exhaustedPoints;
			exhaustedPoints = exhaustedPoints + tickTime / 0.1;
			stamina = stamina + (int) (oldExhaustedPoints) - (int) (exhaustedPoints);
			if (stamina <= 0)
				this.sprinting = false;
		}
		this.setSpeed(this.targetPosition);
		double d = Vector.distanceBetween(targetPosition, position);

		double movedDistanceRelatieveToRemainingDistance = tickTime * speed / d;
		if (Util.fuzzyGreaterThanOrEqualTo(movedDistanceRelatieveToRemainingDistance, 1)) {
			this.setPosition(this.targetPosition);
			this.increaseExperience(1);
			this.exhaustedPoints = 0;
			if (this.getCube().equals(this.targetCube)) {
				this.increaseExperience(1);
				System.out.println("targetCube op null zetten");
				this.sprinting = false;
				this.targetCube = null;
				this.exhaustedPoints = 0;
				this.activeActivity = 0;
				this.speed = 0;
			}
			this.startNextActivity();
		} else {
			Vector difference = Vector.getVectorFromTo(this.position, this.targetPosition);
			this.position = Vector.sum(this.position, difference.scale(movedDistanceRelatieveToRemainingDistance));
			this.orientation = difference.orientationInXZPlane();
		}
	}

	/**
	 * Return whether this unit is moving or not
	 */
	public boolean isMoving() {
		if (activeActivity == 3)
			return true;
		return false;
	}
	/////////////////////////////////////////////////
	///////////////// * ORIENTATION */////////////////
	/////////////////////////////////////////////////

	/**
	 * Return the orientation of this unit.
	 */
	@Basic
	@Raw
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Turn this unit to the opponent
	 * 
	 * @param opponent
	 *            The opponent this unit is fighting with
	 * 
	 * @post this unit is turned to the opponent | new.orientation =
	 *       Math.atan2(opponent.getDoublePosition()[1] -
	 *       this.getDoublePosition()[1] , opponent.getDoublePosition()[0] -
	 *       this.getDoublePosition()[0]);
	 */
	private void face(Vector point) {
		Vector direction = Vector.getVectorFromTo(this.position, point);
		this.orientation = direction.orientationInXZPlane();
	}

	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation;

	///////////////////////////////////////////////////////
	///////////////// * EXTENDED MOVEMENT */////////////////
	///////////////////////////////////////////////////////

	/**
	 * Move the unit to cube
	 * 
	 * @param cube
	 *            the target cube this unit is going to
	 * 
	 * @post cube is set as targetCube | new.setTargetCube(cube)
	 * @throws IllegalArgumentExpetion
	 *             cube is not a valid cube | !isValidCube(cube)
	 * 
	 */
	public void moveTo(Cube cube) {
		if (cube == null)
			throw new ClassCastException();
		this.setTargetCube(cube);
		activeActivity = 0;
		System.out.println("target set");
	}

	/**
	 * Move this unit to an adjacent cube
	 * 
	 * @param dx
	 *            difference between adjacent cube and current cube in x
	 *            direction
	 * @param dy
	 *            difference between adjacent cube and current cube in y
	 *            direction
	 * @param dz
	 *            difference between adjacent cube and current cube in z
	 *            direction
	 * 
	 * @post The unit is moved to an adjacent cube | new.getCube()[0] ==
	 *       this.getCube()[0] + dx + .5 | new.getCube()[1] == this.getCube()[1]
	 *       + dy + .5 | new.getCube()[2] == this.getCube()[2] + dz + .5
	 * 
	 * @throws IllegalArgumentException
	 *             "move" is not a valid activity for this unit or
	 *             targetPosition is not a valid position for this unit |
	 *             !isValidActivity("move") || !isValidPosition(targetPosition)
	 * @throws IllegalArgumentException()
	 *             targetPosition is not a valid position |
	 *             !isValidPosition(targetPosition)
	 */
	private void doMoveTo() {
		List<Cube> path = Pathfinding.getPath(getCube(), this.targetCube, getWorld());
		if (path == null) {
			this.targetCube = null;
		} else {
			Cube target = path.get(path.size() - 2);
			this.moveToAdjacent(target);
		}
	}

	/* Working */

	/**
	 * Pick up the given material
	 * 
	 * @param material
	 *            The material to pick up
	 * 
	 * @post this material is no longer in the world of this unit.
	 * 
	 * @post this unit's weight is the sum of its own weight and the material's
	 *       weight.
	 */
	private void pickupMaterial(Material material) {
		/// **
		// * Change the activity from this unit to work
		// *
		// * @post If work is a valid activity for this unit and its previous
		/// activity
		// * was not work, activeActivity is changed to "work" and endTime
		// * is set to the right value.
		// * | if (isValidActivity("work") && activeActivity != "work")
		// * | then activeActivity = "work"
		// * | new.endTime = this.getCurrentTime() +
		// * | 500/(double)(this.getStrength())
		// * @throws IllegalArgumentException
		// * "work" is not a valid activity for this unit
		// * | !this.isValidActivity("work")
		// */
		// public void work() throws IllegalArgumentException {
		// if (!isValidActivity("work")){
		// this.nextActivity = "work";
		// throw new IllegalArgumentException();
		// }
		// if (activeActivity != "work"){
		// activeActivity = "work";
		// this.endTime = this.getCurrentTime() +
		/// 500/(double)(this.getStrength());
		// }
		// }
		//
		/// **
		// * Set the carried material of this unit to the given material.
		// */
		// public void setCarriedMaterial(Material material) {
		if (material instanceof Log)
			this.carriedMaterial = 2;
		else if (material instanceof Boulder)
			this.carriedMaterial = 1;
		this.setAdditionalWeight(material.getWeight());
		this.getWorld().removeMaterial(material);
	}

	/**
	 * Return the carried material of this unit. Returns "Log" for a log and
	 * "Boulder" for a boulder. Returns null if this unit is not carrying any
	 * material.
	 */
	public int getCarriedMaterial() {
		return this.carriedMaterial;
	}

	/**
	 * Return whether this unit is carrying material.
	 */
	private boolean isCarryingMaterial() {
		if (carriedMaterial == 0)
			return false;
		return true;
	}

	/**
	 * Return whether this unit is carrying a log.
	 */
	public boolean isCarryingLog() {
		return (this.carriedMaterial == 2);
	}

	/**
	 * Return whether this unit is carrying a boulder.
	 */
	public boolean isCarryingBoulder() {
		return (this.carriedMaterial == 1);
	}

	/**
	 * Variable registering what material this unit is carrying.
	 *
	 * loadTypes: 0: nothing 1: boulder 2: log
	 */
	private int carriedMaterial = 0;
	private Cube cubeWorkingOn = null;

	public void workAt(Cube cube) {
		if (!getCube().isNeighbourCube(cube) && !getCube().equals(cube))
			return;
		if (!isValidActivity(1)) {
			if (this.activeActivity != 1) {
				this.nextActivity = 1;
				this.cubeWorkingOn = cube;
			}
			throw new IllegalArgumentException();
		}
		if (activeActivity != 1 || !cubeWorkingOn.equals(cube)) {
			activeActivity = 1;
			this.remainingTimeToFinishWork = 500 / (double) (this.getStrength());
			this.cubeWorkingOn = cube;
			this.face(cube.getCenterOfCube());
		}

	}

	/**
	 * Start the next activity
	 * 
	 * @post If endTime is not passed yet, a next activity will begin | if
	 *       (Util.fuzzyGreaterThanOrEqualTo(this.getCurrentTime(), endTime))
	 *       this.startNextActivity();
	 */
	private void doWork(double tickTime) {
		this.remainingTimeToFinishWork = this.remainingTimeToFinishWork - tickTime;
		if (this.remainingTimeToFinishWork < 0) {
			if (this.isCarryingMaterial()) {
				this.dropMaterial(cubeWorkingOn.getCenterOfCube());
			} else if (this.getWorld().isWorkshopWithLogAndBoulder(cubeWorkingOn)) {
				List<Material> materialsAtCube = this.getWorld().getMaterialsAt(cubeWorkingOn);
				Log log = null;
				Boulder boulder = null;
				for (Material material : materialsAtCube) {
					if (material instanceof Log)
						log = (Log) material;
					if (material instanceof Boulder)
						boulder = (Boulder) material;
				}
				if (!(boulder == null || log == null)) {
					this.getWorld().removeMaterial(boulder);
					this.getWorld().removeMaterial(log);
					this.setWeight(this.getWeight() + 5);
					this.setToughness(this.getToughness() + 5);
				}
			} else if (this.getWorld().materialToPickUp(cubeWorkingOn) != null) {
				System.out.println("pickingMaterialUp");
				this.pickupMaterial(this.getWorld().materialToPickUp(cubeWorkingOn));
			} else if (cubeWorkingOn.getTerrainType() == 2) {
				new Log(cubeWorkingOn.getCenterOfCube(), this.getWorld());
				this.getWorld().setTerrainType(cubeWorkingOn, 0);
			} else if (cubeWorkingOn.getTerrainType() == 1) {
				new Boulder(cubeWorkingOn.getCenterOfCube(), this.getWorld());
				this.getWorld().setTerrainType(cubeWorkingOn, 0);
			} else {
				this.startNextActivity();
				return;
			}
			this.increaseExperience(10);
			this.startNextActivity();
		}
	}

	/**
	 * Drop the material this unit is carrying.
	 * 
	 * @post if this unit was carrying a log, this log will now be part of the
	 *       world with position as position. The same for a boulder. This unit
	 *       is not carrying any material.
	 */
	private void dropMaterial(Vector position) {
		if (!position.getEnclosingCube(this.getWorld()).isSolid()) {
			if (this.getCarriedMaterial() == 2) {
				new Log(position, this.getWorld(), this.getAdditionalWeight());
				// this.getWorld().addMaterial(log); //gebeurt al in Log zelf
				this.setAdditionalWeight(0);
			} else if (this.carriedMaterial == 1) {
				new Boulder(position, this.getWorld(), this.getAdditionalWeight());
				// this.getWorld().addMaterial(boulder);
				this.setAdditionalWeight(0);
			}
			this.carriedMaterial = 0;
		}
	}

	/**
	 * Return whether this unit is working or not
	 */
	public boolean isWorking() {
		return (this.activeActivity == 1);
	}

	/* Attacking */

	/**
	 * Initiate an attack against unit
	 * 
	 * @param unit
	 *            The opponent for the attack
	 * @post If this unit is close enough to the opponent and if this unit is
	 *       not attacking, activeActivity will be changed in "attack". Both the
	 *       units will face each other and the opponent will defend itself. |
	 *       if ((unit != this) && ((this.getCube() == unit.getCube()) | ||
	 *       (this.isNeighbourCube(unit.getCube()))) && | (!this.isAttacking()))
	 *       | then new.activityStartTime = this.getCurrentTime();
	 *       new.activeActivity = "attack"; this.faceOpponent(unit);
	 *       unit.faceOpponent(this); unit.defenseAgainst(this);
	 * 
	 */
	public void attack(Unit defender) {
		if (defender != this
				&& (this.getCube() == defender.getCube() || this.getCube().isNeighbourCube(defender.getCube()))
				&& !this.isAttacking()) {
			System.out.println("attack");
			this.remainingTimeToFinishAttack = 1;
			this.activeActivity = 5;

			this.face(defender.getPosition());
			defender.face(this.getPosition());
			defender.defenseAgainst(this);
		}
	}

	/**
	 * Execute the attack
	 * 
	 * @post if the current time is greater than the ending time, the next
	 *       activity will be started | if (this.getCurrentTime() >=
	 *       activityStartTime + 1){ this.startNextActivity()
	 */
	private void doAttack(double tickTime) {
		this.remainingTimeToFinishAttack = this.remainingTimeToFinishAttack - tickTime;
		if (this.remainingTimeToFinishAttack < 0) {
			this.startNextActivity();
		}
	}

	/**
	 * Return whether this unit is attacking or not
	 */
	public boolean isAttacking() {
		return (this.activeActivity == 5);
	}

	/**
	 * Defend against unit
	 * 
	 * @param unit
	 *            the unit who is attacking this unit
	 * @post | if Math.random() < dodgeChance 
	 * 		new.getPosition ==
	 *       this.getPosition + random this.getOrientation = unit.getOrientation
	 *       this.increaseExperience(20) | else if (!Math.random() <
	 *       blockChance) new.getHitpoints() == this.getHitpoints() -
	 *       unit.getStrength()/10 | else this.increaseExperience(20)
	 * 
	 */
	private void defenseAgainst(Unit attacker) {
		System.out.println("defend");
		if (this.getTask() != null) {
			this.getTask().taskFailed();
		}
		this.activeActivity = 6;
		double blockChance = 0.25 * (this.getStrength() + this.getAgility())
				/ (attacker.getAgility() + attacker.getStrength());
		double dodgeChance = 0.2 * this.getAgility() / (double) attacker.getAgility();
		if (Math.random() < dodgeChance) {
			List<Cube> randomCubesList = new ArrayList<Cube>();
			randomCubesList.addAll(this.getCube().getAccessibleNeigbours());
			Collections.shuffle(randomCubesList);

			for (Cube cube : randomCubesList) {
				Vector newPosition = cube.getCentreOfCube();
				try {
					this.setPosition(newPosition);
					break;
				} catch (IllegalArgumentException e) {
				}
			}
			this.face(attacker.getPosition());
			attacker.face(this.getPosition());
			this.increaseExperience(20);
		} else if (!(Math.random() < blockChance)) {
			attacker.increaseExperience(20);
			this.setHitpoints(this.getHitpoints() - attacker.getStrength() / 10);
		} else
			this.increaseExperience(20);
	}

	/* Resting */

	/**
	 * Variable registering the hitpoints this unit has recovered.
	 */
	private double recoverdPoints;

	/**
	 * Set the activity of this unit to resting.
	 * 
	 * @post The activity of this new unit is equal to resting. |
	 *       new.isResting() == True
	 * @throws IllegalArgumentException
	 *             Resting is not a valid activity for this unit. | !
	 *             isValitActivity("rest")
	 */
	public void rest() throws IllegalArgumentException {
		if (!isValidActivity(4)) {
			this.nextActivity = 4;
			throw new IllegalArgumentException();
		}
		if (activeActivity != 4) {
			recoverdPoints = 0;
			this.activeActivity = 4;
		}
		if (this.getTask() != null) {
			this.getTask().taskFailed();
		}
	}

	/**
	 * Let this unit rest
	 * 
	 * @post | if (this.hitpoints != this.getMaxHitpoints()) | then
	 *       new.hitpoints == this.hitpoints + (this.getCurrentTime() |
	 *       -activityStartTime)*this.getToughness()/200/0.2 | else if
	 *       (this.stamina != this.getMaxStamina()) | then new.stamina ==
	 *       this.stamina + (this.getCurrentTime()
	 *       -activityStartTime)*this.getToughness()/100/0.2 | else if
	 *       (this.hitpoints == this.getMaxHitpoints() && this.stamina | ==
	 *       this.getMaxStamina() | then this.startNextActivity()
	 */
	private void doRest(double tickTime) {
		double oldRecoverdPoints = recoverdPoints;
		recoverdPoints = oldRecoverdPoints + tickTime * this.getToughness() / 200 / 0.2;
		if (Util.fuzzyGreaterThanOrEqualTo(recoverdPoints, 1)) {
			this.timeSinceLastRested = 0;
			if (hitpoints != getMaxHitpoints()) {
				hitpoints = hitpoints - (int) (oldRecoverdPoints) + (int) (recoverdPoints);
				if (hitpoints > getMaxHitpoints())
					hitpoints = getMaxHitpoints();
			} else if (stamina != getMaxStamina()) {
				stamina = stamina - (int) (oldRecoverdPoints * 2) + (int) (recoverdPoints * 2);
				if (stamina > getMaxStamina())
					stamina = getMaxStamina();
			}
			if (hitpoints == getMaxHitpoints() && stamina == getMaxStamina()) {
				this.startNextActivity();
			}

		}
	}

	/**
	 * Return whether this unit is resting or not.
	 */
	public boolean isResting() {
		return (this.activeActivity == 4);
	}

	/* Default behavior */

	/**
	 * Variable registering whether this unit's behavior is default or not.
	 */
	private boolean defaultBehavior;

	/**
	 * Switch the behavior of this unit to default or not default.
	 * 
	 * @param behavior
	 *            true if set to default, false if set to not default
	 * @post behavior is default if true, else not default | new.defaultBehavior
	 *       = behavior
	 */
	@Raw
	public void setDefaultBehavior(boolean behavior) {
		this.defaultBehavior = behavior;
	}

	/**
	 * Return whether the behavior is default or not
	 */
	public boolean getDefaultBehavior() {
		return this.defaultBehavior;
	}

	/**
	 * Change the behavior of this unit to default
	 * 
	 * @post this unit's new activity is a random activity. | new.activeActivity
	 *       = random
	 * @throws IllegalArgumentException
	 *             if newTargetCube is not a valid cube |
	 *             (!isValidCube(targetCube))
	 */
	private void doDefaultBehavior(double tickTime) {
		if (activeActivity == 3 && !sprinting && Math.random() < 0.05) {
			this.sprinting = true;
		} else if (activeActivity == 0) {
			if (this.getTask() != null) {
				this.getTask().advanceProgram(tickTime);
			} else {
				this.setTask(this.getFaction().getScheduler().ascribeTask(this));
				if (this.getTask() != null) {
					this.getTask().advanceProgram(tickTime);
				} 
//				else {
//					int randomActivity = (int) (Math.random() * 3);
//					if (randomActivity == 0) {
//						Cube newTargetCube = world.generateRandomValidPosition();
//						this.setTargetCube(newTargetCube);
//
//					} else if (randomActivity == 1) {
//						List<Cube> randomCubesList = new ArrayList<Cube>();
//						randomCubesList.addAll(this.getCube().getNeighbourCubes());
//						Collections.shuffle(randomCubesList);
//						this.workAt(randomCubesList.get(0));
//					} else if (randomActivity == 2
//							&& (hitpoints != this.getMaxHitpoints() || stamina != getMaxStamina())) {
//						this.rest();
//					}
//				}
			}
		}
	}

	/**
	 * Variable registering the Y-component of the beginning cube.
	 */
	private int fellFrom;

	/**
	 * Variable registering fall speed.
	 */
	private final static Vector fallSpeed = new Vector(0, 0, -3);

	/**
	 * If this unit is not falling, check whether it should be falling and
	 * initiate the fall if needed. If this unit is falling, check whether it
	 * should stop falling and stop the fall if needed.
	 * 
	 * @post if this unit was falling and had a solid block underneath, it is
	 *       not falling anymore. Its hitpoints will be decreased with 10 times
	 *       the fallen cubes. If this unit was not falling and hadn't a solid
	 *       block underneath, it is falling.
	 */
	private void falling(double tickTime) {
		if (this.activeActivity != 2) {
			if (!this.position.hasSupportOfSolid(this.world)) {
				this.fellFrom = getCube().getPosition().get(2);
				this.activeActivity = 2;
			}
		}
		if (this.activeActivity == 2) {
			if (this.position.hasSupportOfSolidUnderneath(this.world)) {
				this.position = getCube().getCenterOfCube();
				int cubesFallen = this.fellFrom - getCube().getPosition().get(2);
				this.setHitpoints(this.hitpoints - 10 * (cubesFallen));
				this.startNextActivity();
			} else {
				this.position = Vector.sum(this.position, fallSpeed.scale(tickTime));
			}
		}

	}
	
	/**
	 * Variable registering the task of this unit.
	 */
	public Task task;
	
	/**
	 * Set the task of this unit to the given task
	 * 
	 * @param task
	 * 		The new task for this unit
	 * 
	 * @post the task of this unit is equal to the given task
	 * 		| new.getTask == task
	 */
	@Raw
	public void setTask(Task task) {
		this.task = task;
	}
	
	/**
	 * Return the task of this unit
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * Execute the task
	 *  //FIXME
	 * @param tickTime
	 */
	public void executeTask(double tickTime) {
		this.getTask().advanceProgram(tickTime);
	}

}