package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.statements.Statement;

public class Task implements Comparable<Task>{
	
	/**
	 * Initialize this new task with the given name and priority.
	 * @param name
	 * 		The name of this new task.
	 * @param priority
	 * 		The priority of this new task.
	 * @post The name of this new task is equal to the given name.
	 * 		| new.getName() == name
	 * @post The priority of this new task is equal to the given priority.
	 * 		| new.getPriority() == priority
	 */
	public Task(String name, int priority, Statement statement) {
		this.setName(name);
		this.setPriority(priority);
		this.setStatement(statement);
	}
	
	public Task(String name, int priority, Statement statement, int[] pos) {
		this.setName(name);
		this.setPriority(priority);
		this.setStatement(statement);
		this.setPos(pos);
		//World world = this.getExecutor().getWorld();
		//Cube cube = world.getCube(pos);
		//this.setCube(cube);
	}
	
	private int[] pos = null;
	private void setPos(int[] pos) {
		this.pos = pos;
	}
	private int[] getPos() {
		return this.pos;
	}
	
	
	private Unit executor;
	
	public void setExecutor(Unit unit){
		this.executor = unit;
		if (unit != null){
			unit.setTask(this);	
			World world = unit.getWorld();
			if (this.getPos() != null) {
				Cube cube = world.getCube(this.getPos());
				this.setCube(cube);
			}
		}
	}
	
	public Unit getExecutor(){
		System.out.println(executor.toString());
		return this.executor;
	}
	
	
	private Integer priority;
	
	private void setPriority(int priority) {
		this.priority = priority;
	}
	
	public Integer getPriority() {
		return this.priority;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName()  {
		return this.name;
	}
	
	private Cube cube;
	
	private void setCube(Cube cube) {
		this.cube = cube;
	}
	
	public Cube getCube() {
		return this.cube;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	private Set<Scheduler> schedulers = new HashSet<>();
	
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	public void reducePriority() {
		this.setPriority(this.getPriority()/2);
	}
	
	/**
	 * Returns whether this task is already being executed or not.
	 * @return True if the task is already being executed.
	 */
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
	private boolean isOccupied;
	
	public void setOccupied(Unit unit) {
		setExecutor(unit);
		this.isOccupied = true;
	}
	
	public void setAvailable() {
		this.isOccupied = false;
	}
	
	/**
	 * Compare this task amount with the other task.
	 * 
	 * @param other
	 * 		The other task
	 * @return The result is equal to the  comparison of the priority
	 * 			of this task and the other task.
	 * @throws ClassCastException
	 * 			The other task is not effective.
	 */
	@Override
	public int compareTo(Task other) {
		if (other == null){
			throw new ClassCastException();
		}
		return getPriority().compareTo(other.getPriority());
	}

	public void taskSucceeded() {
		for (Scheduler scheduler: this.schedulers){
			scheduler.removeTask(this);
		}
		
	}
	
	public void taskFailed() {
		setAvailable();
		reducePriority();
		this.subTask = null;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	private Statement statement;
	private SubTask subTask = null;
	
	public void advanceProgram(double timeLeft){
		if (subTask == null){
			subTask = new SubTask(statement, cube, false);
		}
		if (subTask.advance(timeLeft) != -1){
			taskSucceeded();
		}
	}

}
