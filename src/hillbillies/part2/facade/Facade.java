package hillbillies.part2.facade;

import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.Vector;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
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
		System.out.println("getPosition");
		return unit.getDoublePosition();
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		System.out.println("getCubeCoordinate");
		return unit.getCube();
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		System.out.println("getName");
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
		System.out.println("getWeight");
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		System.out.println("setWeight");
		unit.setWeight(newValue);
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		System.out.println("getStrength");
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		System.out.println("setStrength");
		unit.setStrength(newValue);
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		System.out.println("getAgility");
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		System.out.println("setAgility");
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		System.out.println("getToughness");
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		System.out.println("setToughness");
		unit.setToughness(newValue);
	}
	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		System.out.println("getMaxHitPoints");
		return unit.getMaxHitpoints();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		System.out.println("getCurrentHitPoints");
		return unit.getHitpoints();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		System.out.println("getMaxStaminaPoints");
		return unit.getMaxStamina();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		System.out.println("getCurrentStaminaPoints");
		return unit.getStamina();
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		System.out.println("advanceTime");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		System.out.println("moveToAdjacent");
		Vector positionDifference = new Vector(dx, dy, dz);	
		unit.moveToAdjacent(positionDifference);
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		System.out.println("getCurrentSpeed");
		return unit.getSpeed();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		System.out.println("isMoving");
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
		System.out.println("isSprinting");
		return unit.isSprinting();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		System.out.println("getOrientation");
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		System.out.println("moveTo");
		unit.moveTo(cube);		
	}

	@Override
	public void work(Unit unit) throws ModelException {
		System.out.println("work");
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		System.out.println("isWorking");
		return unit.isWorking();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		System.out.println("fight");
		attacker.attack(defender);	
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		System.out.println("isAttacking");
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		System.out.println("rest");
		unit.rest();		
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		System.out.println("isResting");
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		System.out.println("setDefaultBehaviorEnabled");
		unit.setDefaultBehavior(value);
		System.out.println(value);
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		System.out.println("isDefaultBehaviorEnabled");
		return unit.getDefaultBehavior();
	}

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		System.out.println("createWorld");
		return new World(terrainTypes, modelListener);
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		System.out.println("getNbCubesX");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		System.out.println("getNbCubesY");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		System.out.println("getNbCubesZ");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		System.out.println("advanceTime");
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		System.out.println("getCubeType");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		System.out.println("setCubeType");
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		System.out.println("isSolidConnectedToBorder");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		System.out.println("spawnUnit");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		System.out.println("addUnit");
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		System.out.println("getUnits");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		System.out.println("isCarryingLog");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		System.out.println("isCarryingBoulder");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		System.out.println("isAlive");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		System.out.println("getExperiencePoints");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		System.out.println("workAt");
		// TODO Auto-generated method stub
		
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		System.out.println("getFaction");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		System.out.println("getUnitsOfFaction");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		System.out.println("getActiveFactions");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		System.out.println("getPosition");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		System.out.println("getBoulders");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		System.out.println("getPosition");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		System.out.println("getLogs");
		// TODO Auto-generated method stub
		return null;
	}

}
