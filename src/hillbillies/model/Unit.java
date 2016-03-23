package hillbillies.model;

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

import java.util.Arrays;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.ModelException;
import ogp.framework.util.Util;

// FIXME Als de strength ofzo veranderd kan het zijn dat de unit zijn weight 
//			niet meer legaal is.
// FIXME Er zijn bugs als je meerder units hebt
// FIXME Als je omhoog gaat veranderd alles in Nan !?
// FIXME Na 3 min gameTime crashed het
// FIXME MoveTo start niet zo gemakkelijk.
// TODO Defence is nog niet echt getest
// FIXME isSprinting geeft soms fout aan
// FIXME Een unit kan nog niet sterven.
// FIXME ALLE PUBLIEKE METHODEN MOETEN EEN TEST HEBBEN!!!


/**
 * @invar  The position of each unit must be a valid position for any
 *         unit.
 *       | isValidPosition(getPosition())
 * @invar  The weight of each unit must be a valid weight for any
 *         unit.
 *       | isValidWeight(getWeight())
 *       
 * @invar  The strength of each unit must be a valid strength for any
 *         unit.
 *       | isValidStrength(getStrength())
 *       
 * @invar  The agility of each unit must be a valid agility for any
 *         unit.
 *       | isValidAgility(getAgility())
 *       
 * @invar  The toughness of each unit must be a valid toughness for any
 *         unit.
 *       | isValidToughness(getToughness())
 *       
 * @invar  The stamina of each unit must be a valid stamina for any
 *         unit.
 *       | isValidStamina(getStamina())
 *  
 * @invar  The hitpoints of each Unit must be a valid hitpoints for any
 *         Unit.
 *       | isValidHitpoints(getHitpoints())
 * @invar  The orientation of each unit must be a valid orientation for any
 *         unit.
 *       | isValidOrientation(getOrientation())
 *       
 * @version 1.0
 * @author  Jonas Vantrappen & Victor Van Eetvelt
 */

public class Unit {

/* Unit creation */
	/**
 * Initialize this new unit with given name, given initialPosition, given weight, given agility, 
 * given strength, given toughness, given enableDefaultBehavior, maximal hitpoints, maximal stamina,
 * default orientation.
 * 
 * @param  name
 *         The name for this new unit.
 * @param  initialPosition
 *         The initial position for this new unit.
 * @param  weight
 *         The weight for this new unit.
 * @param  agility
 *         The agility for this new unit.
 * @param  strength
 *         The strength for this new unit.
 * @param  toughness
 *         The toughness for this new unit.  
 * @param  enableDefaultBehavior
 *         Enables default behavior for this new unit.
 * @throws ModelException 
 * @pre    The maximal hitpoints must be a valid hitpoints for any Unit.
 *       | isValidHitpoints(this.getMaxHitpoints)
 * @pre    The given stamina must be a valid stamina for any unit.
 *       | isValidStamina(stamina)
 * @effect The position of this new unit is set to
 *         the given position.
 *       | this.setPosition(position)
 * @post   If the given weight is a valid weight for any unit,
 *         the weight of this new unit is equal to the given
 *         weight. Otherwise, the weight of this new unit is equal
 *         to (strength+agility)/2.
 *       | if (isValidWeight(weight))
 *       |   then new.getWeight() == weight
 *       |   else new.getWeight() == (strength+agility)/2
 * @post   If the given agility is a valid agility for any unit,
 *         the agility of this new unit is equal to the given
 *         agility. Otherwise, the agility of this new unit is equal
 *         to 25.
 *       | if (isValidAgility(agility))
 *       |   then new.getAgility() == agility
 *       |   else new.getAgility() == 25
 * @post   If the given strength is a valid strength for any unit,
 *         the strength of this new unit is equal to the given
 *         strength. Otherwise, the strength of this new unit is equal
 *         to 25.
 *       | if (isValidStrength(strength))
 *       |   then new.getStrength() == strength
 *       |   else new.getStrength() == 25
 * @post   If the given toughness is a valid toughness for any unit,
 *         the toughness of this new unit is equal to the given
 *         toughness. Otherwise, the toughness of this new unit is equal
 *         to 25.
 *       | if (isValidToughness(toughness))
 *       |   then new.getToughness() == toughness
 *       |   else new.getToughness() == 25
 * @post   The hitpoints of this new Unit is equal to the maximal
 *         hitpoints.
 *       | new.getHitpoints() == this.getMaxHitpoints
 * @post   The stamina of this new unit is equal to the given
 *         stamina.
 *       | new.getStamina() == stamina
 * @post  The orientation of this new unit is equal
 *         to PI/2.
 *       | if (isValidOrientation(orientation))
 *       | new.getOrientation() == PI/2
 */
public Unit(String name, int[] initialCube, int weight, int agility, int strength, int toughness,
		boolean enableDefaultBehavior)
		throws IllegalArgumentException, ModelException {
	this.setName(name);
	
	try {
		Vector position = Vector.getCentreOfCube(initialCube);
		this.setPosition(position);
	} catch (ModelException e) {
		// TODO Auto-generated catch block. EN GAAN WE DAN GEEN DEFAULT POSITIE SETTEN?
		e.printStackTrace();
	}
	if (isValidWeight(weight))
		this.weight = weight;
	else 
		this.weight = this.getMinWeight();
	if (! isValidAgility(agility))
		agility = 25;
	else
		this.setAgility(agility);	
	if (! isValidStrength(strength))
		strength = 25;
	else
		this.setStrength(strength);
	if (! isValidToughness(toughness))
		toughness = 25;
	else
		this.setToughness(toughness);
	if (!isValidWeight(weight))
		this.weight = this.getMinWeight();
	else 
		this.setWeight(weight);
	this.setDefaultBehavior(enableDefaultBehavior);
	
	setHitpoints(getMaxHitpoints()-5);
	setStamina(getMaxStamina()-5);
	
	this.orientation = (Math.PI/2);
}

/**
 * Variable registering the world of this unit.
 */
private World world;

/**
 * Return the world of this unit.
 */
public World getWorld(){
	return this.world;
}


////////////////////////////////////////////////////
////////////////////* POSITION *////////////////////
////////////////////////////////////////////////////

/**
 * Variable registering the position of this unit.
 */
private Vector position;

/**
 * Variable registering the target cube of this Unit.
 */
private int[] targetCube;

/**
 * Variable registering the target position of this Unit.
 */
private Vector targetPosition;


/**
 * Return the position of this unit.
 */
@Basic @Raw
public Vector getPosition() {
	return this.position;
}

/**
 * Return the position of this unit.
 */
@Basic @Raw
public double[] getDoublePosition() {
	return this.position.getVector();
}

/**
 * Set the position of this unit to the given position.
 * 
 * @param  position
 *         The new position for this unit.
 * @post   The position of this new unit is equal to
 *         the given position.
 *       | new.getPosition() == position
 * @throws ModelException
 *         The given position is not a valid position for any
 *         unit.
 *       | ! isValidPosition(this.getPosition())
 */
@Raw
public void setPosition(Vector position) 
		throws ModelException {
	if (! this.world.isPositionInWorld(position))
		throw new ModelException();
	this.position = position;
}

/**
 * Return the target position of this unit.
 */
@Basic @Raw
public Vector getTargetPosition() {
	return this.targetPosition;
}

/**
 * Set the target position of this unit to the given target position.
 * 
 * @param  position
 *         The new position for this unit.
 * @post   The target position of this unit is equal to
 *         the given target position.
 *       | new.getTargetPosition() == targetPosition
 * @throws ModelException
 *         The given target position is not a valid target position for any
 *         unit.
 *       | ! isValidPosition(this.getPosition())
 */
@Raw
public void setTargetPosition(Vector targetPosition) throws ModelException {
	if (! this.world.isPositionInWorld(targetPosition))
		throw new ModelException();
	this.targetPosition = targetPosition;
}

/**
 * Return the cube of this unit.
 */
@Basic @Raw
public int[] getCube() {
	return this.position.getIntCube();
}

/**
 * Return the target cube of this unit.
 */
@Basic @Raw
public int[] getTargetCube() {
	return this.targetCube;
}

/**
 * Set the cube of this unit to the given cube.
 * 
 * @param  cube
 *         The new cube for this unit.
 * @post   The cube of this new unit is equal to
 *         the given cube.
 *       | new.getPosition() == cube
 * @throws IllegalPositionException
 *         The given cube is not a valid position for any
 *         unit.
 *       | ! isValidPosition(getCube())
 */
@Raw
public void setTargetCube(int[] cube) 
		throws ModelException {
	if (! this.world.isCubeInWorld(cube))
		throw new ModelException();
	this.targetCube = cube;
}

////////////////////////////////////////////////
////////////////////* NAME *////////////////////
////////////////////////////////////////////////

/**
 * Return the name of this unit.
 */
@Basic @Raw
public String getName() {
	return this.unitName;
}

/**
 * Check whether the given name is a valid name for
 * any unit.
 *  
 * @param  name
 *         The name to check.
 * @return 
 *       | result == Character.isUpperCase(name.charAt(0)) && name.length() >= 2 
				&& name.matches("[a-zA-Z ']")
*/
public static boolean isValidName(String unitName) {
	return Character.isUpperCase(unitName.charAt(0)) && unitName.length() >= 2 
			&& unitName.matches("[a-zA-Z '\"]+");
}

/**
 * Set the name of this unit to the given name.
 * 
 * @param  unitName
 *         The new name for this unit.
 * @post   The name of this new unit is equal to
 *         the given name.
 *       | new.getName() == unitName
 * @throws IllegalArgumentException
 *         The given name is not a valid name for any
 *         unit.
 *       | ! isValidName(getName())
 */
@Raw
public void setName(String unitName) 
		throws IllegalArgumentException {
	if (! isValidName(unitName))
		throw new IllegalArgumentException();
	this.unitName = unitName;
}

/**
 * Variable registering the name of this unit.
 */
private String unitName;

////////////////////////////////////////////////////////
/////////////////////* ATTRIBUTES */////////////////////
////////////////////////////////////////////////////////
/**
 * Return the weight of this unit.
 */
@Basic @Raw
public int getWeight() {
	return this.weight;
}

/**
 * Check whether the given weight is a valid weight for
 * any unit.
 *  
 * @param  weight
 *         The weight to check.
 * @return 
 *       | result == maxWeight > weight >= (strength+agility)/2 
*/
public boolean isValidWeight(int weight) {
	return (weight >= this.getMinWeight() 
			&& weight <= maxWeight);
}

/**
 * Set the weight of this unit to the given weight.
 * 
 * @param  weight
 *         The new weight for this unit.
 * @post   If the given weight is a valid weight for any unit,
 *         the weight of this new unit is equal to the given
 *         weight.
 *       | if (isValidWeight(weight))
 *       |   then new.getWeight() == weight
 *       | else
 *       |	 new.getWeight() == minWeight
 */
@Raw
public void setWeight(int weight) {
	if (isValidWeight(weight))
		this.weight = weight;
	else 
		this.weight = this.getMinWeight();
}

/**
 * Variable registering the weight of this unit.
 */
private int weight;

/**
 * Variable registering the maximum weight of this unit.
 */
private static int maxWeight = 200;

public int getMinWeight() {
	return (this.getStrength() + this.getAgility())/2;
}

/**
 * Return the strength of this unit.
 */
@Basic @Raw
public int getStrength() {
	return this.strength;
}

/**
 * Check whether the given strength is a valid strength for
 * any unit.
 *  
 * @param  strength
 *         The strength to check.
 * @return 
 *       | result == 0 < unitStrength <= maxStrength
*/
public static boolean isValidStrength(int strength) {
	return (0 < strength && strength <= maxStrength);
}

/**
 * Set the strength of this unit to the given strength.
 * 
 * @param  strength
 *         The new strength for this unit.
 * @post   If the given strength is a valid strength for any unit,
 *         the strength of this new unit is equal to the given
 *         strength.
 *       | if (isValidStrength(strength))
 *       |   then new.getStrength() == strength
 */
@Raw
public void setStrength(int strength) {
	if (isValidStrength(strength))
		this.strength = strength;
}


/**
 * Variable registering the strength of this unit.
 */
private int strength;

/**
 * Variable registering the maximum strength of this unit.
 */
private static int maxStrength = 200;

/**
 * Return the agility of this unit.
 */
@Basic @Raw
public int getAgility() {
	return this.agility;
}

/**
 * Check whether the given agility is a valid agility for
 * any unit.
 *  
 * @param  agility
 *         The agility to check.
 * @return 
 *       | result == 0 < agility <= maxAgility
*/
public static boolean isValidAgility(int agility) {
	return (0 < agility && agility <= maxAgility);
}

/**
 * Set the agility of this unit to the given agility.
 * 
 * @param  agility
 *         The new agility for this unit.
 * @post   If the given agility is a valid agility for any unit,
 *         the agility of this new unit is equal to the given
 *         agility.
 *       | if (isValidAgility(agility))
 *       |   then new.getAgility() == agility
 */
@Raw
public void setAgility(int agility) {
	if (isValidAgility(agility))
		this.agility = agility;
}

/**
 * Variable registering the agility of this unit.
 */
private int agility;

/**
 * Variable registering the maximum agility of this unit.
 */
private static int maxAgility = 200;

/**
 * Return the toughness of this unit.
 */
@Basic @Raw
public int getToughness() {
	return this.toughness;
}

/**
 * Check whether the given toughness is a valid toughness for
 * any unit.
 *  
 * @param  toughness
 *         The toughness to check.
 * @return 
 *       | result == 0 < toughness <= maxToughness
*/
public static boolean isValidToughness(int toughness) {
	return (0 < toughness && toughness <= maxToughness);
}

/**
 * Set the toughness of this unit to the given toughness.
 * 
 * @param  toughness
 *         The new toughness for this unit.
 * @post   If the given toughness is a valid toughness for any unit,
 *         the toughness of this new unit is equal to the given
 *         toughness.
 *       | if (isValidToughness(toughness))
 *       |   then new.getToughness() == toughness
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
 * Check whether the given experience is a valid experience for
 * any unit.
 *  
 * @param  experience
 *         The experience to check.
 * @return 
 *       | result == 0 < experience
*/
public static boolean isValidExperience(int experience) {
	if (experience >= 0)
		return true;
	return false;
}

/**
 * Set the experience of this unit to the given experience.
 * 
 * @param  experience
 *         The new experience for this unit.
 * @post   If the given experience is a valid experience for any unit,
 *         the experience of this new unit is equal to the given
 *         experience.
 *       | if (isValidExperience(experience))
 *       |   then new.getExperience() == experience
 */
@Raw
public void setExperience(int experience) {
	if (isValidExperience(experience))
		if (!(experience >= 10))
			this.experience = experience;
		else{ // TODO checken met facade welke we moeten ophogen, random?
			int points;
			points = this.getExperience()/10;
			this.setExperience(this.getExperience()%10);
			this.setStrength(getStrength()+points);}
}

/**
 * Variable registering the experience of this unit.
 */
private int experience;



/* Points */
/**
 * Return the stamina of this unit.
 */
@Basic @Raw
public int getStamina() {
	return this.stamina;
}

/**
 * Check whether the given stamina is a valid stamina for
 * any unit.
 *  
 * @param  stamina
 *         The stamina to check.
 * @return 
 *       | result == 0 < stamina < this.getMaxStamina()
*/
public boolean isValidStamina(int stamina) {
	return (0 < stamina && stamina < this.getMaxStamina());
}

/**
 * Set the stamina of this unit to the given stamina.
 * 
 * @param  stamina
 *         The new stamina for this unit.
 * @pre    The given stamina must be a valid stamina for any
 *         unit.
 *       | isValidStamina(stamina)
 * @post   The stamina of this unit is equal to the given
 *         stamina.
 *       | new.getStamina() == stamina
 */
@Raw
public void setStamina(int stamina) {
	assert isValidStamina(stamina);
	this.stamina = stamina;
}

/**
 * Return the maximal stamina of this unit.
 */
@Basic @Raw
public int getMaxStamina() {
	int maxStamina = this.getWeight()*this.getToughness()/50;
	if ((this.getWeight()*this.getToughness())%50 == 0)
		return maxStamina;
	return maxStamina+1;
}

/**
 * Variable registering the stamina of this unit.
 */
private int stamina;

/**
 * Return the hitpoints of this Unit.
 */
@Basic @Raw
public int getHitpoints() {
	return this.hitpoints;
}

/**
 * Check whether the given hitpoints is a valid hitpoints for
 * any Unit.
 *  
 * @param  hitpoints
 *         The hitpoints to check.
 * @return 
 *       | result == 0 < hitpoints <= getMaxHitpoints()
*/
public boolean isValidHitpoints(int hitpoints) {
	return ((0 < hitpoints) && (hitpoints <= this.getMaxHitpoints()));
}

/**
 * Set the hitpoints of this Unit to the given hitpoints.
 * 
 * @param  hitpoints
 *         The new hitpoints for this Unit.
 * @pre    The given hitpoints must be a valid hitpoints for any
 *         Unit.
 *       | isValidHitpoints(hitpoints)
 * @post   The hitpoints of this Unit is equal to the given
 *         hitpoints.
 *       | new.getHitpoints() == hitpoints
 */
@Raw
public void setHitpoints(int hitpoints) {
	assert isValidHitpoints(hitpoints);
	this.hitpoints = hitpoints;
}

/**
 * Variable registering the hitpoints of this Unit.
 */
private int hitpoints;

/**
 * Return the maximum hitpoints of a unit. 
 */
@Basic @Raw
public int getMaxHitpoints() {
	int maxHitpoint = this.getWeight()*this.getToughness()/50;
	if ((this.getWeight()*this.getToughness())%50 == 0)
		return maxHitpoint;
	return maxHitpoint+1;
}

////////////////////////////////////////////////////
/////////////////////* MOVING */////////////////////
////////////////////////////////////////////////////

/**
 * Return the current speed of this unit.
 */
@Basic @Raw
public double getCurrentSpeed() {
	return 3*(this.getStrength() + this.getAgility())/(4*this.getWeight());
}


//////////////////////////////////////////////////
/////////////////////* TIME */////////////////////
//////////////////////////////////////////////////

// No documentation required for advanceTime
public void advanceTime(double tickTime) throws IllegalArgumentException, ModelException {
	if (!isValidTickTime(tickTime)){
		//System.out.println(tickTime);
		throw new IllegalArgumentException();
	}
	else{
		this.setTime(this.currentTime + tickTime);
		
	
	if (getCurrentTime()-lastTimeRested >= 180 && this.isValidActivity("rest")){
			this.rest();
			//System.out.println("3 min zijn om");
		}
		
	if (this.activeActivity == null && (this.targetCube != null) && 
				!Vector.equals(this.getCube(), this.targetCube)){
		doMoveTo();
	}
	
	if (isWorking())
		doWork();
	else if (isResting())
		doRest();
	else if (this.isAttacking())
		this.doAttack();
	else if (this.isMoving())
		doMove(tickTime);
	else 
		this.speed=0;
	}
	if (this.getDefaultBehavior())
		doDefaultBehavior();
}

/**
 * Check whether the given tick time is a valid tick time.
 *  
 * @param  tickTime
 *         The tick time to check.
 * @return 
 *       |  | result == (0 < tickTime) && (tickTime < maxTimeLapse)
*/
public boolean isValidTickTime(double tickTime) {
	return ((0 < tickTime) && (Util.fuzzyGreaterThanOrEqualTo( maxTimeLapse, tickTime)));

}

/**
 * Return the current time
 */
public double getCurrentTime() {
	return this.currentTime;
}

/**
 * Set the time to the given time.
 * 
 * @param  time
 *         The new time.
 * @post   The time is equal to the given time
 *       | new.getTime() == time
 *  //FIXME moet dit niet checken of het een valid time is en een exception throwen?
 */
@Raw
public void setTime(double time) {
	this.currentTime = time;
}

/**
 * Start a new activity.
 * 
 * @post	The new activity is started.
 * 			| if nextActivity == "null"
 * 				activeActivity == "null"
 * 			| else if nextActivity == "work"
 * 				isWorking == true
 * 			| else if nextActivity == "rest"
 * 				isResting == true
 */
public void startNextActivity(){
	if (nextActivity == null)
		activeActivity = null;
	else if (nextActivity == "work")
		this.work();
	else if (nextActivity == "rest")
		this.rest();
	
	nextActivity = null;
}

/**
 * Variable registering the current time
 */
private double currentTime;

/**
 * Variable registering the maximum time interval
 */
private double maxTimeLapse = 0.2;

/**
 * Variable registering the current activity
 */
private String activeActivity;

/**
 * Variable registering the next activity
 */
private String nextActivity;

/**
 * Variable registering the end time
 */
private double endTime;

/**
 * Variable registering the start time of the current activity
 */
private double activityStartTime;

/**
 * Variable registering the last time this unit rested
 */
private double lastTimeRested =0.2;

////////////////////////////////////////////////////////////////
///////////////////////* BASIC MOVEMENT *///////////////////////
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
@Basic @Raw
public double getBaseSpeed() {
	return this.baseSpeed;
}

//FIXME da's een louche functienaam, kunnen we 
//		dat niet beter in getBaseSpeed zetten?
public void setBaseSpeed(){
	this.baseSpeed = 3*(this.getStrength() + this.getAgility())/(double) (4*this.getWeight());
}

/**
 * Return the speed of this unit.
 */
@Basic @Raw
public double getSpeed() {
	return this.speed;
}

/**
 * Initiates a sprint from this unit.
 * 
 * @post 
 * 		| if (this.stamina > 0 && (this.activeActivity == "move" 
 * 								|| this.targetCube != null))
 * 		| 		then this.isSprinting() == true
 */
public void startSprinting(){
	if (this.stamina > 0 && (this.activeActivity == "move" || this.targetCube != null))
			this.sprinting = true;
}

/**
 * End the sprint of this unit
 */
public void stopSprinting(){
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
 * 			The position the unit is going to
 * @post
 * 		| if (activeActivity != "move")
 *		|		then this.speed = 0; 
 *		| else
 *		|		if zDifference == -1 //TODO ik weet niet of dit mag
 *		|			then new.getSpeed() == this.getBaseSpeed()/2
 *		|		else if zDifference == 1
 *		|			then new.getSpeed() == this.getBaseSpeed()*1.2
 *		|		else 
 *		|			then new.getSpeed() == this.getBaseSpeed()
 *		| if this.isSprinting()
 *		|		new.getSpeed() == this.getSpeed()*2
 *				
 */
public void setSpeed(Vector targetPosition) {
//	if (activeActivity != "move")
//		this.speed = 0;
//	else{ 
		double heightDifference = Vector.heightDifference(this.position, targetPosition);
		if (heightDifference == -1)
			this.speed = this.getBaseSpeed()/2;
		else if (heightDifference == 1)
			this.speed = this.getBaseSpeed()*1.2;
		else{
			this.speed = this.getBaseSpeed();
		}
		if (this.sprinting)
			this.speed = this.speed * 2;
//	}
}


/**
 * Move this unit to an adjacent cube
 * 
 * @param dx
 * 		difference between adjacent cube and current cube in x direction
 * @param dy
 * 		difference between adjacent cube and current cube in y direction
 * @param dz
 * 		difference between adjacent cube and current cube in z direction
 * @post The new units occupation is moving to an adjacent cube
 * 		| new.getCube()[0] == this.getCube()[0] + dx + .5
 * 		| new.getCube()[1] == this.getCube()[1] + dy + .5
 * 		| new.getCube()[2] == this.getCube()[2] + dz + .5
 * 
 * @throws IllegalArgumentException
 * 		"move" is not a valid activity for this unit 
 * 			or targetPosition is not a valid position for this unit
 * 		| !isValidActivity("move") || !isValidPosition(targetPosition)
 * @throws ModelException
 * 		targetPosition is not a valid position
 * 		| !isValidPosition(targetPosition)
 */
public void moveToAdjacent(Vector positionDifference)
		throws IllegalArgumentException, ModelException {
	Vector targetPosition = Vector.addVectors(Vector.getCentreOfCube(this.getCube()),
			positionDifference);
	if (!isValidActivity("move") || !this.world.isPositionInWorld(targetPosition)){
		this.nextActivity = "move";
		throw new IllegalArgumentException();
	}
	if (activeActivity != "move"){
		activeActivity = "move";
		this.setTargetPosition(targetPosition);
		this.setBaseSpeed();
	}
	if (this.getTargetCube() == null)
		this.setExperience(this.getExperience() + 1);
}

/**
 * Variable registering how many stamina the unit has burned.
 */
 
private double exhaustedPoints;

/**
 * Move this unit a step to its target position.
 * 
 * @param tickTime
 * 		The time the tick lasts.
 * 
 * @post The unit has moved a step to its target position. //TODO experience ophogen
 * 		| new.getPosition() == this.getPosition
 * 								+ (this.speed * this.getTargetPosition - this.getPosition)
 *									/ distance
 * @throws ModelException
 * 		The new position is not a valid position
 * 		| !isValidPosition(new.getPosition)
 */
public void doMove(double tickTime) throws ModelException {
	if (sprinting){
		double oldExhaustedPoints = exhaustedPoints;
		exhaustedPoints = exhaustedPoints + tickTime/0.1;
		stamina = stamina + (int) (oldExhaustedPoints) - (int) (exhaustedPoints);
		if (stamina <= 0)
			this.sprinting = false;
		// TODO If the unit has some stamina left but is is not enough so sprint the whole tick, then the unit doesn't sprint at all.
	}
	
	this.setSpeed(this.targetPosition);
	double d = Vector.distanceBetween(targetPosition, position);
	
	double movedDistanceRelatieveToRemainingDistance = tickTime*speed/d;
	if (Util.fuzzyGreaterThanOrEqualTo(movedDistanceRelatieveToRemainingDistance, 1)){
		this.setPosition(this.targetPosition);
		if (Arrays.equals(this.getCube(), this.targetCube)){
			this.setExperience(this.executedSteps + this.getExperience());
			System.out.println("targetCube op null zetten");
			this.sprinting = false;
			this.targetCube = null;
			this.exhaustedPoints = 0;
			this.executedSteps = 0;
		}
		this.startNextActivity();
	}
	else{
		Vector difference = Vector.getVectorFromTo(this.position, this.targetPosition);
		Vector newPosition = Vector.addVectors(this.position, 
			Vector.multiply(difference, movedDistanceRelatieveToRemainingDistance));
		this.orientation = Vector.orientationInXZPlane(difference);
	}
}

public int executedSteps;

/**
 * Return whether this unit is moving or not
 */
public boolean isMoving(){
	if (activeActivity == "move")
		return true;
	return false;
}
/////////////////////////////////////////////////
/////////////////* ORIENTATION */////////////////
/////////////////////////////////////////////////

/**
 * Return the orientation of this unit.
 */
@Basic @Raw
public double getOrientation() {
	return this.orientation;
}

/**
 * Turn this unit to the opponent
 * @param opponent
 * 		The opponent this unit is fighting with
 * 
 * @post this unit is turned to the opponent
 * 		| new.orientation = Math.atan2(opponent.getDoublePosition()[1] - this.getDoublePosition()[1]
									, opponent.getDoublePosition()[0] - this.getDoublePosition()[0]);
 */
public void faceOpponent(Unit opponent){
	this.orientation = Vector.orientationInXZPlane(Vector.getVectorFromTo(opponent.position,
																		this.position));
}

/**
 * Variable registering the orientation of this unit.
 */
private double orientation;

///////////////////////////////////////////////////////
/////////////////* EXTENDED MOVEMENT */////////////////
///////////////////////////////////////////////////////

/**
 * Move the unit to cube
 * @param cube
 * 		the target cube this unit is going to
 * 
 * @post cube is set as targetCube
 * 		| new.setTargetCube(cube)
 * @throws ModelException
 * 		cube is not a valid cube
 * 		| !isValidCube(cube)
 * 		
 */
public void moveTo(int[] cube) throws ModelException{
	if (!this.world.isCubeInWorld(cube))
		throw new ModelException();
	this.setTargetCube(cube);
	System.out.println("target set");
//	if (this.isValidActivity("move"))
//		this.activeActivity = "move";
}

/**
* Move this unit to an adjacent cube
* 
* @param dx
* 		difference between adjacent cube and current cube in x direction
* @param dy
* 		difference between adjacent cube and current cube in y direction
* @param dz
* 		difference between adjacent cube and current cube in z direction
* 
* @post The unit is moved to an adjacent cube
* 		| new.getCube()[0] == this.getCube()[0] + dx + .5
* 		| new.getCube()[1] == this.getCube()[1] + dy + .5
* 		| new.getCube()[2] == this.getCube()[2] + dz + .5
* 
* @throws IllegalArgumentException
* 		"move" is not a valid activity for this unit 
* 			or targetPosition is not a valid position for this unit
* 		| !isValidActivity("move") || !isValidPosition(targetPosition)
* @throws ModelException
* 		targetPosition is not a valid position
* 		| !isValidPosition(targetPosition)
*/
public void doMoveTo() throws IllegalArgumentException, ModelException{
	
	Vector difference = Vector.getOneCubeCloserToCube(this.targetCube);
	for (int i=0; i != 3; i++){
		if (this.getCube()[i] == this.getTargetCube()[i])
			difference[i] = 0;
		else if (this.getCube()[i] < this.getTargetCube()[i])
			difference[i] = 1;
		else {
			difference[i] = -1;
		
		}
	}
	this.moveToAdjacent(difference[0], difference[1], difference[2]);
}



/* Working */

/**
 * Change the activity from this unit to work
 * 
 * @post If work is a valid activity for this unit and its previous activity 
 * 			was not work, activeActivity is changed to "work" and endTime 
 * 			is set to the right value.
 * 		| if (isValidActivity("work") && activeActivity != "work")
 * 		| 		then activeActivity = "work"
 * 		|		new.endTime = this.getCurrentTime() + 
 * 		|			500/(double)(this.getStrength())
 * @throws IllegalArgumentException
 * 		"work" is not a valid activity for this unit
 * 		| !this.isValidActivity("work")
 */
public void work() throws IllegalArgumentException {
	if (!isValidActivity("work")){
		this.nextActivity = "work";
		throw new IllegalArgumentException();
	}
	if (activeActivity != "work"){
		activeActivity = "work";
		this.endTime = this.getCurrentTime() + 500/(double)(this.getStrength());
	}
}

/**
 *  Start the next activity
 *  
 *  @post If endTime is not passed yet, a next activity will begin
 *  		| if (Util.fuzzyGreaterThanOrEqualTo(this.getCurrentTime(), endTime))
					this.startNextActivity();
 */
public void doWork() {
	if (Util.fuzzyGreaterThanOrEqualTo(this.getCurrentTime(), endTime))
		this.startNextActivity();
}

/**
 * Return whether this unit is working or not
 */
public boolean isWorking() {
	if (this.activeActivity == "work")
		return true;
	return false;
}

/* Attacking */

/**
 * Initiate an attack against unit
 * 
 * @param unit
 * 		The opponent for the attack
 * @post If this unit is close enough to the opponent and if this unit is not 
 * 			attacking, activeActivity will be changed in "attack". Both the
 * 			units will face each other and the opponent will defend itself.
 * 		| if ((unit != this) && ((this.getCube() == unit.getCube()) 
 * 		|			|| (this.isNeighbourCube(unit.getCube()))) && 
 *		|				(!this.isAttacking()))
 *		| 		then new.activityStartTime = this.getCurrentTime();
 *						new.activeActivity = "attack";
 *						this.faceOpponent(unit);
 *						unit.faceOpponent(this);
 *						unit.defenseAgainst(this);
 *						
 */
public void attack(Unit unit){
	if ((unit != this) && 
		((this.getCube() == unit.getCube()) || (this.isNeighbourCube(unit.getCube()))) && 
		(!this.isAttacking())){
			
		System.out.println("attack");
		
		this.activityStartTime = this.getCurrentTime();
		this.activeActivity = "attack";
		this.faceOpponent(unit);
		unit.faceOpponent(this);
		unit.defenseAgainst(this);
		// FIXME een methode setActivieActivity maken!
	}
}

/**
 *  Execute the attack
 *  
 *  @post if the current time is greater than the ending time, the next
 *  		activity will be started
 *  		| if (this.getCurrentTime() >= activityStartTime + 1){
					this.startNextActivity()
 */
public void doAttack(){
	if (this.getCurrentTime() >= activityStartTime + 1){
		this.startNextActivity();
	}
}

/**
 * Return whether this unit is attacking or not
 */
public boolean isAttacking() {
	if (this.activeActivity == "attack")
		return true;
	else
		return false;
}

/**
 * Return whether this unit is under attack or not.
 */
public boolean isUnderAttack() {
	if (this.activeActivity == "defend")
		return true;
	else
		return false;
}

/**
 * Defend against unit
 * 
 * @param unit
 * 		the unit who is attacking this unit
 * @post //TODO experience toevoegen aan post
 * 		| if Math.random() < dodgeChance 
 * 				 new.getPosition == this.getPosition + random
 * 				 this.getOrientation = unit.getOrientation
 * 		| else if (!Math.random() < blockChance)
 * 				 new.getHitpoints() == this.getHitpoints() - unit.getStrength()/10
 * 				 
 * 		
 */
public void defenseAgainst(Unit unit) {	
	System.out.println("defend");
	this.activeActivity = "defend";
	double blockChance = 0.25*(unit.getStrength() + unit.getAgility())/
						(this.getAgility() + this.getStrength());
	double dodgeChance = 0.2*unit.getAgility()/(double) this.getAgility();
	
	if (Math.random() <  dodgeChance){
		this.setExperience(this.getExperience() + 20);
		double[] newPosition = new double[3];
		int[] random = new int[3];
		do {
		for (int i=0; i != 3; i++){
			do {
				random[i] = (int) (Math.random() * 3) - 1;
				newPosition[i] = this.getPosition()[i] + random[i];
			} while (!isValidComponent(newPosition[i]));
			// Fixme deze math in een andere classe steken :)
		}
		} while (random[0] == 0 && random[1] == 0 && random[2] == 0);
		try {
			this.setPosition(newPosition);
		} catch (ModelException e) {
			System.out.println("If this happend you broke the matrix");
		}
		this.faceOpponent(unit);
		unit.faceOpponent(this);
	}
	else if (!(Math.random() < blockChance)) {
		this.setExperience(this.getExperience() + 20);
		this.setHitpoints(this.getHitpoints() - unit.getStrength()/10);}
	else
		unit.setExperience(this.getExperience() + 20);
}

/* Resting */

/**
 * Variable registering the hitpoints this unit has recovered.
 */
private double recoverdPoints;

/**
 * Check whether the given activity is a valid activity for this unit.
 * @param  activity
 *         The activity to check.
 * @return 
 *       | result == !(this.isResting() && recoverdPoints<1)
*/
public boolean isValidActivity(String activity){
	if (this.isResting() && recoverdPoints<1)
		return false;
	if (this.activeActivity == "move")
		return false;
	if (this.activeActivity == "attack")
		return false;
	return true;
}

/**
 * Set the activity of this unit to resting.
 * @post   The activity of this new unit is equal to
 *         resting.
 *       | new.isResting() == True
 * @throws IllegalArgumentException
 *         Resting is not a valid activity for this unit.
 *       | ! isValitActivity("rest")
 */
public void rest() throws IllegalArgumentException{
	if (!isValidActivity("rest")){
		this.nextActivity = "rest";
		throw new IllegalArgumentException();
	}
	if (activeActivity != "rest"){
		recoverdPoints = 0;
		activityStartTime = this.getCurrentTime();
		this.activeActivity = "rest";
	}
}

/**
 * Let this unit rest
 * 
 * @post 
 * 		| if (this.hitpoints != this.getMaxHitpoints())
 * 		|		then new.hitpoints == this.hitpoints + (this.getCurrentTime()
 * 		|				-activityStartTime)*this.getToughness()/200/0.2
 * 		| else if (this.stamina != this.getMaxStamina())
 * 		|		then new.stamina == this.stamina + (this.getCurrentTime()
 * 						-activityStartTime)*this.getToughness()/100/0.2
 * 		| else if (this.hitpoints == this.getMaxHitpoints() && this.stamina
 * 		|				== this.getMaxStamina()
 * 		| 		then this.startNextActivity()
 */
public void doRest() {
	double oldRecoverdPoints = recoverdPoints;
	recoverdPoints = (this.getCurrentTime()-activityStartTime)*this.getToughness()/200/0.2;
	if (Util.fuzzyGreaterThanOrEqualTo(recoverdPoints,1)){
		lastTimeRested = getCurrentTime();
		if (hitpoints != getMaxHitpoints()){
			hitpoints = hitpoints - (int) (oldRecoverdPoints) + (int) (recoverdPoints);
			if (hitpoints > getMaxHitpoints())
				hitpoints = getMaxHitpoints();
		}
		else if (stamina != getMaxStamina()){
			stamina = stamina - (int) (oldRecoverdPoints * 2) + (int) (recoverdPoints*2);
			if (stamina > getMaxStamina())
				stamina = getMaxStamina();
		}
		if (hitpoints == getMaxHitpoints() && stamina == getMaxStamina()){
			this.startNextActivity();
		}
			
	}
}

/**
 * Return whether this unit is resting or not.
 */
public boolean isResting() {
	if (this.activeActivity == "rest")
		return true;
	else 
		return false;
}

/* Default behavior */

/**
 * Variable registering whether this unit's behavior is default or not.
 */
private boolean defaultBehavior;

/**
 * Switch the behavior of this unit to default or not default.
 * 
 * @param  behavior
 *         true if set to default, false if set to not default
 * @post   behavior is default if true, else not default
 *       | new.defaultBehavior = behavior
 */
@Raw
public void setDefaultBehavior(boolean behavior){
	this.defaultBehavior = behavior;
}

/**
 * Return whether the behavior is default or not
 */
public boolean getDefaultBehavior(){
	return this.defaultBehavior;
}

/**
 * Change the behavior of this unit to default
 * 
 * @post this unit's new activity is a random activity.
 * 		| new.activeActivity = random
 * @throws ModelException
 * 		if newTargetCube is not a valid cube
 * 		| (!isValidCube(targetCube))
 */
public void doDefaultBehavior() throws ModelException{
	
	if (activeActivity == "move" && !sprinting && Math.random()<0.05){
		this.sprinting = true;
	}
	else if (activeActivity == null) {
		int randomActivity = (int) (Math.random() * 3);
		if (randomActivity == 0){
			int[] newTargetCube = new int[3];		
			for (int i=0; i != 3; i++){
				newTargetCube[i] = (int) (Math.random() * 50);
				}
			this.setTargetCube(newTargetCube);
					
		}else if (randomActivity == 1) {
			this.work();
		}else if (randomActivity == 2 && 
				(hitpoints != this.getMaxHitpoints() || stamina != getMaxStamina())){
			this.rest();
		}else 
			this.doDefaultBehavior();
		}
	}
	
	
}