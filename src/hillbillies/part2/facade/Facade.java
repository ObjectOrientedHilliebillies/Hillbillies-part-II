package hillbillies.part2.facade;

import java.util.Set;

public class Facade {
		
		/* WORLD */
	
		public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException;

		public int getNbCubesX(World world) throws ModelException;

		public int getNbCubesY(World world) throws ModelException;

		public int getNbCubesZ(World world) throws ModelException;

		public void advanceTime(World world, double dt) throws ModelException;

		public int getCubeType(World world, int x, int y, int z) throws ModelException;

		public void setCubeType(World world, int x, int y, int z, int value) throws ModelException;

		public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException;

		/* UNITS */

		public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException;

		public void addUnit(Unit unit, World world) throws ModelException;

		public Set<Unit> getUnits(World world) throws ModelException;

		public boolean isCarryingLog(Unit unit) throws ModelException;

		public boolean isCarryingBoulder(Unit unit) throws ModelException;

		public boolean isAlive(Unit unit) throws ModelException;

		public int getExperiencePoints(Unit unit) throws ModelException;

		public void workAt(Unit unit, int x, int y, int z) throws ModelException;

		@Override
		@Deprecated
		default void work(Unit unit) throws ModelException {
			throw new NoSuchMethodError("This method no longer needs to be supported");
		}

		@Override
		@Deprecated
		default void advanceTime(Unit unit, double dt) throws ModelException {
			throw new NoSuchMethodError("This method no longer needs to be supported");
		}

		/* FACTIONS */

		public Faction getFaction(Unit unit) throws ModelException;

		public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException;

		public Set<Faction> getActiveFactions(World world) throws ModelException;

		/* BOULDERS */

		public double[] getPosition(Boulder boulder) throws ModelException;

		public Set<Boulder> getBoulders(World world) throws ModelException;

		/* LOGS */

		public double[] getPosition(Log log) throws ModelException;

		public Set<Log> getLogs(World world) throws ModelException;

	}

}
