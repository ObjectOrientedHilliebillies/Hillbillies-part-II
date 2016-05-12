package hillbillies.model;

import java.util.Set;

import hillbillies.model.statements.Statement;

public class Task { //implements Comparable<Task>{
	
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
	
	private void setActivity(Statement activity) {
		this.activity = activity;
	}
	
	private Statement activity;
	
	public Statement getActivity() {
		return this.activity;
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
	 * Check whether this task is equal to the given object.
	 * @return True if and only if the given object is effective,
	 * 		   if this task and the given object belong to the same class,
	 * 		   and if this task and the given object have the same name, priority.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other.getClass() != this.getClass())
			return false;
		Task otherTask = (Task)other; 
		return (this.getName() == otherTask.getName() 
					&& this.getPriority() == otherTask.getPriority());
	}

//	@Override
//	public int compareTo(Task otherTask) {
//		return(this.getPriority().compareTo(otherTask.getPriority()));
//	}
}
