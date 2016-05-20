package hillbillies.part3.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.TaskFactory;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;
import hillbillies.model.statements.Statement;
import hillbillies.model.task.Task;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import ogp.framework.util.ModelException;

public class Facade implements IFacade {

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, 
			int agility, int strength, int toughness,boolean enableDefaultBehavior) 
					throws ModelException {	
		System.out.println("createUnit");
		return new Unit(name, initialPosition, weight, agility, strength, toughness, enableDefaultBehavior);
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		return unit.getDoublePosition();
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		System.out.println("getCubeCoordinate");
		Cube unitCube = unit.getCube();
		int[] result = {unitCube.getPosition().get(0),
						unitCube.getPosition().get(1),
						unitCube.getPosition().get(2)};
		return result;
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		System.out.println("setName");
		try {unit.setName(newName);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException();
		}
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		System.out.println("setWeight");
		unit.setWeight(newValue);
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		System.out.println("setStrength");
		unit.setStrength(newValue);
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		System.out.println("setAgility");
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		System.out.println("setToughness");
		unit.setToughness(newValue);
	}
	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxHitpoints();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return unit.getHitpoints();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxStamina();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return unit.getStamina();
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		int x = unit.getCube().getXGrit()+dx;
		int y = unit.getCube().getYGrit()+dy;
		int z = unit.getCube().getZGrit()+dz;
		Cube target;
		try{
			target = unit.getWorld().getCube(x, y, z);
		}catch (NullPointerException e){
			throw new ModelException();
		}
		try{
			unit.moveToAdjacent(target);
		}catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getSpeed();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		System.out.println("startSprinting");
		unit.startSprinting();
		
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		System.out.println("stopSprinting");
		unit.stopSprinting();		
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		unit.moveTo(unit.getWorld().getCube(cube));		
	}
	
	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		System.out.println("fight");
		attacker.attack(defender);	
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		System.out.println("rest");
		try{
		unit.rest();
		} catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException { 
		unit.setDefaultBehavior(value);
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.getDefaultBehavior();
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		System.out.println("createWorld");
		return new World(terrainTypes, modelListener);
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getNbCubesX();
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getNbCubesY();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getNbCubesZ();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		world.advanceTime(dt);		
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		try{
			return world.getCube(x, y, z).getTerrainType();
		}catch (NullPointerException e){
			throw new ModelException();
		}
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		try{
			world.setTerrainType(world.getCube(x, y, z), value);
		}catch (NullPointerException e){
			throw new ModelException();
		}catch (IllegalArgumentException e){
			throw new ModelException();
		}
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		return world.isSolidConnectedToBorder(x, y, z);
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		return world.spawnUnit(enableDefaultBehavior);
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		world.addUnit(unit);
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		try {
			return world.getUnits();
		} catch (NullPointerException e){
			throw new ModelException();
		}
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return unit.isAlive();
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getExperience();
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		List<Integer> cubeList = new ArrayList<>();
		cubeList.add(x);
		cubeList.add(y);
		cubeList.add(z);
		try{
		unit.workAt(unit.getWorld().getCube(cubeList));
		}catch (IllegalArgumentException e){
			throw new ModelException();
		}
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getUnitsInFaction();
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		return world.getActiveFactions();
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		Vector position = boulder.getPosition();
		return position.getVector();
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		try {
			return world.getBoulders();
		} catch (NullPointerException e){
			throw new ModelException();
		}
		
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		Vector position = log.getPosition();
		return position.getVector();
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		try {
			return world.getLogs();
		} catch (NullPointerException e){
			throw new ModelException();
		}
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		unit.advanceTime(dt);
	}

	@Override
	public void work(Unit unit) throws ModelException {
		// Not implemented
	}

	@Override
	public ITaskFactory<Expression, Statement, Task> createTaskFactory() {
		return new TaskFactory();
	}

	@Override
	public boolean isWellFormed(Task task) throws ModelException {
		return task.isWellFormed();
	}

	@Override
	public Scheduler getScheduler(Faction faction) throws ModelException {
		return faction.getScheduler();
	}

	@Override
	public void schedule(Scheduler scheduler, Task task) throws ModelException {
		scheduler.addTask(task);
		
	}

	@Override
	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
		scheduler.replace(original, replacement);
	}

	@Override
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
		return scheduler.areTasksPartOf(tasks);
	}

	@Override
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
		return scheduler.iterator();
	}

	@Override
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
		return task.getSchedulers();
	}

	@Override
	public Unit getAssignedUnit(Task task) throws ModelException {
		return task.getExecutor();
	}

	@Override
	public Task getAssignedTask(Unit unit) throws ModelException {
		return unit.getTask();
	}

	@Override
	public String getName(Task task) throws ModelException {
		return task.getName();
	}

	@Override
	public int getPriority(Task task) throws ModelException {
		return task.getPriority();
	}

}
