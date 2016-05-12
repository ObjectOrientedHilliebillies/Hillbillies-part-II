package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hillbillies.model.statements.SequenceStatement;
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
	public Task(String name, int priority, Statement activity) {
		this.setName(name);
		this.setPriority(priority);
		this.setActivity(activity);
	}
	
	public Task(String name, int priority, Statement activity, int[] pos) {
		this.setName(name);
		this.setPriority(priority);
		this.setActivity(activity);
		World world = this.getExecutor().getWorld();
		Cube cube = world.getCube(pos);
		this.setCube(cube);
	}
	
	private Unit executor;
	
	public void setExecutor(Unit unit){
		this.executor = unit;
		unit.setTask(this);
	}
	
	public Unit getExecutor(){
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
	
	private List<Statement> statements;
	
	public Statement getNextStatement() {
		return statementes.get(next).execute(this);
	}
	
	private Cube cube;
	
	private void setCube(Cube cube) {
		this.cube = cube;
	}
	
	private Cube getCube() {
		return this.cube;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	private Set<Scheduler> schedulers;
	
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
	
	public void setOccupied() {
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
		return getPriority().compareTo(other.getPriority())
	}

	public void taskSucceeded(Task task) {
		this.activeList.remove(task);
		this.managedTasks.remove(task);
	}
	
	public void taskFailed(Task task) {
		this.activeList.remove(task);
		task.reducePriority();
		this.addTask(task);
	}
	
	public void setActivitys(Statement statement) {
		if (statement instanceof SequenceStatement) {
			statements= statement.getAsList();
			for (Statement subStatement : statements){
				SubTask subTask = new SubTask(subStatement, getCube(), this);
				subTask.advance(consumedTime);
			}
		}
	}
	
	private 
	
	public void advanceProgram(){
		
	}

}
